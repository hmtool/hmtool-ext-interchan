package tech.mhuang.ext.interchan.rediskafkamiddle.annaotion;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 注解
 *
 * @author mhuang
 * @since 1.0.0
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface RedisKafka {

    /**
     * 是否不能重复执行（业务处理）
     *
     * @return 默认不能重复
     */
    boolean notRepeat() default true;
}
