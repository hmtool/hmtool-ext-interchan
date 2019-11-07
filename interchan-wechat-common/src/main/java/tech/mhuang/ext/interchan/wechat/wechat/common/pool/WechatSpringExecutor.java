package tech.mhuang.ext.interchan.wechat.wechat.common.pool;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import tech.mhuang.ext.spring.start.SpringContextHolder;

import java.util.concurrent.ExecutorService;

/**
 * 微信处理类（Spring版）
 *
 * @author mhuang
 * @since 1.0.0
 */
public class WechatSpringExecutor extends ExecutorEventWechat {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringContextHolder.getBean(
            "threadPoolTaskExecutor", ThreadPoolTaskExecutor.class);

    private ExecutorService executorService = threadPoolTaskExecutor.getThreadPoolExecutor();

    public WechatSpringExecutor() {
        this.setEService(executorService);
    }
}