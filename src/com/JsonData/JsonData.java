package com.JsonData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

// json数据处理
public class JsonData {
    private static ObjectMapper mapper = new ObjectMapper();
    public JsonData() {
        init();
    }
    private void init(){
        // mapper = new ObjectMapper();
    }
    // 格式化登陆数据
    public static String getLoginJsonData(String account, String password){
        LoginJson loginJson = new LoginJson();
        loginJson.setAccount(account);
        loginJson.setPassword(password);
        String jsonText = null;
        try{
           jsonText = mapper.writeValueAsString(loginJson);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonText;
    }
    // 获得服务器返回的数据类型
    public static String getServerRespontType(String data){
        String type = "null";
        try {
            Map<String, Object> map = mapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
            type = map.get("type").toString();
        }catch (JsonMappingException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        // return map.get("type").toString();
        return type;
    }
    //
}
