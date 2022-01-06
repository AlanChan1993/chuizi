package com.chuizi.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器、
 * https://www.jb51.net/article/194061.htm
 * */
@Slf4j
public class CookiendSessionInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("进入拦截器");
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie:cookies) {
                log.debug("cookie===for遍历"+cookie.getName());
                if (StringUtils.equalsIgnoreCase(cookie.getName(), "isLogin")) {
                    log.debug("有cookie ---isLogin，并且cookie还没过期...");
                    //遍历cookie如果找到登录状态则返回true继续执行原来请求url到controller中的方法
                    return true;
                }
            }
        }
        log.debug("没有cookie-----cookie时间可能到期，重定向到登录页面后请重新登录。。。");
        response.sendRedirect("index.html");
        //返回false，不执行原来controller的方法
        return false;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("---------------------postHandle--------------------------");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("---------------------afterCompletion--------------------------");
    }

}
