package com.JsonData.SendDataJson;

import com.Config.SendDataType;

public class GameGrade {
    private String type = SendDataType.GAMEGRADE;
    private String gameOverType;
    private String opponentId;
    private String time;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGameOverType() {
        return gameOverType;
    }

    public void setGameOverType(String gameOverType) {
        this.gameOverType = gameOverType;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
