package com.chuizi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(value = "/")
public class TestCookieAndSessionController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 首次登录，输入账号和密码，数据库验证无误后，响应返回你设置的cookie。再次输入账号密码登录或者首次登录后再请求下一个页面，就会在请求头中携带cookie,
     * 前提是cookie没有过期。
     * 此url请求方法不管是首次登录还是第n次登录，拦截器都不会拦截。
     * 但是每次（首次或者第N次）登录都要进行，数据库查询验证账号和密码。
     * 做这个目的是如果登录页面A，登录成功后进页面B，页面B有链接进页面C，如果cookie超时，重新回到登录页面A。（类似单点登录）
     */

    @PostMapping(value = "login")
    public String test(HttpServletRequest request, HttpServletResponse response, @RequestParam("username")String name, @RequestParam("password")String pass) throws Exception{
        try {
            Map<String, Object> result= jdbcTemplate.queryForMap("select * from user where username=? and password=?", new Object[]{name, pass});
            if(result==null || result.size()==0){
                log.debug("账号或者密码不正确或者此人账号没有注册");
                throw new Exception("账号或者密码不正确或者此人账号没有注册！");
            }else{
                log.debug("查询满足条数----"+result);
                Cookie cookie = new Cookie("isLogin", "success");
                cookie.setMaxAge(30);
                cookie.setPath("/");
                response.addCookie(cookie);
                request.setAttribute("isLogin", name);
                log.debug("首次登录，查询数据库用户名和密码无误，登录成功，设置cookie成功");
                return "loginSuccess"; }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "error1";
        }
    }

    /**测试登录成功后页面loginSuccess ，进入次页demo.html*/
    @PostMapping(value = "sub")
    public String test() throws Exception{
        return  "demo";
    }

    /** 能进到此方法中，cookie一定没有过期。因为拦截器在前面已经判断过。
     * 过期，拦截器重定向到登录页面。过期退出登录，清空cookie。
     */
    @RequestMapping(value = "exit",method = RequestMethod.POST)
    public String exit(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if("isLogin".equalsIgnoreCase(cookie.getName())){
                log.debug("退出登录时,cookie还没过期，清空cookie");
                cookie.setMaxAge(0);
                cookie.setValue(null);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
        //重定向到登录页面
        return  "redirect:index.html";
    }

    /**利用session进行登录验证*/
    @RequestMapping(value = "login1",method = RequestMethod.POST)
    public String testSession(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam("name1")String name,
                              @RequestParam("pass1")String pass) throws Exception{
        try {
            Map<String, Object> result= jdbcTemplate.queryForMap("select * from userinfo where name=? and password=?", new Object[]{name, pass});
            if(result!=null && result.size()>0){
                String requestURI = request.getRequestURI();
                log.debug("此次请求的url:{}",requestURI);
                HttpSession session = request.getSession();
                log.debug("session="+session+"session.getId()="+session.getId()+"session.getMaxInactiveInterval()="+session.getMaxInactiveInterval());
                session.setAttribute("isLogin1", "true1");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "error1";
        }
        return  "loginSuccess";
    }

    //登出，移除登录状态并重定向的登录页
    @RequestMapping(value = "/exit1", method = RequestMethod.POST)
    public String loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute("isLogin1");
        log.debug("进入exit1方法，移除isLogin1");
        return "redirect:index.html";
    }


}
