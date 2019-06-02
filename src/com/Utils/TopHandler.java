package com.Utils;

import com.Config.Config;
import com.Config.EvenType;
import com.JsonData.JsonData;
import com.Store.GameOverType;
import com.Store.Store;
import com.View.IndexFrame;
import com.View.LoginPanel;
import com.View.OperatePanel;
import com.conection.Connection;
import com.event.Core;
import com.event.Counter;
import com.event.EventRequest;
import com.event.Handle;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;


// 最高级别的处理者，将处理责任链中各个部分不能处理的事情
public class TopHandler extends Handle {
    private LoginPanel loginPanel;
    private IndexFrame indexFrame;
    private Counter counter;
    private Core core;
    private Connection connection;
    public TopHandler(Core core,IndexFrame indexFrame,LoginPanel loginPanel) {
        this.core = core;
        this.indexFrame = indexFrame;
        this.loginPanel = loginPanel;
        init();
    }

    // 初始化
    private void init(){
        counter = new Counter();
        counter.start(); // 开启计数线程
        // 获得首页时间标签的引用
        counter.setShowTimeLabel(indexFrame.getTimeLabel());
    }
    @Override
    public void handleRequest(EventRequest request) {
       System.out.println("请求类型:"+request.getEventType());
       System.out.println("请求内容:"+request.getEventData());
       String requestType = request.getEventType();
       String data = request.getEventData();
       if (requestType.equals(EvenType.LOGINSUCCESS)){
           // 处理登陆成功
           handleLoginSuccess(data);
       }else if (requestType.equals(EvenType.LOGINFAILURE)){
           // 处理登陆失败
           handleLoginFailure(data);
       }else if (requestType.equals(EvenType.MATCHSUCCESS)){
           // 匹配成功
           handleMatchSuccess(data);
       }else if (requestType.equals(EvenType.GAMEOVER)){
           handleGameOver(data);
       }
    }
    // 发送函数
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    // TODO 处理登陆成功请求、
    private void handleLoginSuccess(String data){
        // 关闭登陆窗口
        loginPanel.delFrame();
        // 设置我方id
        indexFrame.setOwnId(data);
        // 显示棋局信息
        indexFrame.showFrame(); // 显示首页
    }

    // 处理登陆失败请求
    private void handleLoginFailure(String data){
        // 显示提示信息
        String message;
        if ("1".equals(data)){
            message = "密码出错";
        }else if ("2".equals(data)){
            message = "你的账号已经登陆!!";
        }else{ message = "未知错误";}
        JOptionPane.showMessageDialog(null,"登陆失败"+message);
        loginPanel.setDefault(); // 恢复成默认的状态
    }

    //TODO 处理匹配成功请求
    private void handleMatchSuccess(String data){
        // 设置可以点击
        Store.setMouseClickable(true);
        // 设置对手id
        indexFrame.setOpponentId(data);
        // 设置操作面板的按钮
        indexFrame.setStartBtnText("对战中..");
        // 启动计时
        counter.StartCount(); // 开始计时
    }
    // TODO 发送成绩到服务器
    private void sendGameGrade(String type,String opponentId,String time,String description){
        String grade = JsonData.getGameGrade(type,opponentId,time,description);
        if (connection!=null) connection.sendData(grade);
        else{System.out.println("topHandler没有连接器");}
    }

    // TODO 处理游戏结束请求
    private void handleGameOver(String data){
        // 停止计时
        counter.StopCount();
        // 设置雷区不可点击
        Store.setMouseClickable(false);
        // 获得时间
        String time = String.valueOf(counter.getCount());
        String description;
        // 提示信息
        //
        String opponentId = indexFrame.getOpponentId();
        String type = data;
        if (type.equals(GameOverType.WIN1)){
            // 获得时间
            description = "最先扫完雷";
            sendGameGrade(type,opponentId,time,description);
            JOptionPane.showMessageDialog(null,"游戏结束,你最先扫完雷");
        }else if (type.equals(GameOverType.WIN2)){
            description = "对方碰雷雷了";
            sendGameGrade(type,opponentId,time,description);
            JOptionPane.showMessageDialog(null,"游戏结束，对方碰雷了");
        }else if (type.equals(GameOverType.DEFEAT1)){
            //
            time = "-1";
            description = "你碰雷了";
            sendGameGrade(type,opponentId,time,description);
            JOptionPane.showMessageDialog(null,"游戏结束,你碰雷了");
        }else if (type.equals(GameOverType.DEFEAT2)){
            time = "-1";
            description = "你手不够快";
            sendGameGrade(type,opponentId,time,description);
            JOptionPane.showMessageDialog(null,"游戏结束,你手不够快哦");
        }
        /*游戏结束请求
        * 1、type: GAMEOVER data: win1
        * 2、type: GAMEOVER data: win2
        * 3、type: GAEMOVER data: defeat1
        * 4、type: GAMEOVER data: defeat2
        * */
        /* 游戏结束发送到服务器的数据
        *  1、赢(Core) 我方最先结束 WIN1  发送数据为 type: GameGrade value: win1 description: costTime
        *  2、赢(Connection) 对方先碰雷 WIN2  发送数据为 type: GameGrade value: win2 description: 对方先碰雷
        *  3、输(Core) 我方碰雷 DEFEAT1 发送数据为 type: GameGrade value: defeat1 - 我方碰雷
        *  4、输(Connection) 对方先结束 DEFEAT2 发送数据为 type: GameGrade value: defeat2 - 对方先结束
        */

        // 将面板恢复成默认状态
        indexFrame.setDefault();
        // 重新设置雷区
        core.resetMines();
    }
}
