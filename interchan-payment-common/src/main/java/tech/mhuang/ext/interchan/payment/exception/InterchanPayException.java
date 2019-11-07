package tech.mhuang.ext.interchan.payment.exception;

import lombok.Getter;
import lombok.Setter;

public class InterchanPayException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    @Setter
    @Getter
    private int code;

    @Setter
    @Getter
    private String message;

    public InterchanPayException() {
        super();
    }

    public InterchanPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterchanPayException(String message) {
        super(message);
    }

    public InterchanPayException(Throwable cause) {
        super(cause);
    }

    public InterchanPayException(int errCode, String errMsg) {
        super(String.format("%d:%s", errCode, errMsg));
        this.code = errCode;
        this.message = errMsg;
    }
}