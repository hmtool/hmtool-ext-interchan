package tech.mhuang.ext.interchan.autoconfiguration.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import tech.mhuang.core.check.CheckAssert;
import tech.mhuang.ext.interchan.auth.AuthFilter;
import tech.mhuang.ext.interchan.auth.interceptor.InterInterceptor;
import tech.mhuang.ext.interchan.auth.interceptor.InterceptorBean;

import java.util.List;

/**
 * 权限自动注入
 *
 * @author mhuang
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "mhuang.interchan.auth", name = "enable", havingValue = "true")
@EnableConfigurationProperties(value = {AuthProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Slf4j
public class AuthAutoConfiguration {

    private AuthProperties properties;

    public AuthAutoConfiguration(AuthProperties properties) {
        CheckAssert.check(properties, "not found auth properties");
        this.properties = properties;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.setUrlPatterns(this.properties.getFilterIncludeUrl());
        return registrationBean;
    }

    @Bean
    @ConditionalOnBean(FilterRegistrationBean.class)
    @ConditionalOnMissingBean
    public InterInterceptor interInterceptor() {
        InterInterceptor interInterceptor = new InterInterceptor();
        InterceptorBean bean = new InterceptorBean();
        bean.setRedisDatabase(properties.getRedisDataBase());
        bean.setCheckUrl(properties.isCheckInterceptorUrl());
        bean.setExcludeUrls(properties.getInterceptorExcludeUrl());
        bean.setIncludeUrls(properties.getInterceptorIncludeUrl());
        interInterceptor.setBean(bean);
        return interInterceptor;
    }

    @Configuration
    @ConditionalOnClass(WebSecurityConfigurerAdapter.class)
    @EnableWebSecurity
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final String ADMIN = "ADMIN";

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            log.info("loading WebSecurityConfig config");
            List<String> includeUrls = properties.getSecurityIncludeUrl();
            http.headers().frameOptions().disable().and().authorizeRequests().
                    antMatchers(includeUrls.toArray(new String[includeUrls.size()])).hasRole(ADMIN)
                    .anyRequest().permitAll().and()
                    .cors().and()
                    .httpBasic().and()
                    .csrf().disable();
            log.info("load WebSecurityConfig config success");
        }
    }
}
