package tech.mhuang.ext.interchan.payment.wechat.utils;


import tech.mhuang.core.io.IOUtil;
import tech.mhuang.core.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
    // in milliseconds
    private final static int CONNECT_TIMEOUT = 5000;
    private final static String DEFAULT_ENCODING = "UTF-8";

    public static String postData(String urlStr, String data) throws Exception {
        return postData(urlStr, data, null, null, 0);
    }

    public static String postData(String urlStr, String data, String contentType, String ip, int port) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn;
            if (StringUtil.isNotEmpty(ip)) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
                // http 代理
                conn = url.openConnection(proxy);
            } else {
                conn = url.openConnection();
            }
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            if (contentType != null) {
                conn.setRequestProperty("content-type", contentType);
            }
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            if (data == null) {
                data = "";
            }
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } finally {
            IOUtil.close(reader);
        }
    }
}
