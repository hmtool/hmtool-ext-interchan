package tech.mhuang.ext.interchan.protocol.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * 可访问的url
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class AuthUrl implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 可访问地址
     */
    private String url;

}