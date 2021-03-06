package com.View;

import com.Config.BlockType;
import com.Config.Config;
import com.Config.EvenType;
import com.event.EventRequest;
import com.event.Handle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MineField extends Handle {

    private int[][] map; // 游戏的显示地图
    private int rows; // 行数
    private int column; // 列数
    private BufferedImage flagImg = null; // 旗子图像
    private BufferedImage mineImg = null; // 地雷图像
    private innerPanel mineFieldJpanel = null; // 雷区显示面板

    public MineField(){
        init();
    }

    // 初始化函数
    private void init(){
        // 初始化

        rows = Config.getRows();
        column = Config.getColumn();
        map = new int[rows][column];
        setDefaultMap();
        try {
            // 获得图片
            flagImg = ImageIO.read(new File(Config.getFlagImagePath()));
            mineImg = ImageIO.read(new File(Config.getMineImagePath()));
        }catch (Exception e){
            e.printStackTrace();
            // System.out.println(e.getStackTrace().toString());
        }
        mineFieldJpanel = new innerPanel();
        // 设置大小
        mineFieldJpanel.setPreferredSize(new Dimension(column*40, rows*40));
        mineFieldJpanel.setVisible(true);

    }

    // 设置初始化的地图
    private void setDefaultMap(){
        for (int i=0;i<rows;i++)
            for (int j=0;j<column;j++)
            {
                map[i][j] = BlockType.UNDEFINE;
            }
    }
    // 设置鼠标监听
    public void setMouseListener(MouseListener mouseListener){
        mineFieldJpanel.addMouseListener(mouseListener);
    }

    // 更新地图函数
    private void update(String s){
        // 更新地图信息;
        // 分割value;
        String[] data = getFormatData(s);
        // 获得x.y.value;
        int x = Integer.parseInt(data[0]);
        int y = Integer.parseInt(data[1]);
        int value = Integer.parseInt(data[2]);
        map[x][y] = value;
        System.out.println("更新地图"+s);
        mineFieldJpanel.repaint(); // 重新绘制
    }

    // 获得地图内容
    public JPanel getMineFieldJpanel(){
        return mineFieldJpanel;
    }

    // 获得 格式化后的信息
    private String[] getFormatData(String s){
        return s.split("\\|");
    }

    // 恢复默认
    public void setDefault(){
        setDefaultMap();
        mineFieldJpanel.repaint();
    }
    @Override
    public void handleRequest(EventRequest request) {
        // 处理请求 UPDATE
        //  System.out.println("MineField收到请求"+request.getEventType()+request.getEventData());
        if (request.getEventType().equals(EvenType.UPDATE)){
            String s = request.getEventData();
            update(s);
        }else if (this.successor != null){
            // 传递请求
            this.successor.handleRequest(request);
        }
    }

    // 内部面板类
    private class innerPanel extends JPanel {
        // 雷区地图绘制
        innerPanel(){
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.black);
            int rowEndPoint = column*40; // 绘制列数的终点
            int columnEndPoint = rows*40; // 绘制行数的终点
            // 绘制行数
            for (int i =0;i <= rows; i++  ){
                g.drawLine(0, 40*i, rowEndPoint, 40*i);
            }
        // 绘制列数
        for (int i = 0; i <= column; i++) {
            g.drawLine(40*i,0,40*i,columnEndPoint);
        }
        // 绘制雷区各个模块
        for (int i=0;i< rows;i++)
            for (int j = 0; j < column; j++){
                int type = map[i][j];
                if (type == BlockType.EMPTY){
                    // 绘制白色的
                    g.setColor(Color.WHITE);
                    g.fillRect(j*40+2,i*40+2,37,37);
                    g.setColor(Color.BLACK);
                } else if (type == BlockType.FLAG) {
                    // 绘制旗子
                    g.drawImage(flagImg,j*40,i*40,mineFieldJpanel);
                } else if (type == BlockType.MINE) {
                    // 绘制地雷
                    g.drawImage(mineImg,j*40,i*40,mineFieldJpanel);
                } else if (type == BlockType.UNDEFINE) {
                    // 绘制未定义的方块
                    g.setColor(Color.GRAY);
                    g.fillRect(j*40+2,i*40+2,37,37);
                    g.setColor(Color.BLACK);
                } else {
                    // 绘制数字
                    g.setFont(new Font("黑体", Font.BOLD, 20));
                    String numstr = Integer.toString(type);
                    g.drawString(numstr, j*40+15, i*40-15+40);
                }
            }
        }
    }
}
