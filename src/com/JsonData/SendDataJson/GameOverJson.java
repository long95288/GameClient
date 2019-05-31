package com.JsonData.SendDataJson;

import com.Config.SendDataType;
/*游戏结束json
* */
public class GameOverJson {
    private String type = SendDataType.GAMEOVER;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
