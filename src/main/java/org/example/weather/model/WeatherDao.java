package org.example.weather.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WeatherDao {
    private final String NAMESPACE = "weather.";

    @Autowired
    SqlSessionTemplate sqlSessionFactory;

    //저장
    public int insert(List<WeatherDto> weatherDto){
        int res = 0;

        try{
            res = sqlSessionFactory.insert(NAMESPACE + "insert", weatherDto);
            System.out.println("[insert completed]");
        }
        catch (Exception e) {
            System.out.println("[error: insert failed]");
            e.printStackTrace();
        }

        return res;
    }

    //전체조회
    public List<WeatherDto> select(String start_date, String end_date, String data_type){
        List<WeatherDto> weatherList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("start_date",start_date);
        map.put("end_date", end_date);
        map.put("data_type", data_type);

        try{
            weatherList = sqlSessionFactory.selectList(NAMESPACE + "select", map);
        } catch (Exception e) {
            System.out.println("[error]: select failed");
            e.printStackTrace();
        }
        return weatherList;
    }


    //




}
