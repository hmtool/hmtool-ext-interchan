package tech.mhuang.ext.interchan.autoconfiguration.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * rest micro service call
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "mhuang.interchan.rest.micro")
public class RestMicroProperties extends RestProperties {

    /**
     * open micro enable default false
     */
    private boolean enable = false;
}
