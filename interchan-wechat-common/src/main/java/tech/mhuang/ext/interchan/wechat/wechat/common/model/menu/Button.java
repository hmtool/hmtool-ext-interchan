package tech.mhuang.ext.interchan.wechat.wechat.common.model.menu;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import tech.mhuang.core.util.CollectionUtil;
import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * 微信菜单
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class Button implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String type;

    private String key;

    private String url;

    @JSONField(name = WechatConsts.MEDIA_ID)
    private String mediaId;

    @JSONField(name = WechatConsts.SUB_BUTTON)
    private List<Button> subButton;

    /**
     * 小程序的appid
     */
    private String appid;

    /**
     * 小程序的页面路径
     */
    private String pagepath;

    public Button() {

    }

    public Button(String type, String name, String key) {
        this(type, name, key, null, null);
    }

    public Button(String type, String name, String key, String appId, String pagePath) {
        /**
         * 链接地址
         */
        if (StringUtil.equals(WechatConsts.VIEW, type)) {
            setUrl(key);
            /**
             * 视频语音
             */
        } else if (StringUtil.equals(WechatConsts.VIEW_LIMITED, type) ||
                StringUtil.equals(WechatConsts.MEDIA_ID, type)) {
            setMediaId(key);

            /**
             * 小程序
             */
        } else if (StringUtil.equals(WechatConsts.MINIPROGRAM, type)) {
            setUrl(key);
            setAppid(appId);
            setPagepath(pagePath);
        } else {
            setKey(key);
        }
        setType(type);
        setName(name);
    }


    public static Button subButton(String subName, String type, String name, String key) {
        return subButton(subName, type, name, key, null, null);
    }

    public static Button subButton(String subName, String type, String name, String key, String appId, String pagePath) {
        Button button = new Button();
        button.setName(name);
        if (CollectionUtil.isEmpty(button.getSubButton())) {
            button.setSubButton(new LinkedList<>());
        }
        button.getSubButton().add(Button.add(type, subName, key));
        return button;
    }

    public void addSubButton(String type, String name, String key) {
        addSubButton(type, name, key, null, null);
    }

    public void addSubButton(String type, String name, String key, String appId, String pagePath) {
        if (CollectionUtil.isEmpty(getSubButton())) {
            setSubButton(new LinkedList<>());
        }
        getSubButton().add(Button.add(type, name, key, appId, pagePath));
    }


    public static Button add(String type, String name, String key) {
        return new Button(type, name, key);
    }

    public static Button add(String type, String name, String key, String appId, String pagePath) {
        return new Button(type, name, key, appId, pagePath);
    }
}
