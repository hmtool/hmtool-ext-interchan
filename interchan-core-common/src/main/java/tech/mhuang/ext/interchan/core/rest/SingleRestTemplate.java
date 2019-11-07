package tech.mhuang.ext.interchan.core.rest;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * 单机版RestTemplate
 *
 * @author mhuang
 * @since 1.0.0
 */
public class SingleRestTemplate extends AbstractRestTemplate {

    public SingleRestTemplate() {
        super();
    }

    public SingleRestTemplate(SimpleClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }
}
