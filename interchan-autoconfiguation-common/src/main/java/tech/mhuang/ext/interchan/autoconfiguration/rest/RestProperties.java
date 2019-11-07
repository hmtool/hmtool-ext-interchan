package tech.mhuang.ext.interchan.autoconfiguration.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.Proxy;

/**
 *
 * 基础类
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class RestProperties {

    /**
     * read time out
     */
    private Integer readTimeOut = 30000;

    /**
     * connect time out
     */
    private Integer connectTimeOut = 5000;

    /**
     * is use proxy.
     * default <p>false</p>
     */
    private boolean useProxy;

    /**
     * proxy host
     */
    private String proxyHost;

    /**
     * proxy port
     */
    private Integer proxyPort;

    /**
     * proxy type
     */
    private Proxy.Type proxyType = Proxy.Type.HTTP;
    /**
     * max pool size
     */
    private Integer maxPoolSize = 200;

    /**
     * buffer req Body
     */
    private Boolean bufferRequestBody = true;

    /**
     * trunk size
     */
    private Integer chunkSize = 4096;
}
