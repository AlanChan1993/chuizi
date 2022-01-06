package com.chuizi.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/testA")
public class TestController {
    @Value("${server.port}")
    Integer port;

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public String set(HttpSession session) {
        session.setAttribute("session2", "guangzhou");
        return String.valueOf(port) + session.getId();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(HttpSession session) {
        return session.getAttribute("session2") + ":" + port;
    }

}
