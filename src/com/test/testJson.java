package com.test;
import com.JsonData.JsonData;
import com.JsonData.LoginJson;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.util.Map;

public class testJson {
    public static void main(String[] argv){
        ObjectMapper mapper = new ObjectMapper();
        LoginJson loginJson = new LoginJson();
        loginJson.setAccount("23839902");
        loginJson.setPassword("ddddddd");
        String logintext = "{\"type\":\"Login\",\"account\":\"23212\",\"password\":\"dd2121\"}";

        // 字符串转java对象

        try {
            // java 对象转String
            String text = mapper.writeValueAsString(loginJson);
            System.out.print(text);
            // String 转java对象
            LoginJson newjson = mapper.readValue(logintext,LoginJson.class);//
            System.out.print(newjson.toString());

            // 解析json数据并获得某些属性的值
            Map<String,Object> map = mapper.readValue(text, new TypeReference<Map<String,Object>>() {
            });
            System.out.print("map="+map);
            System.out.println("type"+map.get("type"));
            System.out.println("account:"+map.get("account"));
            System.out.println("password:"+map.get("password"));
            System.out.println("type2="+ JsonData.getServerResponseType(text));
            JsonNode node = mapper.readTree(text);
            String account = node.get("account").toString();
            String password = node.get("password").toString();
            // String pp = node.get("dd").toString();

            System.out.println("账号:"+account+"密码："+password);
            // 解析xml文件
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
            xmlMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
            TreeNode node2 = xmlMapper.readTree(new File("config.xml"));
            String ip =  node2.get("server").get("ip").toString();
            TreeNode portn = node2.get("server").get("port");
            System.out.println(portn);
            String port = node2.get("server").get("port").toString();
            System.out.println("xml="+node2);
            System.out.println("ip:"+ip);
            System.out.println("port:"+port);
            int portint = Integer.parseInt(port);
            System.out.println("整型数据:"+portint);
//              Map<String,Object> map1 = xmlMapper.readValue(new File("config.xml"), new TypeReference<Map<String,Object>>() {
//            });
//             String port1 =  map1.get("port").toString();
//             System.out.println("Port1="+port1);

//            System.out.println(map1.get("server"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
