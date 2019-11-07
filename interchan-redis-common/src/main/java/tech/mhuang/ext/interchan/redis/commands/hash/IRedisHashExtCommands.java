package tech.mhuang.ext.interchan.redis.commands.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * redisHash扩展接口
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IRedisHashExtCommands {

    /**
     * 设置hash过期时间
     *
     * @param key     key
     * @param field   field
     * @param value   value
     * @param seconds 时间
     * @return boolean
     */
    boolean hset(String key, String field, Object value, long seconds);

    /**
     * 取值转相应类型（该方法只支持单个）
     *
     * @param key   key
     * @param field field
     * @param clazz class
     * @param <T>   转换的类型
     * @return 转换后的结果
     */
    <T> T hget(String key, String field, Class<T> clazz);

    /**
     * 获取值转相应类型（该方法只支持单个）
     *
     * @param dbIndex 库
     * @param key     key
     * @param field   field
     * @param clazz   class
     * @param <T>     转换的类型
     * @return T 结果
     */
    <T> T hget(int dbIndex, String key, String field, Class<T> clazz);

    /**
     * 获取多个field的值
     *
     * @param key    key
     * @param fields field结合
     * @param clazz  转换的class
     * @param <T>    转换的类型
     * @return 结果
     */
    <T> List<T> hmget(String key, Collection<String> fields, Class<T> clazz);

    /**
     * 获取多个field的值
     *
     * @param dbIndex 库
     * @param key     key
     * @param fields  field集合
     * @param clazz   转换的class
     * @param <T>     转换的类型
     * @return 获取的集合
     */
    <T> List<T> hmget(int dbIndex, String key, Collection<String> fields, Class<T> clazz);

    /**
     * 获取值转相应类型（该方法只支持单个）
     *
     * @param key   key
     * @param field field
     * @param clazz 转换的class
     * @param <T>   转换的类型
     * @return 集合数组
     */
    <T> List<T> hgetList(String key, String field, Class<T> clazz);

    /**
     * 获取值转相应类型（该方法只支持单个）
     *
     * @param dbIndex 库
     * @param key     key
     * @param field   field
     * @param clazz   转换的class
     * @param <T>     转换的类型
     * @return 集合数组
     */
    <T> List<T> hgetList(int dbIndex, String key, String field, Class<T> clazz);

    /**
     * 获取key下的field value集合。
     *
     * @param key   key
     * @param clazz 转换的class
     * @param <T>   转换的类型
     * @return 对应的字段和值
     */
    <T> Map<String, T> hgetAll(String key, Class<T> clazz);

    /**
     * 获取key下的field value集合。
     *
     * @param index 库
     * @param key   key
     * @param clazz 转换的class
     * @param <T>   转换的类型
     * @return 对应的字段和值
     */
    <T> Map<String, T> hgetAll(int index, String key, Class<T> clazz);

    /**
     * 获取key下的field value集合。
     *
     * @param key   key
     * @param clazz 转换的class
     * @param <T>   转换的类型
     * @return 对应的字段和值
     */
    <T> Map<String, List<T>> hgetAllList(String key, Class<T> clazz);

    /**
     * 获取key下的field value集合。
     *
     * @param index 库
     * @param key   key
     * @param clazz 转换的class
     * @param <T>   转换的类型
     * @return 对应的字段和值
     */
    <T> Map<String, List<T>> hgetAllList(int index, String key, Class<T> clazz);

    /**
     * 获取key所有的值
     *
     * @param index 库
     * @param key   key
     * @param clazz 转换的class
     * @param <T>   转换的类型
     * @return 所有值
     */
    <T> List<T> hvals(int index, String key, Class<T> clazz);

    /**
     * 获取key下的所有value
     *
     * @param key   key
     * @param clazz 转换的class
     * @param <T>   转换的类型
     * @return 所有值
     */
    <T> List<T> hvals(String key, Class<T> clazz);
}
