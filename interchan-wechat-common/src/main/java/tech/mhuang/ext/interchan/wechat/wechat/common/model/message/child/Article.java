package tech.mhuang.ext.interchan.wechat.wechat.common.model.message.child;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;

import java.io.Serializable;


/**
 * 图文消息
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XStreamAlias(WechatConsts.ITEM)
public class Article extends BaseChildMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = WechatConsts.PICURL)
    private String picUrl;

    private String url;

    public static Article getArticle(String title, String descption, String picUrl, String url) {
        Article article = new Article();
        article.setTitle(title);
        article.setDescption(descption);
        article.setPicUrl(picUrl);
        article.setUrl(url);
        return article;
    }
}
