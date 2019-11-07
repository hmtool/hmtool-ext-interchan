package tech.mhuang.ext.interchan.wechat.common.utils.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.mhuang.core.io.IOUtil;
import tech.mhuang.core.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Set;

/**
 * 自定义APACHE HTTPCLIENT、并优化
 *
 * @author mhuang
 * @since 1.0.0
 */
public class NetWorkUtils {

    static final private int TIMEOUT = 30000;
    private int httpTimeOut = TIMEOUT;
    private static final String POST = "POST";
    private static final String GET = "GET";

    /**
     * default is 30 sec
     * set http timeout limit (million second)
     *
     * @param timeOut 超时时间
     */
    public void setHttpTimeOut(int timeOut) {
        this.httpTimeOut = timeOut;
    }

    /**
     * (million second)
     *
     * @return http timeout limit
     */
    public int getHttpTimeOut() {
        return this.httpTimeOut;
    }

    public JSONObject syncHttpClient(String webUrl) {
        return syncHttpClient(webUrl, new CommonPostParamers());
    }

    public static void setRequest(HttpURLConnection connection, String methodType) {
        try {
            connection.setRequestMethod(methodType);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            // 连接超时
            connection.setConnectTimeout(TIMEOUT);

            // 读取超时 --服务器响应比较慢,增大时间
            connection.setReadTimeout(TIMEOUT);

            HttpURLConnection.setFollowRedirects(true);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取流
     *
     * @param inStream 读取的留
     * @return 字节数组
     * @throws Exception 抛出异常
     */
    public static String readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toString();
    }

    public JSONObject syncGetHttpClient(String webUrl) {
        return syncGetHttpClient(webUrl, new JSONObject());
    }

    public JSONArray syncGetHttpClientArr(String webUrl) {
        return syncGetHttpClientArr(webUrl, new JSONObject());
    }

    public JSONObject syncGetHttpClient(String webUrl, JSONObject header) {
        String returnData = getString2(webUrl, header);
        return JSON.parseObject(returnData);
    }

    public JSONArray syncGetHttpClientArr(String webUrl, JSONObject header) {
        String returnData = getString2(webUrl, header);
        return JSON.parseArray(returnData);
    }

    public JSONObject syncHttpClient(String webUrl, JSONObject json) {
        printStartUrl(webUrl);
        String response = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(webUrl);
            StringEntity s = new StringEntity(json.toString(), "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httppost.setEntity(s);
            responses = httpclient.execute(httppost);
            HttpEntity entity = responses.getEntity();
            response = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpTookit.resume(httpclient, responses);
            logger.info("endTime：" + System.currentTimeMillis());
            logger.debug("执行完毕！！");
        }
        logger.debug("返回的数据：" + response);
        return JSON.parseObject(response);
    }

    public String getString2(String webUrl, JSONObject header) {
        printStartUrl(webUrl);
        String response = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(webUrl);
            Set<String> headerKeys = header.keySet();
            for (String headerKey : headerKeys) {
                httpGet.setHeader(headerKey, header.getString(headerKey));
            }
            responses = httpclient.execute(httpGet);
            HttpEntity entity = responses.getEntity();
            response = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpTookit.resume(httpclient, responses);
            logger.info("endTime：" + System.currentTimeMillis());
            logger.debug("执行完毕！！");
        }
        logger.debug("返回的数据：" + response);
        return response;
    }

