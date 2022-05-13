package org.example.weather.controller;

import org.example.weather.api.WeatherApi;
import org.example.weather.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller

public class MainController {

    @Autowired
    WeatherBiz weatherBiz;

    @Autowired
    WeatherDao weatherDao;


    @RequestMapping("/")
    public String index() {
        return "index";
    }


    //API 호출, insert, select
    @RequestMapping(value = "/api/weather", method = POST)
    @ResponseBody
    public List<WeatherDto> callApi() {
        List<WeatherDto> weatherDto = new ArrayList<>();
        WeatherApi w = new WeatherApi();
        //System.out.println("grid: " + grid);
        weatherDto = w.getList();

//        //잘 받아오는지 확인
//        int count = 0;
//        for (int i = 0; i < weatherDto.size(); i++) {
//            count++;
//            System.out.println(weatherDto.get(i));
//        }
//        System.out.println("결과: " + count);

        //DB Insert
        int res = weatherBiz.insert(weatherDto);
        System.out.println("insert 결과: " + res); //insert 실패시 0

        //DB Select
        Date today = new Date();
        SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMdd");

        String start_date = simpledate.format(today); //오늘 날짜
        String end_date = simpledate.format(today.getTime() + 1000 * 60 * 60 * 24 * 3); //3일 후
        String data_type = "ALL";

        weatherDto = weatherBiz.select(start_date, end_date, data_type);

        return weatherDto;
    }


    //선택조회
    @RequestMapping(value = "/select", method = POST)
    @ResponseBody
    public List<WeatherDto> select(String start_date, String end_date, String data_type) {
        System.out.println("선택: " + start_date + " " + end_date + " " + data_type);

        List<WeatherDto> list = weatherBiz.select(start_date, end_date, data_type);

        return list;
    }
}
