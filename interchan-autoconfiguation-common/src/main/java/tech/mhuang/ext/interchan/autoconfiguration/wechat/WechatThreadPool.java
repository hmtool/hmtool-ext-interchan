package tech.mhuang.ext.interchan.autoconfiguration.wechat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import tech.mhuang.ext.spring.pool.SpringThreadPool;

/**
 *
 * 微信线程池配置
 *
 * @author mhuang
 * @since 1.4.0
 */
@ConfigurationProperties(prefix = "mhuang.interchan.wechat.pool")
public class WechatThreadPool extends SpringThreadPool {

    private final String DEFAULT_NAME = "wechatThreadPool";

    public WechatThreadPool() {
        super();
        setBeanName(DEFAULT_NAME);
    }
}