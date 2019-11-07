package tech.mhuang.ext.interchan.redis.commands.sets.sorted;

import java.util.List;

/**
 * 有序集合
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IRedisSortedSetCommands {

    /**
     * 添加
     *
     * @param key   简
     * @param score 分数
     * @param value 值
     * @return boolean
     */
    boolean zadd(String key, double score, Object value);

    Long zadd(String key, List<RedisSortedSetDTO> list);


    ///////////////////////////操作其他库/////////////////////

    /**
     * 添加
     *
     * @param index 库
     * @param key   简
     * @param score 分数
     * @param value 值
     * @return boolean
     */
    boolean zadd(int index, String key, double score, Object value);

    double zIncrBy(int index, String key, double score, Object member);

    /**
     * 最大的分数在前获取
     *
     * @param index 库
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @param clz   转换的class
     * @param <T>   转换的类型
     * @return 结果
     */
    <T> List<T> zRevRange(int index, String key, long start, long end, Class<T> clz);

    Long zadd(int index, String key, List<RedisSortedSetDTO> list);

}
