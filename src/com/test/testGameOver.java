package com.test;

import com.JsonData.JsonData;
import com.Store.GameOverType;

public class testGameOver {
    public static void main(String[] args){
        System.out.println(JsonData.getGameGrade(
                GameOverType.WIN1,
                "100",
                "300",
                "第一种胜利"
        ));

        System.out.println(JsonData.getGameOverJson(GameOverType.DEFEAT1));
    }
}
