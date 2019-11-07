package tech.mhuang.ext.interchan.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务通用异常
 *
 * @author mhuang
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    @Setter
    @Getter
    private int code;

    /**
     * 返回信息
     */
    @Setter
    @Getter
    private String message;

    /**
     * 异常
     */
    @Setter
    @Getter
    private Throwable cause;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

}
