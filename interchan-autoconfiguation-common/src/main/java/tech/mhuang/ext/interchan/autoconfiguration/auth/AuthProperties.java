package tech.mhuang.ext.interchan.autoconfiguration.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 权限校验
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "mhuang.interchan.auth")
public class AuthProperties {

    /**
     * default auth is <code>true</code>
     */
    private boolean enable = true;

    /**
     * default auth redisdatabase is <code>0</code>
     */
    private Integer redisDataBase = 0;

    /**
     * default check url
     */
    private boolean checkInterceptorUrl = true;

    /**
     * interceptor include url
     */
    private List<String> interceptorIncludeUrl = new ArrayList<>();

    /**
     * interceptor exclude url
     */
    private List<String> interceptorExcludeUrl = new ArrayList<>();

    /**
     * filter include url
     */
    private List<String> filterIncludeUrl = Stream.of("/*").collect(Collectors.toList());

    /**
     * security include url
     */
    private List<String> securityIncludeUrl = Stream.of("/monitor/**").collect(Collectors.toList());
}