package tech.mhuang.ext.interchan.wechat.wechat.common.model.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;

import java.io.Serializable;

/**
 * 基础消息配置
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public abstract class BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = WechatConsts.TOUSER)
    private String toUserName;

    @JSONField(serialize = false)
    private String fromUserName;

    @JSONField(serialize = false)
    private Long createTime;

    @JSONField(name = WechatConsts.MSGTYPE)
    private String msgType = WechatConsts.NULL_STR;

    public BaseMessage() {

    }

    public BaseMessage(String tuser) {
        setToUserName(tuser);
    }

    public BaseMessage(String toUserName, String fromUserName) {
        setToUserName(toUserName);
        setFromUserName(fromUserName);
        setCreateTime(System.currentTimeMillis());
    }
}
