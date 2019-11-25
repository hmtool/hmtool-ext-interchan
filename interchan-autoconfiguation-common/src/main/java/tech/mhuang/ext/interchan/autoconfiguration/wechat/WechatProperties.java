package tech.mhuang.ext.interchan.autoconfiguration.wechat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * task properties
 *
 * @author mhuang
 * @since 1.4.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "mhuang.interchan.wechat")
public class WechatProperties {

    /**
     * default task enable is <code>true</code>
     */
    private boolean enable = true;
}
