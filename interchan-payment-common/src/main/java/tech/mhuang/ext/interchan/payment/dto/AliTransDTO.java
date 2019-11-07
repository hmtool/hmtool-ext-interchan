package tech.mhuang.ext.interchan.payment.dto;

import lombok.Data;

/**
 *
 * 阿里转账
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class AliTransDTO {


    private String mchId;
    private String rsaPublicKey;
    //公匙

    private String rsaPrivateKey;
    //私匙

    private String tradeNo;
    //订单号

    /**
     * 收款方账户类型。可取值：
     * 1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
     * 2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。
     */
    private String payeeType;

    /**
     * 收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
     */
    private String payeeAcoount;

    /**
     * 转账金额，单位：元。
     * 只支持2位小数，小数点前最大支持13位，金额必须大于等于0.1元。
     */
    private String amount;

    ///////////非必填项///////////////
    /**
     * 付款方姓名（最长支持100个英文/50个汉字）。显示在收款方的账单详情页。如果该字段不传，则默认显示付款方的支付宝认证姓名或单位名称。
     */
    private String showName;

    /**
     * 收款方真实姓名（最长支持100个英文/50个汉字）。
     * 如果本参数不为空，则会校验该账户在支付宝登记的实名是否与收款方真实姓名一致。
     */
    private String realName;

    /**
     * 转账备注（支持200个英文/100个汉字）。
     * 当付款方为企业账户，且转账金额达到（大于等于）50000元，remark不能为空。收款方可见，会展示在收款用户的收支详情中。
     */
    private String remark;

}
