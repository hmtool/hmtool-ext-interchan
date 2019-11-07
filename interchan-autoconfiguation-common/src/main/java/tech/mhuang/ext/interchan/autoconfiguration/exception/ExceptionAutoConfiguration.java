package tech.mhuang.ext.interchan.autoconfiguration.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.mhuang.ext.interchan.core.exception.CommonExceptionHandler;

/**
 *
 * 异常通用处理
 *
 * @author mhuang
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(CommonExceptionHandler.class)
public class ExceptionAutoConfiguration {

    @Bean
    public CommonExceptionHandler commonExceptionHandler(){
        return new CommonExceptionHandler();
    }
}