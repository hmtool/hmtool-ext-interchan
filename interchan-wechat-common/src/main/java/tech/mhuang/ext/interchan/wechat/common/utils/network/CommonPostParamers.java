package tech.mhuang.ext.interchan.wechat.common.utils.network;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import tech.mhuang.core.util.StringUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * 重构apache提交
 *
 * @author mhuang
 * @since 1.0.0
 */
public class CommonPostParamers {

    private MultipartEntityBuilder multiPart = null;
    private final static int boundaryLength = 0x20;
    private final static String boundaryAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
    private String boundary;
    final static String PLAIN_TEXT_TYPE = "text/plain";
    final static String UTF8 = "UTF-8";
    final static ContentType DEFAULT_CONTENT_TYPE = ContentType.create(PLAIN_TEXT_TYPE, UTF8);

    public CommonPostParamers() {
        super();
        boundary = getBoundary();
        multiPart = MultipartEntityBuilder.create();
        multiPart.setBoundary(boundary);
        multiPart.setCharset(Charset.forName(UTF8));
    }

    /**
     * auto generate boundary string
     *
     * @return a boundary string
     */
    private String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < boundaryLength; ++i)
            sb.append(boundaryAlphabet.charAt(random.nextInt(boundaryAlphabet.length())));
        return sb.toString();
    }

    /**
     * @return multipart boundary string
     */
    public String boundaryString() {
        return boundary;
    }

    /**
     * @return get MultipartEntity (apache)
     */
    public MultipartEntityBuilder getMultiPart() {
        return multiPart;
    }

    public void addString(String id, String str) {
        if (StringUtil.isEmpty(str)) {
            return;
        }
        multiPart.addPart(id, new StringBody(str, DEFAULT_CONTENT_TYPE));
    }

    public void addStringList(String id, ArrayList<String> strList) {
        addString(id, toStringList(strList));
    }

    public void addStringList(String id, String[] strArray) {
        addString(id, toStringList(strArray));
    }

    public void addPart(String id, File file) {
        multiPart.addPart(id, new FileBody(file));
    }

    public void addPart(String id, byte[] data) {
        addPart(id, data, "noName");
    }

    public void addPart(String id, byte[] data, String fileName) {
        multiPart.addPart(id, new ByteArrayBody(data, fileName));
    }

    private String toStringList(String[] sa) {
        return toStringList(Arrays.asList(sa));
    }

    private String toStringList(List<String> sa) {
        return StringUtil.join(sa, ",");
    }
}
