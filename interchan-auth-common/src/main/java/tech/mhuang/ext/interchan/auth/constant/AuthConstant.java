
package tech.mhuang.ext.interchan.auth.constant;

/**
 * 权限常量
 *
 * @author mhuang
 * @since 1.0.0
 */
public class AuthConstant {

    /**
     * 不需要登录访问的地址
     */
    public static final String NOT_LOGIN_VIST_URLS_CACHEKEY = "not_login_vist_url";

    /**
     * 用户可访问地址
     */
    public static final String USER_VIST_URL_CACHEKEY = "user_vist_url";


    /**
     * 用户可访问地址
     */
    public static final String USER_VIST_ONLY_URL_CACHEKEY = "user_vist_only_url";

    /**
     * 排除访问的地址
     */
    public static final String EXCLUDE_VIST_URLS_CACHEKEY = "exclude_vist_url";


    /**
     * 排除访问的地址
     */
    public static final String AUTH_DICT_KEY = "auth_exclude";
}
