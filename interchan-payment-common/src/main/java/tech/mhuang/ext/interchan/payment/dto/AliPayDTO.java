package tech.mhuang.ext.interchan.payment.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 支付宝支付
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AliPayDTO extends BasePayDTO {

    /**
     * 请求方式
     */
    private String mode;

    /**
     * 公匙
     */
    private String rsaPublicKey;

    /**
     * 私匙
     */
    private String rsaPrivateKey;
}