package tech.mhuang.ext.interchan.wechat.wechat.common.utils;

import org.springframework.util.ObjectUtils;
import tech.mhuang.ext.interchan.wechat.common.utils.md5.MD5Util;
import tech.mhuang.ext.interchan.wechat.wechat.common.config.WeConfig;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;


public class PayCommonUtil {

    private final static String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String CreateNoncestr(int length) {
        StringBuilder res = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res.append(CHARS.indexOf(rd.nextInt(CHARS.length() - 1)));
        }
        return res.toString();
    }

    public static String createTimetmp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String CreateNoncestr() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res.append(CHARS.charAt(rd.nextInt(CHARS.length() - 1)));
        }
        return res.toString();
    }

    /**
     * 签名
     *
     * @param characterEncoding 编码格式
     * @param parameters        请求参数
     * @return 签名
     */
    @SuppressWarnings("rawtypes")
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (!ObjectUtils.isEmpty(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=").append(WeConfig.API_KEY);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }
}
