package tech.mhuang.ext.interchan.wechat.wechat.common.pool.thread;

import tech.mhuang.ext.interchan.wechat.wechat.common.pool.service.ExecuteService;

/**
 * 页面点击事件.
 * 一般用于页面统计
 *
 * @author mhuang
 * @since 1.0.0
 */
@Deprecated
public class ViewThread extends BaseThread {

    private String fileName;

    public ViewThread(String openId, String fileName, ExecuteService weChatService) {
        super(openId, weChatService);
        this.fileName = fileName;
    }

    @Override
    public void run() {
    }
}
