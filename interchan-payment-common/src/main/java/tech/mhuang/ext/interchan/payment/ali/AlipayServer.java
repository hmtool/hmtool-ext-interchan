package tech.mhuang.ext.interchan.payment.ali;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.payment.dto.AliPayDTO;
import tech.mhuang.ext.interchan.payment.dto.AliRefundDTO;
import tech.mhuang.ext.interchan.payment.dto.AliTransDTO;
import tech.mhuang.ext.interchan.payment.exception.InterchanPayException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 阿里支付服务
 *
 * @author mhuang
 * @since 1.0.0
 */
public class AlipayServer {

    private static final String GATEWAY = "https://openapi.alipay.com/gateway.do";
    private static final String FORMAT = "json";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";
    private static final String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    private final static String APP_MODE = "app";

    public static AlipayTradeAppPayResponse payment(AliPayDTO dto) throws Exception {
        AlipayClient client = getClient(dto.getMchId(), dto.getRsaPrivateKey(), dto.getRsaPublicKey());
        if (APP_MODE.equals(dto.getMode())) {
            return createAppPay(client,
                    dto.getBody(),
                    dto.getSubject(),
                    dto.getTradeNo(), dto.getTimeout(), dto.getAmount(), dto.getNotifyUrl());
        }
        throw new InterchanPayException(500, "暂不支持支付" + dto.getMode() + "方式");
    }

    /**
     * 单笔退款
     *
     * @param dto 退款的对象
     * @return 退款结果
     * @throws Exception 退款失败则抛出异常
     */
    public static AlipayTradeRefundResponse refundOrder(AliRefundDTO dto) throws Exception {
        try {
            AlipayClient alipayClient = getClient(dto.getMchId(), dto.getRsaPrivateKey(), dto.getRsaPublicKey());
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setOutTradeNo(dto.getTradeNo()); //与预授权转支付商户订单号相同，代表对该笔交易退款
            model.setRefundAmount(dto.getAmount());
            model.setRefundReason(dto.getSubject());
            model.setOutRequestNo(dto.getOutRequestNo());//标识一次退款请求，同一笔交易多次退款需要保证唯一，如部分退款则此参数必传。
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            request.setBizModel(model);
            return alipayClient.execute(request);
        } catch (Exception e) {
            throw new InterchanPayException(500, "退款错误,调用退款失败");
        }
    }

    public static AlipayFundTransToaccountTransferResponse fundTrans(AliTransDTO dto) throws Exception {

        AlipayClient alipayClient = getClient(dto.getMchId(), dto.getRsaPrivateKey(), dto.getRsaPublicKey());
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("out_biz_no", dto.getTradeNo());
        contentMap.put("payee_type", dto.getPayeeType());
        contentMap.put("payee_account", dto.getTradeNo());
        contentMap.put("amount", dto.getTradeNo());
        if (StringUtil.isNotBlank(dto.getShowName())) {
            contentMap.put("payer_show_name", dto.getShowName());
        }
        if (StringUtil.isNotBlank(dto.getRealName())) {
            contentMap.put("payee_real_name", dto.getRealName());
        }
        if (StringUtil.isNotBlank(dto.getRemark())) {
            contentMap.put("remark", dto.getRemark());
        }
        request.setBizContent(JSONObject.toJSONString(contentMap));
        return alipayClient.execute(request);
    }

    public static AlipayTradeAppPayResponse createAppPay(AlipayClient alipayClient, String body, String subject, String tradeNo, String timeout, String amount, String notifyUrl) throws AlipayApiException {
        return createAppPay(alipayClient, body, subject, tradeNo, timeout, amount, PRODUCT_CODE, notifyUrl);
    }

    public static AlipayTradeAppPayResponse createAppPay(AlipayClient alipayClient,
                                      String body, String subject, String tradeNo,
                                      String timeout, String amount,
                                      String productCode, String notifyUrl) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(tradeNo);
        model.setTimeoutExpress(timeout);
        model.setTotalAmount(amount);
        model.setProductCode(PRODUCT_CODE);
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        //这里和普通的接口调用不同，使用的是sdkExecute
        return alipayClient.sdkExecute(request);
        //就是orderString 可以直接给客户端请求，无需再做处理。
    }

    public static AlipayClient getClient(String appId, String rsaPrivateKey, String rsaPublicKey) {
        return getClient(GATEWAY, appId, rsaPrivateKey, FORMAT, CHARSET, rsaPublicKey, SIGN_TYPE);
    }

    public static AlipayClient getClient(String gateway, String appId, String rsaPrivateKey, String format, String charset, String rsaPublicKey, String signType) {
        return new DefaultAlipayClient(gateway, appId, rsaPrivateKey, format, charset, rsaPublicKey, signType);
    }
}
