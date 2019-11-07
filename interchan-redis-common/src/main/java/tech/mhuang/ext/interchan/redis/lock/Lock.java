package tech.mhuang.ext.interchan.redis.lock;

import lombok.Data;

/**
 * 锁
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class Lock {
    private String name;
    private String value;
}
