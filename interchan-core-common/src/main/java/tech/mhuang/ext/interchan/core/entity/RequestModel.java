package tech.mhuang.ext.interchan.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import tech.mhuang.core.util.ObjectUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

/**
 * 请求封装类.
 *
 * @author zhangxh,mhuang
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public abstract class RequestModel<T> {

    private Type type;

    /**
     * 请求类型,默认POST请求
     * @since 1.2
     */
    private HttpMethod method = HttpMethod.POST;

    /**
     * 返回类型.
     * 支持Result<UserDTO<HeadDTO>>此方式
     * @since 1.2
     */
    private ParameterizedTypeReference<T> typeReference;

    /**
     * 媒体类型,默认json
     */
    private MediaType mediaType = MediaType.APPLICATION_JSON;
    /**
     * 服务器地址
     */
    private String url;
    /**
     * 后缀地址
     */
    private String sufUrl;

    /**
     * 传递的header
     * @since 1.2
     */
    private Map<String, String> headerParamMap = Collections.emptyMap();

    /**
     * 请求的参数
     */
    private Object params;

    protected RequestModel() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            typeReference = ParameterizedTypeReference.forType(type);
        }
        if (ObjectUtil.isEmpty(typeReference)) {
            typeReference = new ParameterizedTypeReference<T>() {
            };
        }
    }
}