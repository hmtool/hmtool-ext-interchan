package tech.mhuang.ext.interchan.wechat.wechat.common.pool.thread;

import tech.mhuang.ext.interchan.wechat.wechat.common.pool.service.ExecuteService;

/**
 * 扫码事件
 *
 * @author mhuang
 * @since 1.0.0
 */
public class ScanThread extends BaseThread {


    private String eventKey;//关注带事件

    public ScanThread(String openId, String eventKey, ExecuteService weChatService) {
        super(openId, weChatService);
        this.eventKey = eventKey;
    }

    @Override
    public void run() {
        synchronized (openId) {//关注后开启线程来处理关注数据
            weChatService.scan(openId, eventKey);
        }
    }

}
