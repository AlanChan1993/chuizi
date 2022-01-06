package com.chuizi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("testDate")
public class DateTestController {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @RequestMapping("testD")
    public boolean testD(String date) throws ParseException {
        boolean a = true;
        Date dataA = new Date();

        if(simpleDateFormat.parse(date).getTime()>dataA.getTime()){
            a = false;
        }
        return a;
    }
}
