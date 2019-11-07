package tech.mhuang.ext.interchan.wechat.wechat.common.model.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;
import tech.mhuang.ext.interchan.wechat.wechat.common.model.message.child.Article;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 图文响应消息
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleResMessage extends BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleCount = 0;

    @JSONField(serialize = false)
    private List<Article> articles = new ArrayList<Article>();

    @JSONField(name = WechatConsts.NEWS)
    @XStreamOmitField
    private ArticleList articleList = new ArticleList();

    public ArticleResMessage() {
        setMsgType(WechatConsts.NEWS);
    }

    public ArticleResMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
        setMsgType(WechatConsts.NEWS);
    }

    public void addArticle(Article article) {
        articles.add(article);
        articleCount = articles.size();
    }

    public void addArticle(String title, String descption, String picUrl, String url) {
        addArticle(Article.getArticle(title, descption, picUrl, url));
    }

    public void addJSONArticle(Article article) {
        articleList.articles.add(article);
        articleCount = articleList.articles.size();
    }

    public void addJSONArticle(String title, String descption, String picUrl, String url) {
        addJSONArticle(Article.getArticle(title, descption, picUrl, url));
    }

    @Data
    class ArticleList {
        private List<Article> articles = new ArrayList<>();
    }
}
