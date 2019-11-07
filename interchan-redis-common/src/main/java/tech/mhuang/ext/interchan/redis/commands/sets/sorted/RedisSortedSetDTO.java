package tech.mhuang.ext.interchan.redis.commands.sets.sorted;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * sortedset 对象
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class RedisSortedSetDTO implements Tuple {

    private Double score;

    private Object value;

    @Override
    public int compareTo(Double o) {
        return o.compareTo(score);
    }

    @Override
    public byte[] getValue() {
        if (value instanceof String) {
            return new StringRedisSerializer().serialize(String.valueOf(value));
        } else {
            return new StringRedisSerializer().serialize(JSON.toJSONString(value));
        }

    }

    @Override
    public Double getScore() {
        return score;
    }
}
