package tech.mhuang.ext.interchan.autoconfiguration.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 单机版调用，此类非微服务
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "mhuang.interchan.rest.single")
public class RestSingleProperties extends RestProperties {

    /**
     * open single enable default false
     */
    private boolean enable = false;
}
