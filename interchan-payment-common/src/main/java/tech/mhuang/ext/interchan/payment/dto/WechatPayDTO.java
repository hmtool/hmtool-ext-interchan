package tech.mhuang.ext.interchan.payment.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信支付DTO
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WechatPayDTO extends BasePayDTO {

    /**
     * 公众id
     */
    private String appId;

    /**
     * 支付密匙
     */
    private String apiKey;

    /**
     * 支付得ip地址
     */
    private String ip;

    /**
     * jsapi 支付的时候使用
     */
    private String openId;
}
