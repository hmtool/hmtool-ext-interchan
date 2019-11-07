package tech.mhuang.ext.interchan.core.rest;


import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * 微服务RestTemplate
 *
 * @author mhuang
 * @since 1.0.0
 */
public class MicroRestTemplate extends AbstractRestTemplate {

    public MicroRestTemplate() {

    }

    public MicroRestTemplate(SimpleClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }
}
