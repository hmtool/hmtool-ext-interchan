package tech.mhuang.ext.interchan.auth.interceptor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.mhuang.core.util.CollectionUtil;

import java.util.List;


/**
 * 拦截器配置
 *
 * @author mhuang
 * @since 1.0.0
 */
public class InterInterceptor implements WebMvcConfigurer {

    @Setter
    @Getter
    private InterceptorBean bean;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> includeUrls = bean.getIncludeUrls();
        List<String> excludeUrls = bean.getExcludeUrls();
        OpAuthInterceptor opAuthInterceptor = new OpAuthInterceptor();
        opAuthInterceptor.setRedisDataBase(bean.getRedisDatabase());
        opAuthInterceptor.setCheckUrl(bean.isCheckUrl());
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(opAuthInterceptor);
        if (CollectionUtil.isNotEmpty(excludeUrls)) {
            interceptorRegistration.excludePathPatterns(excludeUrls.toArray(new String[excludeUrls.size()]));
        }
        if (CollectionUtil.isNotEmpty(includeUrls)) {
            interceptorRegistration.addPathPatterns(includeUrls.toArray(new String[includeUrls.size()]));
        }
    }
}
