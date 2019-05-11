package com.com.View;

import javax.swing.*;
import java.awt.*;

public class MineField extends JPanel {

    int[][] map; // 游戏的显示地图
    int rows; // 行数
    int column; // 列数
    public MineField() {

    }
    public MineField(int[][] map){
        this.map = map; // 外部赋值方式设置地图
    }
    private void init(){
        // 初始化
        if (map!=null){
            // 从地图中获得行数和列数
        }
    }
    public void setMap(int[][] map){
       this.map = map; // 设置地图
    }
    public void updata(int x ,int y, int value){
        // 更新地图信息
        map[x][y] = value;
        this.repaint(); // 重新绘制
    }
    @Override
    public void paint(Graphics g) {
        // 雷区地图绘制
        super.paint(g);


    }

}
