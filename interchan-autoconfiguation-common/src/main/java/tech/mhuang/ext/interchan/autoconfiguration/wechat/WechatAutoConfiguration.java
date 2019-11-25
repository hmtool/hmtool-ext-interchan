package tech.mhuang.ext.interchan.autoconfiguration.wechat;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.mhuang.ext.interchan.wechat.wechat.common.pool.ExecutorEventWechat;

/**
 * 微信配置
 *
 * @author mhuang
 * @since 1.4.0
 */
@Configuration
@ConditionalOnClass(ExecutorEventWechat.class)
@ConditionalOnProperty(prefix = "mhuang.interchan.wechat", name = "enable", havingValue = "true")
@EnableConfigurationProperties({WechatProperties.class, WechatThreadPool.class})
public class WechatAutoConfiguration {

    private final WechatProperties properties;
    private final WechatThreadPool wechatThreadPool;

    public WechatAutoConfiguration(WechatProperties properties,WechatThreadPool wechatThreadPool) {
        this.properties = properties;
        this.wechatThreadPool = wechatThreadPool;
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutorEventWechat executorEventWechat(){
        ExecutorEventWechat executorEventWechat = new ExecutorEventWechat();
        wechatThreadPool.initialize();
        executorEventWechat.setEService(wechatThreadPool);
        return executorEventWechat;
    }
}
