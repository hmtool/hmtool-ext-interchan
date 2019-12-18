package tech.mhuang.ext.interchan.core.rest;

import com.alibaba.fastjson.JSON;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.mhuang.core.file.FileUtil;
import tech.mhuang.core.util.CollectionUtil;
import tech.mhuang.core.util.ObjectUtil;
import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.core.entity.RequestModel;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * RestTemplate抽象实现
 *
 * @author mhuang
 * @since 1.0.0
 */
public abstract class AbstractRestTemplate extends RestTemplate {


    public AbstractRestTemplate() {
        super();
    }

    public AbstractRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    /**
     * 服务调用
     *
     * @param model 传递的参数
     * @return ResponseEntity
     */
    public ResponseEntity request(RequestModel model) {
        ResponseEntity response = null;
        HttpMethod method = model.getMethod();
        String url = model.getUrl();
        String sufUrl = model.getSufUrl();
        Map<String, String> headerParamMap = model.getHeaderParamMap();
        Object params = model.getParams();
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(headerParamMap);
        if (StringUtil.isNotBlank(sufUrl)) {
            url = url.concat(sufUrl);
        }
        if (method.equals(HttpMethod.GET) || method.equals(HttpMethod.DELETE)) {
            MultiValueMap<String, String> value = new LinkedMultiValueMap<>();
            if (ObjectUtil.isNotEmpty(params)) {
                Map tempData = null;
                if (params instanceof Map) {
                    tempData = (Map) params;
                } else {
                    tempData = BeanMap.create(params);
                }
                if (CollectionUtil.isNotEmpty(tempData)) {
                    tempData.forEach((k, v) -> {
                        value.add(JSON.toJSONString(k), JSON.toJSONString(v));
                    });
                }
            }
            url = UriComponentsBuilder.fromHttpUrl(url).queryParams(value).build().toUriString();
            HttpEntity entity = new HttpEntity(headers);
            response = exchange(url, method, entity, model.getTypeReference());
        } else if (method == HttpMethod.PUT || method == HttpMethod.POST) {
            headers.setContentType(model.getMediaType());
            HttpEntity request = new HttpEntity(params, headers);
            response = exchange(url, method, request, model.getTypeReference());
        }
        return response;
    }

    /**
     * 将file转换成byteArrayResource
     *
     * @param file File
     * @return ByteArrayResource资源
     * @throws IOException 完整异常
     */
    public static ByteArrayResource convertFileToByteArrayResource(File file) throws IOException {

        return new ByteArrayResource(FileUtil.readFileToByteArray(file)) {
            @Override
            public String getFilename() {
                return file.getName();
            }
        };
    }
}
