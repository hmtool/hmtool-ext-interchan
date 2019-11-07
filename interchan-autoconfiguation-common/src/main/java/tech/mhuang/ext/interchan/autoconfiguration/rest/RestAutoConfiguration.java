package tech.mhuang.ext.interchan.autoconfiguration.rest;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import tech.mhuang.ext.interchan.core.rest.MicroRestTemplate;
import tech.mhuang.ext.interchan.core.rest.SingleRestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 *
 * Rest自动注入类
 *
 * @author mhuang
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(RestTemplate.class)
public class RestAutoConfiguration {

    static SimpleClientHttpRequestFactory generatorRequestFactory(RestProperties properties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (properties.isUseProxy()) {
            requestFactory.setProxy(new Proxy(properties.getProxyType(),
                    new InetSocketAddress(properties.getProxyHost(), properties.getProxyPort())
            ));
        }
        requestFactory.setReadTimeout(properties.getReadTimeOut());
        requestFactory.setConnectTimeout(properties.getConnectTimeOut());
        requestFactory.setChunkSize(properties.getChunkSize());
        requestFactory.setBufferRequestBody(properties.getBufferRequestBody());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.initialize();
        requestFactory.setTaskExecutor(executor);
        return requestFactory;
    }

    @AllArgsConstructor
    @Configuration
    @ConditionalOnProperty(prefix = "mhuang.interchan.rest.single", name = "enable", havingValue = "true")
    @EnableConfigurationProperties(RestSingleProperties.class)
    static class SingeRestTemplateConfiguration {

        private final RestSingleProperties properties;

        @Bean
        SingleRestTemplate singleRestTemplate() {
            return new SingleRestTemplate(generatorRequestFactory(this.properties));
        }
    }

    @AllArgsConstructor
    @Configuration
    @ConditionalOnProperty(prefix = "mhuang.interchan.rest.micro", name = "enable", havingValue = "true")
    @EnableConfigurationProperties(RestMicroProperties.class)
    static class MicroRestTemplateConfiguration {

        private final RestMicroProperties properties;

        @LoadBalanced
        @Bean
        MicroRestTemplate microRestTemplate() {
            return new MicroRestTemplate(generatorRequestFactory(this.properties));
        }
    }
}
