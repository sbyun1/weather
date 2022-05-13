package org.example.weather.api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDate {
    static SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");

    public static String getToday() { //현재 날짜 : base_date
        Date date = new Date();
        String currentDate = simpleDate.format(date);

        return currentDate;
    }

    public static String getYesterday() {
        Date date = new Date();
        date = new Date(date.getTime() + (1000 * 60 * 60 * 24 * -1));
        String yesterday = simpleDate.format(date);

        return yesterday;
    }

    public static int getTime() { //현재 시간 : base_time
        Date time = new Date();
        SimpleDateFormat simpleHour = new SimpleDateFormat("HH");
        int currentHour = Integer.parseInt(simpleHour.format(time));

        SimpleDateFormat simpleMin = new SimpleDateFormat("mm");
        int currentMin = Integer.parseInt(simpleMin.format(time));

        int currentTime = (currentHour * 60) + currentMin; //분으로 변환

        return currentTime;
    }

    public static String[] getBase() { // 예측 시간 변환
        String baseTime;
        String baseDate;
        int currentTime = getTime();

        //return variable
        String[] res = new String[2];

        //base_date 결정 :
        if (currentTime >= (2 * 60) + 10) { //2시10분 이후 요청했을때

            //baseTime 결정: //23:10 이후 시간일때 이전 예측시간 가져오기
            if ((currentTime >= (23 * 60) + 10)) {
                baseTime = "23";
            } else if (currentTime >= (20 * 60) + 10) {
                baseTime = "20";
            } else if (currentTime >= (17 * 60) + 10) {
                baseTime = "17";
            } else if (currentTime >= (14 * 60) + 10) {
                baseTime = "14";
            } else if (currentTime >= (11 * 60) + 10) {
                baseTime = "11";
            } else if (currentTime >= (8 * 60) + 10) {
                baseTime = "08";
            } else if (currentTime >= (5 * 60) + 10) {
                baseTime = "05";
            }
            else {
                baseTime = "02";
            }
            baseDate = getToday();

        } else {//baseTime == 23 이고 날짜만 하루이전으로 변경해주면됌: 2시10분 이전에 요청시..
            baseTime = "23";
            baseDate = getYesterday();
        }

        res[0] = baseDate;
        res[1] = baseTime;

        return res;
    }


    public static void main(String[] args) {
        System.out.println("날짜: " + getToday() + " 시간: " + getTime());
        System.out.println("어제" + getYesterday());

    }


}
