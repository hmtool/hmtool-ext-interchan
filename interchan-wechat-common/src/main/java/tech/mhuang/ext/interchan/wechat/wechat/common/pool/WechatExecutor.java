package tech.mhuang.ext.interchan.wechat.wechat.common.pool;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 微信处理类（JDK版）
 *
 * @author mhuang
 * @since 1.0.0
 */
@Component
public class WechatExecutor extends ExecutorEventWechat {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public WechatExecutor() {
        setEService(executorService);
    }

}
