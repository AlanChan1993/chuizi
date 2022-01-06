package com.chuizi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns("/**") 表示拦截所有的请求
        //excludePathPatterns("/firstLogin","/zhuce");设置白名单，就是拦截器不拦截。首次输入账号密码登录和注册不用拦截！
        //登录页面在拦截器配置中配置的是排除路径，可以看到即使放行了，还是会进入prehandle，但是不会执行任何操作。
        registry.addInterceptor(new CookiendSessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/**/login",
                        "/**/login2",
                        "/**/regist",
                        "/**/*.html",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.jpg",
                        "/test*"
                        );
    }

}
