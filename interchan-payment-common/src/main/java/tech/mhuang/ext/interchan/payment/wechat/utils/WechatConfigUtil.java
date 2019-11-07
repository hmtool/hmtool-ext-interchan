package tech.mhuang.ext.interchan.payment.wechat.utils;

import tech.mhuang.core.date.DatePattern;
import tech.mhuang.core.date.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.SortedMap;

/**
 *
 * 微信配置URL
 *
 * @author mhuang
 * @since 1.0.0
 */
public class WechatConfigUtil {

    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 基础参数
     * @param packageParams 存放的map
     * @param appId appid
     * @param mchId 商户id
     */
    public static void commonParams(SortedMap<Object, Object> packageParams, String appId, String mchId) {
        // 账号信息
        // 生成随机字符串
        String currTime = DateTimeUtil.fromDateTime(LocalDateTime.now(), DatePattern.PURE_DATETIME_PATTERN);
        String strTime = currTime.substring(8, 14);
        String strRandom = PayCommonUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        // 公众账号ID
        packageParams.put("appid", appId);
        // 商户号
        packageParams.put("mch_id", mchId);
        // 随机字符串
        packageParams.put("nonce_str", nonce_str);
    }
}