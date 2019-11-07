package tech.mhuang.ext.interchan.core.constans;

/**
 * 全局常量
 *
 * @author mhuang
 * @since 1.0.0
 */
public class Global {

    public static final String TYPE = "type";
    public static final String SOURCE = "source";
    public static final String GLOBAL_HEADER = "global_header";

    /**
     * 30分钟过期
     */
    public static final Long EXPIRE_THIRTY_MINUTES = 60 * 30L;

    /**
     * 2小时过期
     */
    public static final Long EXPIRE_TWO_HOURS = 60 * 60 * 2L;

    /**
     * 1天过期
     */
    public static final Long EXPIRE_ONE_DAY = 60 * 60 * 24L;

    /**
     * 1周过期
     */
    public static final Long EXPIRE_ONE_WEEKS = 60 * 60 * 24 * 7L;

    /**
     * 30天过期
     */
    public static final Long EXPIRE_THIRTY_DAYS = 60 * 60 * 24 * 30L;

    public static final String USER_ID = "userId";
    public static final String COMPANY_ID = "companyId";
}
