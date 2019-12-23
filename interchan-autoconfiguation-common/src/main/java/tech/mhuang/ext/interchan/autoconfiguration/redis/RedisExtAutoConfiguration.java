package tech.mhuang.ext.interchan.autoconfiguration.redis;

import io.lettuce.core.RedisClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import tech.mhuang.ext.interchan.redis.commands.IRedisExtCommands;
import tech.mhuang.ext.interchan.redis.commands.RedisExtCommands;
import tech.mhuang.ext.interchan.redis.lock.DistributedLockHandler;

/**
 * redis自动注册
 *
 * @author mhuang
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisExtAutoConfiguration {

    @Bean
    @ConditionalOnClass(RedisClient.class)
    @ConditionalOnBean(RedisTemplate.class)
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        lettuceConnectionFactory.setShareNativeConnection(false);
        RedisTemplate redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    @ConditionalOnClass(RedisExtCommands.class)
    public IRedisExtCommands redisExtCommands(RedisTemplate redisTemplate) {
        RedisExtCommands redisExtCommands = new RedisExtCommands();
        redisExtCommands.setRedisTemplate(redisTemplate);
        return redisExtCommands;
    }

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    @ConditionalOnClass(DistributedLockHandler.class)
    public DistributedLockHandler distributedLockHandler(StringRedisTemplate stringRedisTemplate) {
        DistributedLockHandler distributedLockHandler = new DistributedLockHandler();
        distributedLockHandler.setTemplate(stringRedisTemplate);
        return distributedLockHandler;
    }
}
