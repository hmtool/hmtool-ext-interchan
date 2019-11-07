package tech.mhuang.ext.interchan.redis.commands.string;

import java.util.Collection;
import java.util.Map;

/**
 * String工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IRedisStringCommands {

    /**
     * 设置key value
     *
     * @param key   保存的key
     * @param value 保存的value
     * @return 返回成功或者失败
     */
    boolean set(String key, Object value);

    /**
     * 设置key value 并添加过期时间
     *
     * @param key        保存的key
     * @param value      保存的value
     * @param expireTime 过期时间毫秒
     * @return boolean 返回成功或失败
     */
    boolean set(String key, Object value, long expireTime);

    /**
     * 获取
     *
     * @param key 获取值的key
     * @return String 返回的获取的值
     */
    String get(String key);

    /**
     * 根据库获取值
     *
     * @param index 获取的库
     * @param key   获取值的key
     * @return String 返回的key的value
     */
    String get(int index, String key);

    /**
     * 原子追加
     *
     * @param key 需要追加的key
     * @return Long 返回追加后的结果
     */
    Long incr(String key);

    /**
     * 根据库进行原子追加
     *
     * @param index 库
     * @param key   追加的key
     * @return Long 追加后的结果
     */
    Long incr(int index, String key);

    /**
     * 设置多个值
     *
     * @param map map的key代表redis的key、value代表redis的value
     * @return boolean 返回成功或者失败
     */
    boolean mset(Map<String, Object> map);

    /**
     * 获取多个值
     *
     * @param keys 获取的多个key的值
     * @return 返回多值
     */
    Collection<String> mget(Collection<String> keys);

    /**
     * 根据key追加值
     *
     * @param key   key
     * @param value 追加的值
     * @return long  返回追加后的长度
     */
    long append(String key, Object value);

    /**
     * 根据key进行删除
     *
     * @param key 删除的key
     * @return long 返回删库的结果
     */
    long del(String key);

    ////////////////////////操作其他库//////////////////////////////

    /**
     * 设置其他库的key value
     *
     * @param index 库
     * @param key   key
     * @param value value
     * @return 结果
     */
    boolean set(int index, String key, Object value);

    /**
     * 设置其他库过期时间
     *
     * @param index      库
     * @param key        key
     * @param value      value
     * @param expireTime 过期时间
     * @return boolean 结果
     */
    boolean set(int index, String key, Object value, long expireTime);

    /**
     * 设置其他库多个值
     *
     * @param index 库
     * @param map   其他的库的key value
     * @return boolean 结果
     */
    boolean mset(int index, Map<String, Object> map);

    /**
     * 根据key集合获取多个值
     *
     * @param index 库
     * @param keys  集合
     * @return 返回多值
     */
    Collection<String> mget(int index, Collection<String> keys);

    /**
     * 其他库追加值返回长度
     *
     * @param index 库
     * @param key   key
     * @param value 值
     * @return long 长度
     */
    long append(int index, String key, Object value);

    /**
     * 删除key
     *
     * @param index 需要删除key的库
     * @param key   删除的key
     * @return long 结果
     */
    public long del(int index, String key);
}
