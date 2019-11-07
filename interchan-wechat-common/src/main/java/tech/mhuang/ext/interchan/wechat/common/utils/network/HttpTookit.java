package tech.mhuang.ext.interchan.wechat.common.utils.network;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

public class HttpTookit {

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    private static Logger logger = LoggerFactory.getLogger(HttpTookit.class);

    public static String postFile(String url, File f) throws IOException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        String response = null;
        if (url == null || f == null) {
            logger.error("URL或者文件为NULL");
            return response;
        }
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            FileEntity entity = new FileEntity(f, ContentType.APPLICATION_OCTET_STREAM);
            httpPost.setEntity(entity);
            responses = httpclient.execute(httpPost);
            HttpEntity responseEntity = responses.getEntity();
            response = EntityUtils.toString(responseEntity);
            EntityUtils.consume(responseEntity);
        } catch (Exception e) {
            logger.error("上传文件失败，发生异常", e);
        } finally {
            resume(httpclient, responses);
        }
        return response;
    }

    public static String httpGetWithJSON(String url) throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        String response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

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
            resume(httpclient, responses);
        }

        return response;
    }

    public static String httpPostMultipartWithJSON(String url, JSONObject json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        String response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            Set<String> keys = json.keySet();
            builder.setContentType(ContentType.MULTIPART_FORM_DATA);
            for (String key : keys) {
                builder.addPart(key, new StringBody(json.getString(key), ContentType.TEXT_PLAIN));
            }

            httpPost.setEntity(builder.build());
            responses = httpclient.execute(httpPost);
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
            resume(httpclient, responses);
        }

        return response;
    }

    public static String httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
//        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        String response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

            StringEntity se = new StringEntity(json, "UTF-8");
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);
            responses = httpclient.execute(httpPost);
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
            resume(httpclient, responses);
        }

        return response;
    }

    public static String httpDelWithJSON(String url) throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responses = null;
        String response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setHeader("Accept-Encoding", "gzip, deflate");
            httpDelete.setHeader("Accept-Language", "zh-CN");
            httpDelete.setHeader("Accept", "application/json, application/xml, text/html, text/*, image/*, */*");

            responses = httpclient.execute(httpDelete);
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
            resume(httpclient, responses);
        }

        return response;
    }

    public static void resume(CloseableHttpClient httpclient, CloseableHttpResponse responses) {
        try {
            if (responses != null) {
                responses.close();
            }
            if (httpclient != null) {
                httpclient.close();
            }
        } catch (IOException e) {
            logger.error("连接回收出现异常", e);
        }
    }
}
