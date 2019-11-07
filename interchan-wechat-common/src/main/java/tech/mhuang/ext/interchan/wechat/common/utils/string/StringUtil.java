package tech.mhuang.ext.interchan.wechat.common.utils.string;


/**
 * str工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class StringUtil {

    /**
     * 组装URL参数
     *
     * @param str 组装的参数
     * @return URL
     */
    public static String packURL(String... str) {
        StringBuilder urlStr = new StringBuilder(str[0]);
        for (int i = 1; i < str.length; i++) {
            urlStr.append("&").append(str[i]);
        }
        return urlStr.toString();
    }

    /**
     * 首字母转大写
     *
     * @param s 转的字符串
     * @return 字符串
     */
    public static String toUpperCaseFirstOne(String s) {
        if (tech.mhuang.core.util.StringUtil.isEmpty(s) || Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return new StringBuilder().append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
