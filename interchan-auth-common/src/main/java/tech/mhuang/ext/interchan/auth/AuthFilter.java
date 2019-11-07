package tech.mhuang.ext.interchan.auth;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.mhuang.core.util.ObjectUtil;
import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.core.constans.Global;
import tech.mhuang.ext.interchan.core.exception.BusinessException;
import tech.mhuang.ext.interchan.core.local.GlobalHeaderThreadLocal;
import tech.mhuang.ext.interchan.protocol.GlobalHeader;
import tech.mhuang.ext.interchan.protocol.Result;
import tech.mhuang.ext.jwt.admin.JwtFramework;
import tech.mhuang.ext.jwt.admin.bean.Jwt;
import tech.mhuang.ext.spring.start.SpringContextHolder;
import tech.mhuang.ext.spring.util.DataUtil;
import tech.mhuang.ext.spring.util.IpUtil;
import tech.mhuang.ext.spring.webmvc.WebRequestHeader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用权限处理
 *
 * @author mhuang
 * @since 1.0.0
 */
public class AuthFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Setter
    @Getter
    private List<String> excludeUrl = new ArrayList<>();

    /**
     * 权限类型--对应jwt的name名
     */
    private final String AUTH_TYPE = "authType";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (req instanceof HttpServletRequest) {
            try {
                HttpServletRequest request = (HttpServletRequest) req;
                WebRequestHeader httpRequest = new WebRequestHeader(request);
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                logger.info("请求的URL:{},请求类型:{}", request.getRequestURL().toString(), request.getMethod());
                GlobalHeader globalHeader = new GlobalHeader();
                globalHeader.setIp(IpUtil.getIp(request));
                globalHeader.setSource(request.getHeader(Global.SOURCE));
                JwtFramework jwtFramework = SpringContextHolder.getBean(JwtFramework.class);
                String authType = request.getHeader(AUTH_TYPE);
                String auth = null, headerName = null;
                if (StringUtil.isNotEmpty(authType)) {
                    Jwt.JwtBean jwtBean = jwtFramework.getJwt().getBeanMap().get(authType);
                    if (ObjectUtil.isNotEmpty(jwtBean)) {
                        auth = request.getHeader(jwtBean.getType());
                        headerName = jwtBean.getHeaderName();
                    }
                }
                if (StringUtil.isBlank(auth)) {
                    GlobalHeaderThreadLocal.set(globalHeader);
                    httpRequest.putHeader(Global.GLOBAL_HEADER, JSON.toJSONString(globalHeader));
                } else if (StringUtil.indexOf(auth, "Basic") == 0) {
                    GlobalHeaderThreadLocal.set(globalHeader);
                    httpRequest.putHeader(Global.GLOBAL_HEADER, JSON.toJSONString(globalHeader));
                } else if (StringUtil.length(auth) > headerName.length()) {
                    logger.debug("当前调用的token:{}", auth);
                    String token = StringUtil.substringAfter(auth, headerName);
                    try {
                        Map<String, Object> claimMap = jwtFramework.getProducer(authType).parse(token);
                        if (claimMap != null) {
                            globalHeader.setToken(token.trim());
                            globalHeader.setType((String) claimMap.get(Global.TYPE));
                            globalHeader.setCompanyId((String) claimMap.get(Global.COMPANY_ID));
                            globalHeader.setUserId((String) claimMap.get(Global.USER_ID));
                            GlobalHeaderThreadLocal.set(globalHeader);
                            httpRequest.putHeader(Global.GLOBAL_HEADER, JSON.toJSONString(globalHeader));
                        } else {
                            throw new BusinessException(Result.TOKEN_IS_VALID, Result.TOKEN_IS_VALID_MSG);
                        }
                    } catch (ExpiredJwtException e) {
                        logger.error("token已过期:{}", e);
                        throw new BusinessException(Result.TOKEN_EXPIRED, Result.TOKEN_EXPIRED_MSG);
                    } catch (Exception e) {
                        logger.error("token异常:{}", e);
                        throw new BusinessException(Result.TOKEN_IS_VALID, Result.TOKEN_IS_VALID_MSG);
                    }
                } else {
                    logger.error("token:{}无效，长度不一致", auth);
                    throw new BusinessException(Result.TOKEN_IS_VALID, Result.TOKEN_IS_VALID_MSG);
                }
                chain.doFilter(httpRequest, httpResponse);
            } catch (BusinessException e) {
                response.setContentType("text/json; charset=utf-8");
                Result<?> result = DataUtil.copyTo(e, Result.class);
                response.getWriter().write(JSON.toJSONString(result));
            }
        }
    }

    @Override
    public void destroy() {
        GlobalHeaderThreadLocal.remove();
    }
}
