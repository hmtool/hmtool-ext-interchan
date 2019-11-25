package tech.mhuang.ext.interchan.autoconfiguration.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * task properties
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "mhuang.interchan.task")
public class TaskProperties {

    /**
     * default task enable is <code>true</code>
     */
    private boolean enable = true;

    /**
     * default name is <code>defaultInterTask</code>
     */
    private String name = "defaultInterTask";

    /**
     * default pool is <code>50</code>
     */
    private int poolSize = 50;
}
