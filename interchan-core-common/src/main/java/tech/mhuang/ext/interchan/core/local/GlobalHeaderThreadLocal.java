package tech.mhuang.ext.interchan.core.local;

import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.core.exception.BusinessException;
import tech.mhuang.ext.interchan.protocol.GlobalHeader;
import tech.mhuang.ext.interchan.protocol.Result;

/**
 * 全局当前线程的常量
 *
 * @author mhuang
 * @since 1.0.0
 */
public class GlobalHeaderThreadLocal {

    private static ThreadLocal<GlobalHeader> globalUser = ThreadLocal.withInitial(() -> null);

    public static GlobalHeader get() {
        return globalUser.get();
    }

    public static GlobalHeader getOrException() {
        GlobalHeader globalHeader = get();
        if (globalHeader == null || StringUtil.isBlank(globalHeader.getUserId())) {
            throw new BusinessException(Result.TOKEN_IS_VALID, Result.TOKEN_IS_VALID_MSG);
        }
        return globalHeader;
    }

    public static void set(GlobalHeader value) {
        globalUser.set(value);
    }

    public static void remove() {
        globalUser.remove();
    }
}
