package tech.mhuang.ext.interchan.redis.commands;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import tech.mhuang.core.util.CollectionUtil;
import tech.mhuang.core.util.StringUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * redis操作类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class RedisExtCommands extends AbstractBaseRedisCommands {

    public void setRedisTemplate(RedisTemplate<String, ?> redisTemplate) {
        this.baseTempalte = redisTemplate;
    }

    @Override
    public boolean hset(String key, String field, Object value, long seconds) {
        boolean result = hset(key, field, value);
        if (result) {
            result = expire(key, seconds);
        }
        return result;
    }


    @Override
    public <T> T hget(String key, String field, Class<T> clazz) {
        return hget(defaultDbIndex, key, field, clazz);
    }

    @Override
    public <T> T hget(int dbIndex, String key, String field, Class<T> clazz) {
        String value = hget(dbIndex, key, field);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    @Override
    public <T> List<T> hgetList(String key, String field, Class<T> clazz) {
        return hgetList(defaultDbIndex, key, field, clazz);
    }

    @Override
    public <T> List<T> hgetList(int dbIndex, String key, String field, Class<T> clazz) {
        String value = hget(dbIndex, key, field);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return JSON.parseArray(value, clazz);
    }

    @Override
    public <T> List<T> hvals(int index, String key, Class<T> clazz) {
        List<String> value = hvals(index, key);
        if (CollectionUtil.isEmpty(value)) {
            return null;
        }
        return value.parallelStream().map(
                val -> JSON.parseObject(val, clazz)
        ).collect(Collectors.toList());
    }

    @Override
    public <T> Map<String, T> hgetAll(int index, String key, Class<T> clazz) {
        Map<String, String> map = hgetall(index, key);
        Map<String, T> result = new HashMap<>(map.size());
        map.forEach((mapKey, mapValue) -> {
            result.put(mapKey, JSON.parseObject(mapValue, clazz));
        });
        return result;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String value = get(key);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    @Override
    public <T> T get(int dbIndex, String key, Class<T> clazz) {
        String value = get(dbIndex, key);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    @Override
    public <T> Map<String, T> hgetAll(String key, Class<T> clazz) {
        Map<String, String> map = hgetall(key);
        Map<String, T> result = new HashMap<>(map.size());
        map.forEach((mapKey, mapValue) -> {
            result.put(mapKey, JSON.parseObject(mapValue, clazz));
        });
        return result;
    }

    @Override
    public <T> Map<String, List<T>> hgetAllList(String key, Class<T> clazz) {
        Map<String, String> map = hgetall(key);
        Map<String, List<T>> result = new HashMap<>(map.size());
        map.forEach((mapKey, mapValue) -> {
            result.put(mapKey, JSON.parseArray(mapValue, clazz));
        });
        return result;
    }

    @Override
    public <T> Map<String, List<T>> hgetAllList(int index, String key, Class<T> clazz) {
        Map<String, String> map = hgetall(index, key);
        Map<String, List<T>> result = new HashMap<>(map.size());
        map.forEach((mapKey, mapValue) -> {
            result.put(mapKey, JSON.parseArray(mapValue, clazz));
        });
        return result;
    }

    @Override
    public <T> List<T> hvals(String key, Class<T> clazz) {
        List<String> value = hvals(key);
        if (CollectionUtil.isEmpty(value)) {
            return null;
        }
        return value.parallelStream().map(
                val -> JSON.parseObject(val, clazz)
        ).collect(Collectors.toList());
    }

    @Override
    public <T> T executeRedisCommand(RedisCommand<T> redisCommand) {
        return redisCommand.executeCommand(this.baseTempalte);
    }

    @Override
    public <T> List<T> hmget(String key, Collection<String> fields, Class<T> clazz) {
        return hmget(defaultDbIndex, key, fields, clazz);
    }

    @Override
    public <T> List<T> hmget(int dbIndex, String key, Collection<String> fields, Class<T> clazz) {
        List<String> value = hmget(dbIndex, key, fields);
        if (CollectionUtil.isEmpty(value)) {
            return null;
        }
        return value.parallelStream().map(
                val -> JSON.parseObject(val, clazz)
        ).collect(Collectors.toList());
    }
}
