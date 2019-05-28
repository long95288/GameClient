package com.Utils;

import com.Config.EvenType;
import com.View.IndexFrame;
import com.View.LoginPanel;
import com.View.OperatePanel;
import com.event.Counter;
import com.event.EventRequest;
import com.event.Handle;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;


// 最高级别的处理者，将处理责任链中各个部分不能处理的事情
public class TopHandler extends Handle {
    private LoginPanel loginPanel;
    private IndexFrame indexFrame;
    private OperatePanel operatePanel; // 操作面板
    private Counter counter;
    public TopHandler() {
        init();
    }

    // 初始化
    private void init(){
        counter = new Counter();
    }

    // 设置登陆窗口
    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    // 设置首页面
    public void setIndexFrame(IndexFrame indexFrame) {
        this.indexFrame = indexFrame;
    }

    // 设置操作界面
    public void setOperatePanel(OperatePanel operatePanel) {
        this.operatePanel = operatePanel;
    }

    // 关闭登陆页面
    private void closeLoginPanel(){
        loginPanel.delFrame();
    }

    // 打开首页
    private void openIndexFrame(){
        indexFrame.showFrame();
    }

    // 设置我方ID
    private void setOwnId(String id){
        indexFrame.setOwnId(id);
    }

    // 设置对方ID
    private void setOpponentId(String id){
        indexFrame.setOpponentId(id);
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
       }
    }

    // TODO 处理登陆成功请求、
    private void handleLoginSuccess(String data){
        // 关闭登陆窗口
        loginPanel.delFrame();
        // 设置我方id
        String id = "dd";
        indexFrame.setOwnId(id);
        // 显示棋局信息
        indexFrame.showFrame(); // 显示首页

    }

    // 处理登陆失败请求
    private void handleLoginFailure(String data){
        // 显示提示信息
        JOptionPane.showMessageDialog(null,"登陆失败");
    }

    //TODO 处理匹配成功请求
    private void handleMatchSuccess(String data){
        // 设置对手id
        indexFrame.setOpponentId("对手id");
        // 设置操作面板的按钮
        // 启动计时
        counter.setShowTimeLabel(operatePanel.getTimeLbl());
        counter.start(); // 开启线程
        counter.StartCount(); // 开始计时

    }
}
