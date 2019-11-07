package tech.mhuang.ext.interchan.auth.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import tech.mhuang.core.util.CollectionUtil;
import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.auth.constant.AuthConstant;
import tech.mhuang.ext.interchan.core.constans.Global;
import tech.mhuang.ext.interchan.core.exception.BusinessException;
import tech.mhuang.ext.interchan.protocol.GlobalHeader;
import tech.mhuang.ext.interchan.protocol.Result;
import tech.mhuang.ext.interchan.protocol.auth.AuthExcludeUrl;
import tech.mhuang.ext.interchan.protocol.auth.AuthUrl;
import tech.mhuang.ext.interchan.redis.commands.IRedisExtCommands;
import tech.mhuang.ext.spring.start.SpringContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 拦截器
 *
 * @author mhuang
 * @since 1.0.0
 */
@Slf4j
public class OpAuthInterceptor implements HandlerInterceptor {

    @Setter
    private int redisDataBase;

    @Setter
    private boolean checkUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof ResourceHttpRequestHandler)) {
            log.debug("请求的URL:{},请求类型:{}", request.getRequestURL().toString(), request.getMethod());
            try {
                if (checkUrl) {
                    boolean flag = this.checkUrlIsNotLogin(getUri(request), request);
                    if (!flag) {
                        String headJson = request.getHeader(Global.GLOBAL_HEADER);
                        if (StringUtil.isNotBlank(headJson)) {
                            GlobalHeader globalHeader = JSON.parseObject(headJson, GlobalHeader.class);
                            if (StringUtil.isNotBlank(globalHeader.getToken())) {
                                flag = this.checkUrlPower(globalHeader.getUserId(), request);
                                if (!flag) {
                                    throw new BusinessException(Result.SYS_FAILD, "您没有权限访问!");
                                }
                            } else {
                                //检查是否在排除的路径中
                                flag = this.checkUrlIsNotLogin(getUri(request), request);
                                if (!flag) {
                                    throw new BusinessException(Result.SYS_FAILD, "您没有权限访问!");
                                }
                            }
                        } else {
                            throw new BusinessException(Result.SYS_FAILD, "您没有权限访问!");
                        }
                    }
                }
            } catch (BusinessException e) {
                log.error("检查权限异常{}", e);
                this.writeJson(e.getCode(), e.getMessage(), response);
                return false;
            } catch (Exception e) {
                this.writeJson(Result.SYS_FAILD, "授权失败！", response);
                log.error("检查权限异常{}", e);
                return false;
            }
        }
        return true;
    }

    /**
     * 检查地址是否不需要登录
     *
     * @param uri
     * @param httpRequest
     * @return boolean
     * @Title: checkUrlIsNotLogin
     */
    private boolean checkUrlIsNotLogin(String uri, HttpServletRequest httpRequest) {
        //检查路径权限
        IRedisExtCommands repository = SpringContextHolder.getBean(IRedisExtCommands.class);
        boolean filterFlag = false;
        log.debug("开始检查请求的URI:{}是否不需要登录", uri);
        if (repository != null) {
            String cacheExcludeKey = AuthConstant.NOT_LOGIN_VIST_URLS_CACHEKEY;
            filterFlag = isFilterFlag(uri, repository, filterFlag, cacheExcludeKey);
        }
        return filterFlag;
    }

    private boolean isFilterFlag(String uri, IRedisExtCommands repository, boolean filterFlag, String cacheExcludeKey) {
        List<AuthExcludeUrl> vos =
                repository.hgetList(redisDataBase, AuthConstant.AUTH_DICT_KEY, cacheExcludeKey, AuthExcludeUrl.class);
        if (CollectionUtil.isNotEmpty(vos)) {
            filterFlag =
                    vos.parallelStream().filter(vo ->
                            (StringUtil.isNotBlank(vo.getUrl())
                                    && (uri.startsWith(vo.getUrl()) || "*".equals(vo.getUrl())))).findFirst().isPresent();
        }
        return filterFlag;
    }

    /**
     * 写json数据
     *
     * @param code
     * @param message
     * @param response
     * @return void
     * @throws Exception
     * @Title: writeJson
     * @Description: 写返回数据
     */
    private void writeJson(int code, String message, HttpServletResponse response) {
        try {
            Result result = new Result();
            result.setCode(code);
            result.setMessage(message);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(403);
            response.getWriter().write(JSON.toJSONString(result));
            response.getWriter().flush();
        } catch (Exception e) {
        }
    }

    /**
     * @return void
     * @Title: checkUrlPower
     * @Description:检查路径权限
     */
    @SuppressWarnings("unchecked")
    private boolean checkUrlPower(String userId, HttpServletRequest httpRequest) {
        //检查权限排除路径地址
        String url = getUri(httpRequest);
        log.debug("开始检查请求的URI:{}是否具有访问权限", url);
        //检查路径权限
        IRedisExtCommands repository = SpringContextHolder.getBean(IRedisExtCommands.class);
        boolean filterFlag = false;
        if (repository != null) {
            String cacheExcludeKey = AuthConstant.EXCLUDE_VIST_URLS_CACHEKEY;
            filterFlag = isFilterFlag(url, repository, filterFlag, cacheExcludeKey);
            if (!filterFlag) {
                String cacheKey = AuthConstant.USER_VIST_URL_CACHEKEY;
                AuthUrl authUrl = repository.hget(redisDataBase, cacheKey, userId.concat("-").concat(url), AuthUrl.class);
                if (authUrl != null) {
                    filterFlag = true;
                }
            }
        }
        return filterFlag;
    }


    /**
     * Retrieves the current request servlet path.
     * Deals with differences between servlet specs (2.2 vs 2.3+)
     *
     * @param request the request
     * @return the servlet path
     */
    public static String getServletPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();

        String requestUri = request.getRequestURI();
        // Detecting other characters that the servlet container cut off (like anything after ';')
        if (requestUri != null && servletPath != null && !requestUri.endsWith(servletPath)) {
            int pos = requestUri.indexOf(servletPath);
            if (pos > -1) {
                servletPath = requestUri.substring(requestUri.indexOf(servletPath));
            }
        }

        if (null != servletPath && !"".equals(servletPath)) {
            return servletPath;
        }

        int startIndex = request.getContextPath().equals("") ? 0 : request.getContextPath().length();
        int endIndex = request.getPathInfo() == null ? requestUri.length() : requestUri.lastIndexOf(request.getPathInfo());

        if (startIndex > endIndex) {
            // this should not happen
            endIndex = startIndex;
        }

        return requestUri.substring(startIndex, endIndex);
    }

    /**
     * Gets the uri from the request
     *
     * @param request The request
     * @return The uri
     */
    public static String getUri(HttpServletRequest request) {
        // handle http dispatcher includes.
        String uri = (String) request
                .getAttribute("javax.servlet.include.servlet_path");
        if (uri != null) {
            return uri;
        }

        uri = getServletPath(request);
        if (uri != null && !"".equals(uri)) {
            return uri;
        }

        uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }
}
