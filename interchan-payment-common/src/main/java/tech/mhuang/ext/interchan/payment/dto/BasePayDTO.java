package tech.mhuang.ext.interchan.payment.dto;

import lombok.Data;

/**
 *
 * 基础支付DTO
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class BasePayDTO {

    /**
     * 方式
     */
    private String mode;

    /**
     * 商户id
     */
    private String mchId;

    /**
     * 通知url
     */
    private String notifyUrl;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 价钱
     */
    private String amount;

    /**
     * 商品名
     */
    private String subject;

    /**
     * 超时时间
     */
    private String timeout;

    /**
     * 内容
     */
    private String body;

    /**
     * 代理ip
     */
    private String proxyIp;

    /**
     * 代理得端口
     */
    private int proxyPort;
}
