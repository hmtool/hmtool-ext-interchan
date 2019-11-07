package tech.mhuang.ext.interchan.wechat.wechat.common.model.message.child;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 公共子消息类
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseChildMessage extends BaseOtherMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String descption;

    public static BaseChildMessage setChildMessage(String mediaId, String title, String descption) {
        BaseChildMessage baseChildMessage = new BaseChildMessage();
        baseChildMessage.setMediaId(mediaId);
        baseChildMessage.setTitle(title);
        baseChildMessage.setDescption(descption);
        return baseChildMessage;
    }
}
