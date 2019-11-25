package tech.mhuang.ext.interchan.payment.wechat.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import tech.mhuang.core.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayUtil {

    public static String requestUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (StringUtil.isNotBlank(queryString)) {
            return request.getRequestURL().append("?").append(request.getQueryString()).toString();
        }
        return request.getRequestURL().toString();
    }

    public static Map<String, String> parseXml(String request) {
        Map<String, String> map = new HashMap<>();
        try {
            // 读取输入流
            Document document = DocumentHelper.parseText(request);
            parse(map, document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void parse(Map<String, String> map, Document document) {
        // 得到xml根元素
        Element root = document.getRootElement();

        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
    }
}
