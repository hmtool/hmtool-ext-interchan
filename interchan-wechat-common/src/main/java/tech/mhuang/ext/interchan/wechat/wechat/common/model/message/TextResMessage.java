package tech.mhuang.ext.interchan.wechat.wechat.common.model.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;
import tech.mhuang.ext.interchan.wechat.wechat.common.model.message.child.Content;


/**
 * 应答的文本消息
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextResMessage extends BaseMessage {

    private static final long serialVersionUID = 1L;

    @JSONField(serialize = false)
    private String content;

    @JSONField(name = WechatConsts.TEXT)
    private Content contentes;


    public TextResMessage() {
        setMsgType(WechatConsts.TEXT);
    }


    public TextResMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
        setMsgType(WechatConsts.TEXT);
    }

    //////////////////////////////////json///////////////////////////////
    public TextResMessage(String toUser) {
        super(toUser);
    }

    public void saveJSON(String toUser, String content) {
        setToUserName(toUser);
        if (contentes == null)
            contentes = new Content();
        contentes.setContent(content);
    }
}
