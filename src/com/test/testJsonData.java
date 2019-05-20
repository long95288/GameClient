package com.test;

import com.JsonData.JsonData;

public class testJsonData {
    public static void main(String[] argv){
        String transmit = JsonData.formatTransmitToJsonData("1","2","-3");
        System.out.println("formatTransmitToJsonData:"+transmit);

        String loginstr = JsonData.formatLoginToJsonData("123456","123456");
        System.out.println("formatLoginToJsonDat:"+loginstr);


    }
}
