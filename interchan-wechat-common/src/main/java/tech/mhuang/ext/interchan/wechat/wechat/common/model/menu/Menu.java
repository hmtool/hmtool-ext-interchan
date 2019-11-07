package tech.mhuang.ext.interchan.wechat.wechat.common.model.menu;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 菜单工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = WechatConsts.BUTTON)
    private List<Button> button = new LinkedList<>();

    public void buttonAdd(String type, String name, String key) {
        buttonAdd(type, name, key, null, null);
    }

    public void buttonAdd(String type, String name, String key, String appId, String pagePath) {
        button.add(Button.add(type, name, key, appId, pagePath));
    }

    public Button buttonAddSub(String subName, String type, String name, String key) {
        return buttonAddSub(subName, type, name, key, null, null);
    }

    public Button buttonAddSub(String subName, String type, String name, String key, String appId, String pagePath) {
        return Button.subButton(subName, type, name, key, appId, pagePath);
    }


}
