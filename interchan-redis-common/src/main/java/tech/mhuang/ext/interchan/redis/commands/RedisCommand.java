
package tech.mhuang.ext.interchan.redis.commands;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis命令接口
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface RedisCommand<T> {

    T executeCommand(RedisTemplate<?, ?> template);

}
