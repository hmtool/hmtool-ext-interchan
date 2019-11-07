package tech.mhuang.ext.interchan.wechat.wechat.common.pool.thread;

import tech.mhuang.ext.interchan.wechat.wechat.common.pool.service.ExecuteService;

/**
 * 基础线程
 *
 * @author mhuang
 * @since 1.0.0
 */
public abstract class BaseThread implements Runnable {

    protected String openId;

    protected ExecuteService weChatService;

    @Override
    public abstract void run();

    public BaseThread(String openId, ExecuteService weChatService) {
        this.openId = openId;
        this.weChatService = weChatService;
    }

    public BaseThread(ExecuteService weChatService) {
        this.weChatService = weChatService;
    }
}
