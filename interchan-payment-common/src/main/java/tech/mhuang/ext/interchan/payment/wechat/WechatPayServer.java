package tech.mhuang.ext.interchan.payment.wechat;

import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.payment.dto.WechatPayDTO;
import tech.mhuang.ext.interchan.payment.dto.WechatRefundDTO;
import tech.mhuang.ext.interchan.payment.exception.InterchanPayException;
import tech.mhuang.ext.interchan.payment.wechat.utils.CommonUtil;
import tech.mhuang.ext.interchan.payment.wechat.utils.HttpUtil;
import tech.mhuang.ext.interchan.payment.wechat.utils.PayCommonUtil;
import tech.mhuang.ext.interchan.payment.wechat.utils.WechatConfigUtil;
import tech.mhuang.ext.interchan.payment.wechat.utils.XMLUtil;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * 微信支付服务
 *
 * @author mhuang
 * @since 1.0.0
 */
public class WechatPayServer {

    private final static String APP_MODE = "APP";
    /**
     * 小程序、微信H5
     */
    private final static String JSAPI_MODE = "JSAPI";
    private final static List<String> SUPPORT_MODE_LIST = Stream.of(APP_MODE, JSAPI_MODE).collect(Collectors.toList());

    public static Map<?, ?> payment(WechatPayDTO dto) throws Exception {
        SortedMap<Object, Object> packageParams = createCommonParmas(dto.getAppId(), dto.getMchId());
        if (SUPPORT_MODE_LIST.contains(dto.getMode().toUpperCase())) {
            if (APP_MODE.equals(dto.getMode().toUpperCase())) {
                return createAppPay(packageParams, dto.getTradeNo(), dto.getSubject(), dto.getAmount(), dto.getApiKey(), dto.getIp(), dto.getNotifyUrl(), dto.getProxyIp(), dto.getProxyPort());
            } else if (JSAPI_MODE.equalsIgnoreCase(dto.getMode().toUpperCase())) {
                return createJsApiPay(packageParams, dto.getTradeNo(), dto.getSubject(), dto.getAmount(), dto.getApiKey(), dto.getIp(), dto.getNotifyUrl(), dto.getProxyIp(), dto.getProxyPort(), dto.getOpenId());
            }
        }
        throw new InterchanPayException(500, "暂不支持支付" + dto.getMode() + "方式");
    }

    public static Map<?, ?> refundOrder(WechatRefundDTO dto) throws Exception {
        SortedMap<Object, Object> packageParams = createCommonParmas(dto.getAppId(), dto.getMchId());
        packageParams.put("out_trade_no", dto.getTradeNo());// 商户订单号
        packageParams.put("out_refund_no", dto.getOutRefundNo());// 商户退款单号
        packageParams.put("total_fee", dto.getTotalFee());// 订单金额
        packageParams.put("refund_fee", dto.getRefundFee());// 退款金额
        if (StringUtil.isNotBlank(dto.getFeeType())) {
            packageParams.put("refund_fee_type", dto.getFeeType());// 退款币种
        }
        if (StringUtil.isNotBlank(dto.getRefundDesc())) {
            packageParams.put("refund_desc", dto.getRefundDesc());// 退款原因
        }
        if (StringUtil.isNotBlank(dto.getNotifyUrl())) {
            packageParams.put("notify_url", dto.getNotifyUrl());// 退款结果通知url
        }

        String sign = PayCommonUtil.createSign("UTF-8", packageParams, dto.getApiKey()); //密匙
        packageParams.put("sign", sign);// 签名
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        String resXml = HttpUtil.postData(WechatConfigUtil.REFUND_URL, requestXML, null, dto.getProxyIp()
                , dto.getProxyPort());
        return XMLUtil.doXMLParse(resXml);
    }

    public static SortedMap<Object, Object> createCommonParmas(String appId, String mchId) {
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        WechatConfigUtil.commonParams(packageParams, appId, mchId);

        return packageParams;
    }

    public static Map<?, ?> createAppPay(SortedMap<Object, Object> packageParams,
                                         String tradeNo, String subject, String totalFee, String apiKey,
                                         String ip, String notifyUrl) throws Exception {
        return createAppPay(packageParams, tradeNo, subject, totalFee, apiKey, ip, notifyUrl, null, 0);
    }

    public static Map<?, ?> createAppPay(SortedMap<Object, Object> packageParams,
                                         String tradeNo, String subject, String totalFee, String apiKey,
                                         String ip, String notifyUrl, String proxyIp, int proxyPort) throws Exception {
        return createBasePay(packageParams, tradeNo, subject, totalFee, apiKey, ip, notifyUrl, proxyIp, proxyPort, APP_MODE, null);
    }

    public static Map<?, ?> createJsApiPay(SortedMap<Object, Object> packageParams,
                                           String tradeNo, String subject, String totalFee, String apiKey,
                                           String ip, String notifyUrl, String openid) throws Exception {
        return createJsApiPay(packageParams, tradeNo, subject, totalFee, apiKey, ip, notifyUrl, null, 0, openid);
    }

    public static Map<?, ?> createJsApiPay(SortedMap<Object, Object> packageParams,
                                           String tradeNo, String subject, String totalFee, String apiKey,
                                           String ip, String notifyUrl, String proxyIp, int proxyPort, String openid) throws Exception {
        // 账号信息

        return createBasePay(packageParams, tradeNo, subject, totalFee, apiKey, ip, notifyUrl, proxyIp, proxyPort, JSAPI_MODE, openid);
    }

    public static Map<?, ?> createBasePay(SortedMap<Object, Object> packageParams,
                                          String tradeNo, String subject, String totalFee, String apiKey,
                                          String ip, String notifyUrl, String proxyIp, int proxyPort, String appMode, String openid) throws Exception {
        packageParams.put("body", subject);// 商品描述
        packageParams.put("out_trade_no", tradeNo);// 商户订单号
        totalFee = CommonUtil.subZeroAndDot(totalFee);
        packageParams.put("total_fee", totalFee);// 总金额
        //H5支付要求商户在统一下单接口中上传用户真实ip地址 spbill_create_ip
        packageParams.put("spbill_create_ip", ip);// 发起人IP地址
        packageParams.put("notify_url", notifyUrl);// 回调地址
        packageParams.put("trade_type", appMode);// 交易类型
        packageParams.put("nonce_str", PayCommonUtil.CreateNoncestr());
        if (JSAPI_MODE.equalsIgnoreCase(appMode)) {
            packageParams.put("openid", openid);
        }
        String sign = PayCommonUtil.createSign("UTF-8", packageParams, apiKey); //密匙
        packageParams.put("sign", sign);// 签名
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        String resXml = HttpUtil.postData(WechatConfigUtil.UNIFIED_ORDER_URL, requestXML, null, proxyIp, proxyPort);
        return XMLUtil.doXMLParse(resXml);
    }
}