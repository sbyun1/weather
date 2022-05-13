package org.example.weather.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherBiz {

    @Autowired
    WeatherDao weatherDao;

    public int insert(List<WeatherDto> weatherDto){
        return weatherDao.insert(weatherDto);
    }

    public List<WeatherDto> select(String start_date, String end_date, String data_type){
        return weatherDao.select(start_date,end_date,data_type);
    }


}
