package tech.mhuang.ext.interchan.wechat.wechat.common.pool.thread;

import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.wechat.wechat.common.pool.service.ExecuteService;

/**
 * 关注监听
 *
 * @author mhuang
 * @since 1.0.0
 */
public class SubscribeThread extends BaseThread {


    private String status; //0代表关注 1代表取消关注
    private String eventKey;//关注带事件

    public SubscribeThread(String openId, String status, ExecuteService weChatService) {
        super(openId, weChatService);
        this.status = status;
    }

    public SubscribeThread(String openId, String status, String eventKey, ExecuteService weChatService) {
        super(openId, weChatService);
        this.status = status;
        this.eventKey = eventKey;
    }

    @Override
    public void run() {
        synchronized (openId) {//关注后开启线程来处理关注数据
            if (StringUtil.isEmpty(eventKey)) {
                weChatService.subscribe(openId, status);
            } else {
                weChatService.subscribeOtherEvent(openId, status, eventKey);
            }
        }
    }

}
