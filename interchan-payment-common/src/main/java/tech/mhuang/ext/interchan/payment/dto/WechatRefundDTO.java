package tech.mhuang.ext.interchan.payment.dto;

import lombok.Data;

/**
 *
 * 退款实体
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class WechatRefundDTO {

    private String appId;
    //公众id

    private String apiKey;
    //支付密匙

    private String mchId;
    //商户id

    private String nonce_str;
    //随机字符

    private String tradeNo;
    //订单号(微信订单号与商户订单号二选一)

    private String outRefundNo;
    //商户退款单号

    private String totalFee;
    //订单金额

    private String refundFee;
    //退款金额

    private String proxyIp;
    //代理ip

    private int proxyPort;
    //代理得端口

    //////////一下是非必填项/////
    /**
     * 退款货币类型，需与支付一致，或者不填。符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    private String feeType;
    //金额类型

    private String refundDesc;
    //退款原因 String(80)

    private String notifyUrl;
    //notify_url退款结果通知url

}