    public String getString2(String webUrl) {
        printStartUrl(webUrl);
        String response = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(webUrl);
            responses = httpclient.execute(httpGet);
            HttpEntity entity = responses.getEntity();
            response = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpTookit.resume(httpclient, responses);
            logger.info("endTime：" + System.currentTimeMillis());
            logger.debug("执行完毕！！");
        }
        logger.debug("返回的数据：" + response);
        return response;
    }

    public String getString(String webUrl, CommonPostParamers params) {
        return getString(webUrl, new JSONObject(), params);
    }

    public String getString(String webUrl, JSONObject header, CommonPostParamers params) {
        printStartUrl(webUrl);
        String response = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(webUrl);
            Set<String> headerKeys = header.keySet();
            for (String headerKey : headerKeys) {
                httppost.setHeader(headerKey, header.getString(headerKey));
            }
            MultipartEntityBuilder reqEntity = params.getMultiPart();
            httppost.setEntity(reqEntity.build());
            responses = httpclient.execute(httppost);
            HttpEntity entity = responses.getEntity();
            response = EntityUtils.toString(entity);
            EntityUtils.consume(entity);

        } catch (MalformedURLException e) {
            logger.error("malformed异常", e);
        } catch (IOException e) {
            logger.error("io异常", e);
        } catch (Exception e) {
            logger.error("异常", e);
        } finally {
            HttpTookit.resume(httpclient, responses);
            logger.info("endTime：" + System.currentTimeMillis());
            logger.debug("执行完毕！！");
        }
        logger.debug("返回的数据：" + response);
        return response;
    }

    private void printStartUrl(String webUrl) {
        logger.info("正在执行");
        logger.info("url:" + webUrl);
        logger.info("startTime：" + System.currentTimeMillis());
    }

    public JSONArray syncArrayHttpClient(String webUrl, CommonPostParamers params) {
        String returnData = getString(webUrl, params);
        return JSON.parseArray(returnData);
    }

    public JSONObject syncHttpClient(String webUrl, CommonPostParamers params) {
        return syncHttpClient(webUrl, new JSONObject(), params);
    }

    public JSONObject syncHttpClient(String webUrl, JSONObject header, CommonPostParamers params) {
        String returnData = getString(webUrl, header, params);
        return JSON.parseObject(returnData);
    }

    private static Logger logger = LoggerFactory.getLogger(NetWorkUtils.class);

    public static String sync(String url) {
        return sync(url, "");
    }

    public static String sync(String url, byte[] PostData) {
        HttpURLConnection connection = null;
        //尝试发送请求
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            setRequest(connection, POST);
            connection.setRequestProperty("Content-Type", "binary/octet-stream");
            OutputStream outStream = connection.getOutputStream();
            outStream.write(PostData);
            outStream.flush();
            outStream.close();

            int response_code = connection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                String result = IOUtil.toString(connection.getInputStream(), "UTF-8");
                logger.info("HTTP获取的数据是：" + result);
                return result;
            }
        } catch (Exception e) {
            logger.error("请求异常", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public static String sync(String url, String data) {
        logger.debug("请求的url：" + url);
        HttpURLConnection connection = null;
        try {
            URL address_url = new URL(url);
            connection = (HttpURLConnection) address_url.openConnection();
            setRequest(connection, POST);
            //设置请求的属性
            connection.setRequestProperty("Content-type", "text/html");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("accept-language", "zh-CN");

            sendData(connection, data);

            //得到访问页面的返回值
            int response_code = connection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                String result = IOUtil.toString(connection.getInputStream(), "UTF-8");
                logger.info("HTTP获取的数据是：" + result);
                return result;
            } else {
                logger.error("HTTP请求错误：" + IOUtil.toString(connection.getErrorStream(), "UTF-8"));
            }
        } catch (MalformedURLException e) {
            logger.error("mal：", e);
        } catch (IOException e) {
            logger.error("io异常：", e);
        } catch (Exception e) {
            logger.error("未知异常，需要处理的", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public static byte[] getImageBySync(String url) {
        return getImageBySync(url, null);
    }

    public static byte[] getImageBySync(String url, String data) {
        HttpURLConnection connection = null;
        try {
            URL address_url = new URL(url);
            connection = (HttpURLConnection) address_url.openConnection();
            setRequest(connection, GET);
            //设置请求的属性
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("accept-language", "zh-CN");

            //设置访问超时时间及读取网页流的超市时间,毫秒值
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");

            //设置参数
            sendData(connection, data);

            //得到访问页面的返回值
            int response_code = connection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                return IOUtil.toByteArray(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            logger.error("malformedUrl异常", e);
        } catch (IOException e) {
            logger.error("IO请求异常", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private static void sendData(HttpURLConnection connection, String data) throws IOException {
        if (StringUtil.isNotBlank(data)) {
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            // 发送请求参数
            out.write(data);
            out.flush();
            out.close();
        }
    }
}
