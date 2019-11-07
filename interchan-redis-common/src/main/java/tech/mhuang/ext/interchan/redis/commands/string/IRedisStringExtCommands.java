package tech.mhuang.ext.interchan.redis.commands.string;


/**
 * redis string扩展接口
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IRedisStringExtCommands {

    /**
     * 获取值并且转换对应类型
     *
     * @param key   要获取值的key
     * @param clazz 转换的class
     * @param <T>   转换的class类型
     * @return T 返回class类的值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 根据库获取值
     *
     * @param dbIndex 库
     * @param key     key
     * @param clazz   转换的class
     * @param <T>     转换的class类型
     * @return T 返回class类的值
     */
    <T> T get(int dbIndex, String key, Class<T> clazz);
}
