package com.event;

import javax.swing.*;

public class Counter extends Thread{
    private boolean gameStop = true;
    private long gameStartTime = 0;
    private JLabel showTimeLabel = null;
    private int gameTime = 0; // 游戏时间
    public Counter() {
        // this.start();
    }

    // 设置时间显示的标签
    public void setShowTimeLabel(JLabel showTimeLabel){
        this.showTimeLabel = showTimeLabel;
    }

    // 设置开始计数
    public void StartCount(){
        gameStartTime = System.currentTimeMillis();
        gameStop = false;
    }

    // 设置停止计数
    public void StopCount(){
        gameStop = true;
    }

    // 获得时间数
    public int getCount(){
        return this.gameTime;
    }

    @Override
    public void run() {
//        super.run();
        long elpased = 0;
       while (true) {
            if (!gameStop) {
                // 游戏开始
                elpased = System.currentTimeMillis() - gameStartTime; // 已经过了多少时间
                gameTime = (int) (elpased / 1000);  // 秒数
                String showTime = String.format("%03d", gameTime);
                // 设置时间标志
                if (showTimeLabel != null){
                    showTimeLabel.setText(showTime);
                } else {
                    // 未设置显示组件
                    System.out.println("未设置显示时间组件");
                }
            }
            try {
                sleep(1000); // 每500毫秒更新一次
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);// 退出
            }
        }

    }
}
