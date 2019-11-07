package tech.mhuang.ext.interchan.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.mhuang.ext.interchan.protocol.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用异常拦截
 *
 * @author mhuang
 * @since 1.0.0
 */
@ControllerAdvice
public class CommonExceptionHandler {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<?> defaultErrorHandler(HttpServletRequest request, Exception e) {

        logger.error("---Exception Handler---Host {} invokes url {} ERROR: {}", request.getRemoteHost(), request.getRequestURL(), e.getMessage());

        Result result = new Result<>();

        if (e instanceof BusinessException) {
            BusinessException business = (BusinessException) e;
            result.setCode(business.getCode());
            result.setMessage(business.getMessage());
        } else {
            result.setCode(Result.SYS_FAILD);
            result.setMessage("服务器异常");
        }
        result.setExceptionMsg(e.getStackTrace());
        return result;
    }
}
