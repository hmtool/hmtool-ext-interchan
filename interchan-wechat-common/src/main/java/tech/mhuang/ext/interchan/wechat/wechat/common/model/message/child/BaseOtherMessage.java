package tech.mhuang.ext.interchan.wechat.wechat.common.model.message.child;

import com.alibaba.fastjson.annotation.JSONField;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;

/**
 * 子消息公共类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class BaseOtherMessage {

    @JSONField(name = WechatConsts.MEDIA_ID)
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public static BaseOtherMessage setMedia(String mediaId) {
        BaseOtherMessage baseOtherMessage = new BaseOtherMessage();
        baseOtherMessage.setMediaId(mediaId);
        return baseOtherMessage;
    }
}
