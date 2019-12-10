package com.future.storage.utils;

import com.future.storage.service.AuthorizationService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证授权拦截器
 *
 * @author shiyong
 * 2019-10-09 10:02
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Resource
    private AuthorizationService authorizationService;

    /**
     * <p>Title: preHandle</p>
     * <p>Description: 该方法将在请求处理之前进行调用</p>
     * @param request
     * @param response
     * @param handler
     * @return boolean
     * @throws Exception
     * @see HandlerInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorization = request.getHeader("Authorization");

        if (null == authorization || "".equals(authorization)) {

            throw new ParameterException();
        }

        return authorizationService.doAuthorization(authorization);
    }

    /**
     * <p>Title: postHandle</p>
     * <p>Description: 在当前请求进行处理之后，也就是Controller方法调用之后执行，但是它会在DispatcherServlet 进行视图返回渲染之前被调用</p>
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     * @see HandlerInterceptor#postHandle(HttpServletRequest, HttpServletResponse, Object, ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

    }

    /**
     * <p>Title: afterCompletion</p>
     * <p>Description: 该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。这个方法的主要作用是用于进行资源清理工作的。</p>
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     * @see HandlerInterceptor#afterCompletion(HttpServletRequest, HttpServletResponse, Object, Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
