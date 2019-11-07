package tech.mhuang.ext.interchan.redis.commands;

import tech.mhuang.ext.interchan.redis.commands.hash.IRedisHashCommands;
import tech.mhuang.ext.interchan.redis.commands.hash.IRedisHashExtCommands;
import tech.mhuang.ext.interchan.redis.commands.key.IRedisKeyCommands;
import tech.mhuang.ext.interchan.redis.commands.list.IRedisListCommands;
import tech.mhuang.ext.interchan.redis.commands.sets.sorted.IRedisSortedSetCommands;
import tech.mhuang.ext.interchan.redis.commands.string.IRedisStringCommands;
import tech.mhuang.ext.interchan.redis.commands.string.IRedisStringExtCommands;

/**
 * 通用Redis接口
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IRedisExtCommands extends
        IRedisStringCommands, IRedisStringExtCommands,
        IRedisHashCommands, IRedisHashExtCommands,
        IRedisListCommands,
        IRedisSortedSetCommands,
        IRedisKeyCommands {
    <T> T executeRedisCommand(RedisCommand<T> redisCommand);
}
