package tech.mhuang.ext.interchan.redis.commands.key;

/**
 * redis key操作
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IRedisKeyCommands {

    /**
     * 设置过期时间（秒）
     *
     * @param key     key
     * @param seconds 秒数
     * @return boolean
     */
    Boolean expire(String key, long seconds);

    /**
     * 设置过期时间（秒）
     *
     * @param index   库
     * @param key     key
     * @param seconds 秒数
     * @return boolean
     */
    Boolean expire(int index, String key, long seconds);

    /**
     * 判断key是否存在
     *
     * @param key key
     * @return 存在返回True、不存在返回False
     */
    Boolean exists(String key);

    /**
     * 判断key是否存在
     *
     * @param index 库
     * @param key   key
     * @return 存在返回True、不存在返回False
     */
    Boolean exists(int index, String key);
}
