package tech.mhuang.ext.interchan.auth.interceptor;

import lombok.Data;

import java.util.List;

/**
 * 拦截器的bean
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class InterceptorBean {

    private Integer redisDatabase = 0;

    private boolean checkUrl = true;

    private List<String> includeUrls;

    private List<String> excludeUrls;
}
