package tech.mhuang.ext.interchan.protocol.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * 过滤的URL
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class AuthExcludeUrl implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String type;

    /**
     * 可访问地址
     */
    private String url;

}