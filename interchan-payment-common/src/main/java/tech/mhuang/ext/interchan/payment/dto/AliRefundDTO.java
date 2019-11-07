package tech.mhuang.ext.interchan.payment.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 阿里退款
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AliRefundDTO {

    private String rsaPublicKey;
    //公匙

    private String rsaPrivateKey;
    //私匙

    private String mchId;
    //商户id

    private String tradeNo;
    //订单号

    private String amount;
    //价钱

    private String subject;
    //商品名

    private String outRequestNo;
    //标识一次退款请求，同一笔交易多次退款需要保证唯一，如部分退款则此参数必传

}
