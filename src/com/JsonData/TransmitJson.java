package com.JsonData;

import com.Config.SendDataType;

public class TransmitJson {
    // 游戏方块数据
    private String type = SendDataType.GAMEBLOCKDATA;
    private String x; // x点坐标
    private String y; // y点坐标
    private String value; // 需要更新的值
    public TransmitJson(){}
    public TransmitJson(String x, String y, String value){
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }
}
