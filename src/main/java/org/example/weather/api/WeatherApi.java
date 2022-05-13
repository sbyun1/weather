package org.example.weather.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.weather.model.WeatherDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class WeatherApi {
    private final static String serviceKey = "%2Bcl3XHI9m1jqeDj9l4oK2ULa5PsUIq5cgWs5bN%2BHP1aUunINJusdylb%2BeGhjzGJdao8uLzP%2FgKUoesEUZftIlA%3D%3D";

    public String requestUrl() {

        try {
            final String numOfRows = "1010";
            final String dataType = "JSON";
            String pageNo = "1";
            String[] base = TimeDate.getBase();
            String base_date = base[0];
            String base_time = base[1] + "00";
            String nx = "62";
            String ny = "122";

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst");
            urlBuilder.append("?serviceKey=" + serviceKey);
            urlBuilder.append("&pageNo=" + pageNo);
            urlBuilder.append("&numOfRows=" + numOfRows);
            urlBuilder.append("&dataType=" + dataType);
            urlBuilder.append("&base_date=" + base_date);
            urlBuilder.append("&base_time=" + base_time);
            urlBuilder.append("&nx=" + nx);
            urlBuilder.append("&ny=" + ny);

            System.out.println("요청주소: " + urlBuilder);
            //요청주소
            URL url = new URL(urlBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());

            //읽어오기
            BufferedReader br;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();
            String result = sb.toString();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<WeatherDto> getList() {
        WeatherApi w = new WeatherApi();
        String res = w.requestUrl(); //API 호출 후 string으로 받기

        //String -> JsonObj Parsing
        //Gson JsonObject
        //JsonParser 클래스의 parse() 사용 -> JsonElement 이기때문에 JsonObject로 변환.
        JsonObject resObj = new JsonParser().parse(res).getAsJsonObject();
        JsonObject response = resObj.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonObject items = body.getAsJsonObject("items");
        JsonArray item = items.getAsJsonArray("item");
        List<WeatherDto> weatherDtoList = new ArrayList<WeatherDto>();

        for (int i = 0; i < item.size(); i++) {
            JsonObject temp = item.get(i).getAsJsonObject(); //item하나씩 꺼내서 temp에 담아줌
            String category = temp.get("category").getAsString(); //category 를 string으로 가져오기

            if (category.equals("TMP") || category.equals("REH")) {

                String baseDate = temp.get("baseDate").getAsString();
                String baseTime = temp.get("baseTime").getAsString();
                String fcstDate = temp.get("fcstDate").getAsString();
                String fcstTime = "T" + temp.get("fcstTime").getAsString().substring(0,2);
                int fcstValue = temp.get("fcstValue").getAsInt();
                int nx = temp.get("nx").getAsInt();
                int ny = temp.get("ny").getAsInt();

                WeatherDto dto = new WeatherDto();

                dto.setBaseDate(baseDate);
                dto.setBaseTime(baseTime);
                dto.setCategory(category);
                dto.setFcstDate(fcstDate);
                dto.setFcstTime(fcstTime);
                dto.setFcstValue(fcstValue);
                dto.setNx(nx);
                dto.setNy(ny);

                weatherDtoList.add(dto);


            }
        }
        return weatherDtoList;
    }
}
