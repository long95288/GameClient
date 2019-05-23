package com.event;

import javax.swing.*;

public class Couter extends Thread{
    private boolean gamestop = true;
    private long gameStartTime = 0;
    private JLabel showTimeLabel = null;
    private int gameTime = 0; // 游戏时间
    public Couter() {
        setDaemon(true);
    }

    // 设置时间显示的标签
    public void setShowTimeLabel(JLabel showTimeLabel){
        this.showTimeLabel = showTimeLabel;
    }
    // 设置开始计数
    public void StartCount(){
        gameStartTime = System.currentTimeMillis();
        gamestop = false;
    }
    // 设置停止计数
    public void StopCount(){
        gamestop = true;
    }
    // 获得时间数
    public int getCount(){
        return this.gameTime;
    }

    @Override
    public void run() {
//        super.run();
       while (true) {
            if (!gamestop) {
                // 游戏开始
                long elpased = System.currentTimeMillis() - gameStartTime; // 已经过了多少时间
                // 格式化时间为 秒数：毫秒
//                int milli = (int) (elpased % 1000); // 毫秒数
//                elpased = elpased / 60;
                gameTime = (int) (elpased / 1000);  // 秒数
                String showTime = String.format("%03d", gameTime);
                // 设置时间标志
                showTimeLabel.setText(showTime);
                // System.out.println("时间" + showTime);
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
