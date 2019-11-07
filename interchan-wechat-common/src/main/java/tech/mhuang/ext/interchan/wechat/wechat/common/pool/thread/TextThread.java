package tech.mhuang.ext.interchan.wechat.wechat.common.pool.thread;

import tech.mhuang.ext.interchan.wechat.wechat.common.pool.service.ExecuteService;

/**
 * 文本消息异步处理
 *
 * @author mhuang
 * @since 1.0.0
 */
public class TextThread extends BaseThread {

    private String content;

    public TextThread(String openId, String content, ExecuteService weChatService) {
        super(openId, weChatService);
        this.content = content;
    }

    @Override
    public void run() {//文本消息保存的接口
        weChatService.saveOpTextSend(openId, content);
    }
}
