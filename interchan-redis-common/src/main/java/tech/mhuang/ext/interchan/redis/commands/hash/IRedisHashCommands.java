package tech.mhuang.ext.interchan.redis.commands.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Redis hash接口
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IRedisHashCommands {

    /**
     * 设置单个值
     *
     * @param key   设置的key
     * @param field 设置的field
     * @param value 设置的值
     * @return boolean
     */
    boolean hset(String key, String field, Object value);

    /**
     * 获取单个对key的字段的值
     *
     * @param key   获取的key
     * @param field 获取的field
     * @return String
     */
    String hget(String key, String field);

    /**
     * 根据键设置多个字段和值
     *
     * @param key    键
     * @param params 多个值和字段（Map格式）
     * @return boolean
     */
    boolean hmset(String key, Map<String, Object> params);

    /**
     * 根获取这个key中选择的多个field值
     *
     * @param key    获取的键
     * @param fields 获取的字段列表
     * @return 所有值
     */
    Collection<String> hmget(String key, Collection<String> fields);

    /**
     * 获取key的所有的字段、值
     *
     * @param key 建
     * @return 字段和值
     */
    Map<String, String> hgetall(String key);

    ///////////////////////////操作其他库////////////////////////////

    /**
     * 设置单个值
     *
     * @param index 设置的库
     * @param key   设置的key
     * @param field 设置的field
     * @param value 设置的value
     * @return boolean
     */
    boolean hset(int index, String key, String field, Object value);


    /**
     * 获取单个对key的字段的值
     *
     * @param index 获取的库
     * @param key   获取的key
     * @param field 获取的field
     * @return String
     */
    String hget(int index, String key, String field);

    /**
     * 根据键设置多个字段和值
     *
     * @param index  库
     * @param key    键
     * @param params 多个值和字段（Map格式）
     * @return boolean
     */
    boolean hmset(int index, String key, Map<String, Object> params);

    boolean hmsetList(String key, Map<String, List<Object>> params);

    boolean hmsetList(int index, String key, Map<String, List<Object>> params);

    /**
     * 根获取这个key中选择的多个field值
     *
     * @param index  库
     * @param key    获取的键
     * @param fields 获取的字段列表
     * @return 所有值
     */
    Collection<String> hmget(int index, String key, Collection<String> fields);

    /**
     * 获取key的所有的字段、值
     *
     * @param index 库
     * @param key   建
     * @return 字段和值
     */
    Map<String, String> hgetall(int index, String key);

    List<String> hvals(String key);

    /**
     * 获取key所有的值
     *
     * @param index 获取的库
     * @param key   获取的key
     * @return key下的所有值
     */
    List<String> hvals(int index, String key);

    Long hdel(int index, String key, Object field);

    Long hdel(String key, Object field);

    Long hincrby(String key, String field, Long incroment);

    Long hincrby(int index, String key, String field, Long incroment);

    List<String> hkeys(String key);

    List<String> hkeys(int index, String key);
}
