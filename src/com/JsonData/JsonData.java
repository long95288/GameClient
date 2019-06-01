package com.JsonData;

import com.JsonData.SendDataJson.GameGrade;
import com.JsonData.SendDataJson.GameOverJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
    public static String formatLoginToJsonData(String account, String password){
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
    // 格式化鼠标点击数据
    public static String formatClickJson(String x,String y, String type){
        ClickJson clickJson = new ClickJson();
        clickJson.setX(x);
        clickJson.setY(y);
        clickJson.setType(type);
        String jsonText;
        jsonText = getJson(clickJson);
        assert jsonText.equals("ERROR"):"获得错误信息";
        return jsonText;
    }

    // 格式化游戏内的方块更新数据
    public static String formatTransmitToJsonData(String x,String y,String value){
        return getJson(new UpdateGameBlockJson(x,y,value));
    }
    // 获得json的字典
    public static Map getJsonMap(String value) throws IOException{
            return mapper.readValue(value, new TypeReference<Map<String, Object>>(){});
    }
    // 获得服务器返回的数据类型
    public static String getServerResponseType(String data) throws IOException{
        return getJsonMap(data).get("type").toString();
    }
    // 从对象中获得json的数据
    private static String getJson(Object object){
        String restr = null;
        try{
            restr = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        } finally {
            if (restr == null) restr = "ERROR";
        }
        return restr;
    }
    // 获得updateGame对应的对象
    public static UpdateGameBlockJson getUpdateGameBlockObject(String data) throws IOException{
        return mapper.readValue(data,UpdateGameBlockJson.class);
    }

    // 获得gameGrade字符串
    public static String getGameGrade(String gameOverType,String opponentId,String time,String description){
        GameGrade gameGrade = new GameGrade();
        gameGrade.setGameOverType(gameOverType);
        gameGrade.setOpponentId(opponentId);
        gameGrade.setTime(time);
        gameGrade.setDescription(description);
        return getJson(gameGrade);
    }

    // 获得游戏结束字符串
    public static String getGameOverJson(String gameOverType){
        GameOverJson gameOverJson = new GameOverJson();
        gameOverJson.setValue(gameOverType);
        return JsonData.getJson(gameOverJson);
    }
}
