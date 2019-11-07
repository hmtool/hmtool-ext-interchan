package tech.mhuang.ext.interchan.redis.lock;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import tech.mhuang.core.util.StringUtil;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author mhuang
 * @since 1.0.0
 */
@Slf4j
public class DistributedLockHandler {

    /**
     * 单个业务持有锁的时间30s，防止死锁
     */
    private final static long LOCK_EXPIRE = 30 * 1000L;
    /**
     * 默认30ms尝试一次
     */
    private final static long LOCK_TRY_INTERVAL = 30L;
    /**
     * 默认尝试20s
     */
    private final static long LOCK_TRY_TIMEOUT = 20 * 1000L;

    @Setter
    private StringRedisTemplate template;

    /**
     * 尝试获取全局锁
     *
     * @param lock 锁的名称
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock) {
        return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE, false);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock   锁的名称
     * @param isOnly 是否只能获取一次锁
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, boolean isOnly) {
        return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE, isOnly);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock    锁的名称
     * @param timeout 获取超时时间 单位ms
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long timeout) {
        return getLock(lock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE, false);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock        锁的名称
     * @param timeout     获取锁的超时时间
     * @param tryInterval 多少毫秒尝试获取一次
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long timeout, long tryInterval) {
        return getLock(lock, timeout, tryInterval, LOCK_EXPIRE, false);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock           锁的名称
     * @param timeout        获取锁的超时时间
     * @param tryInterval    多少毫秒尝试获取一次
     * @param lockExpireTime 锁的过期
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
        return getLock(lock, timeout, tryInterval, lockExpireTime, false);
    }


    /**
     * 操作redis获取全局锁
     *
     * @param lock           锁的名称
     * @param timeout        获取的超时时间
     * @param tryInterval    多少ms尝试一次
     * @param lockExpireTime 获取成功后锁的过期时间
     * @param isOnly         是否只能获取一次锁
     * @return true 获取成功，false获取失败
     */
    public boolean getLock(Lock lock, long timeout, long tryInterval, long lockExpireTime, boolean isOnly) {
        try {
            if (StringUtil.isEmpty(lock.getName()) || StringUtil.isEmpty(lock.getValue())) {
                return false;
            }
            do {
                if (!template.hasKey(lock.getName())) {
                    ValueOperations<String, String> ops = template.opsForValue();
                    ops.set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
                    return true;
                } else if (isOnly) {
                    return false;
                } else {//存在锁
                    log.debug("lock[{}] exist!！！", lock.getName());
                }
                Thread.sleep(tryInterval);
            }
            while (template.hasKey(lock.getName()));
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return false;
    }

    final String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 释放锁
     *
     * @param lock 释放的锁
     */
    public void releaseLock(Lock lock) {
        if (StringUtil.isNotEmpty(lock.getName())) {
            template.execute((RedisConnection connection) -> {
                RedisSerializer<String> serializer = template.getStringSerializer();
                return connection.eval(serializer.serialize(script), ReturnType.BOOLEAN, 1, serializer.serialize(lock.getName()), serializer.serialize(lock.getValue()));
            });
        }
    }
}