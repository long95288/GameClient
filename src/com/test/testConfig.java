package com.test;

import com.Config.Config;

public class testConfig {
    public static void main(String[] args){
        try {
            Class.forName("com.Config.Config");
        }catch (ClassNotFoundException e){e.printStackTrace();}
    }
}
