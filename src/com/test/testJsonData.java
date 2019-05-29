package com.test;

import com.JsonData.JsonData;

import java.io.IOException;
import java.util.Map;

public class testJsonData {
    public static void main(String[] argv){
        String transmit = JsonData.formatTransmitToJsonData("1","2","-3");
        System.out.println("formatTransmitToJsonData:"+transmit);

        String loginStr = JsonData.formatLoginToJsonData("123456","123456");
        System.out.println("formatLoginToJsonDat:"+loginStr);
        try {
            String type = JsonData.getServerResponseType(loginStr);
            System.out.println(type);

            // 测试鼠标单击数据
            String clickstr = JsonData.formatClickJson("1","2","3");
            System.out.println("clickStr="+clickstr);

            // map
            Map<String,Object> click = JsonData.getJsonMap(clickstr);
            String x = click.get("x").toString();
            String y = click.get("y").toString();
            String btntype = click.get("type").toString();
            System.out.println("x"+x+"y"+y+"type"+btntype);

        }catch (IOException e){e.printStackTrace();}

    }
}
