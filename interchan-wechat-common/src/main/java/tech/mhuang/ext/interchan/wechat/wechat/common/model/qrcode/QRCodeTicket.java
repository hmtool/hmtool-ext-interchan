package tech.mhuang.ext.interchan.wechat.wechat.common.model.qrcode;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码
 *
 * @author mhuang
 * @since 1.0.0
 */
public class QRCodeTicket implements Serializable {


    public enum QRCODE_TYPE {
        QR_SCENE,//临时二维码
        QR_LIMIT_SCENE,//永久二维码
        QR_LIMIT_STR_SCENE;//永久二维码（字符串）

        @Override
        public String toString() {
            return QR_LIMIT_STR_SCENE.name();
        }

        ;
    }

    ;

    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    @JSONField(name = WechatConsts.ACTION_NAME)
    private QRCODE_TYPE actionName;//二维码类型

    @Setter
    @Getter
    @JSONField(name = WechatConsts.EXPRICE_SECONDS)
    private String expireSeconds;//只有临时二维码有效。该二维码有效时间，以秒为单位。 最大不超过604800（即7天）。

    @Setter
    @Getter
    @JSONField(name = WechatConsts.ACTION_INFO)
    private Map<String, Scene> actionInfo = new HashMap<String, QRCodeTicket.Scene>();//二维码详细信息


    public void createTicket(String scene_id, QRCODE_TYPE qrcodeType) {
        setActionName(qrcodeType);
        Scene scene = new Scene();
        scene.setSceneId(scene_id);
        getActionInfo().put(WechatConsts.SCENE, scene);
    }

    public void createTicket(String sceneId, String expireSeconds, QRCODE_TYPE qrcodeType) {
        setActionName(qrcodeType);
        Scene scene = new Scene();
        scene.setSceneId(sceneId);
        setExpireSeconds(expireSeconds);
        getActionInfo().put(WechatConsts.SCENE, scene);
    }

    class Scene {

        @Setter
        @Getter
        @JSONField(name = WechatConsts.SCENE_ID)
        private String sceneId;//临时/永久二维码采用（数字）

        @Setter
        @Getter
        @JSONField(name = WechatConsts.SCENE_STR)
        private String sceneStr;//永久二维码采用
    }
}
