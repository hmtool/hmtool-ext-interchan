package tech.mhuang.ext.interchan.wechat.wechat.common.model.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;
import tech.mhuang.ext.interchan.wechat.wechat.common.model.message.child.BaseChildMessage;
import tech.mhuang.ext.interchan.wechat.wechat.common.model.message.child.BaseOtherMessage;
import tech.mhuang.ext.interchan.wechat.wechat.common.model.message.child.Music;

import java.io.Serializable;

/**
 * 其他的应答消息
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OtherResMessage<T> extends BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private T otherMessage;

    public OtherResMessage() {

    }

    public OtherResMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
    }

    /**
     * 根据类型进行添加
     *
     * @param mediaId 添加的媒体id
     * @param msgType 消息类型
     */
    @Deprecated
    public void saveType(String mediaId, String msgType) {
        baseSave(mediaId, msgType);
    }

    /**
     * 保存图片
     *
     * @param mediaId 媒体id
     */
    public void saveImage(String mediaId) {
        baseSave(mediaId, WechatConsts.IMAGE);
    }


    /**
     * 保存音频
     *
     * @param mediaId 媒体id
     */
    public void saveVoice(String mediaId) {
        baseSave(mediaId, WechatConsts.VOICE);
    }

    /**
     * 保存视频
     *
     * @param mediaId   视频id
     * @param title     标题
     * @param descption 说明
     */
    public void saveVideo(String mediaId, String title, String descption) {
        baseSaveTitle(mediaId, title, descption, WechatConsts.VIDEO);
    }

    /**
     * 响应音乐消息
     *
     * @param title        标题
     * @param descption    内容
     * @param musicUrl     音乐Url
     * @param hQMusicUrl   高清url
     * @param thumbMediaId 背景媒体id
     */
    @SuppressWarnings("unchecked")
    public void saveMusic(String title, String descption, String musicUrl, String hQMusicUrl, String thumbMediaId) {
        setMsgType(WechatConsts.MUSIC);
        otherMessage = (T) Music.setMusicMessage(title, descption, musicUrl, hQMusicUrl, thumbMediaId);
    }

    @SuppressWarnings("unchecked")
    private void baseSave(String mediaId, String msgType) {
        setMsgType(msgType);
        otherMessage = (T) BaseOtherMessage.setMedia(mediaId);
    }

    @SuppressWarnings("unchecked")
    private void baseSaveTitle(String mediaId, String title, String descption, String msgType) {
        setMsgType(msgType);
        otherMessage = (T) BaseChildMessage.setChildMessage(mediaId, title, descption);
    }
}
