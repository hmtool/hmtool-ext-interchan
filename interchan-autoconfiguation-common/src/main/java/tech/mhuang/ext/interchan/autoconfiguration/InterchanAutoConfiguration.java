package tech.mhuang.ext.interchan.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tech.mhuang.ext.interchan.autoconfiguration.auth.AuthAutoConfiguration;
import tech.mhuang.ext.interchan.autoconfiguration.exception.ExceptionAutoConfiguration;
import tech.mhuang.ext.interchan.autoconfiguration.redis.RedisExtAutoConfiguration;
import tech.mhuang.ext.interchan.autoconfiguration.rest.RestAutoConfiguration;
import tech.mhuang.ext.interchan.autoconfiguration.swagger.GatewaySwaggerAutoConfiguration;
import tech.mhuang.ext.interchan.autoconfiguration.swagger.SwaggerAutoConfiguration;
import tech.mhuang.ext.interchan.autoconfiguration.task.TaskAutoConfiguration;
import tech.mhuang.ext.springboot.context.SpringBootExtAutoConfiguration;

/**
 * 自动配置注入包.
 *
 * @author mhuang
 * @since 1.0.0
 */
@Configuration
@Import({AuthAutoConfiguration.class, RestAutoConfiguration.class,
        SwaggerAutoConfiguration.class, RedisExtAutoConfiguration.class,
        TaskAutoConfiguration.class,
        GatewaySwaggerAutoConfiguration.class, ExceptionAutoConfiguration.class})
@AutoConfigureAfter(value = {SpringBootExtAutoConfiguration.class})
public class InterchanAutoConfiguration {

}