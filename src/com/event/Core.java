package com.event;

import com.Config.BlockType;

// 核心处理类
public class Core {
    int[][] map;
    int rows;
    int column;
    int minenumber; // 雷的数量
    Mine[] mines; // 雷的实例
    Core(int[][] map){
        this.map = map;
    }
    public void SetMines(){
        map = new int[rows][column]; // 游戏地图
        mines = new Mine[minenumber]; // 定义雷的实列
        // 初始化为-3
        for (int i = 0;i<rows;i++)
            for (int j =0;j<column;j++){
                map[i][j] = BlockType.UNDEFINE; // 初始化为未定义状态
            }

        // 随机数设置雷区。设置每个雷的位置 当雷的数目比地图的数目还多的时候会发生死循环，待解决
        for (int i = 0;i< minenumber;i++){
            mines[i] = new Mine();
            while (true){
                // 随机产生一个雷的x 和 y
                int randomX = (int)(0+Math.random()*(rows));
                int randomY = (int)(0+Math.random()*(column));
                System.out.println("产生雷区:"+randomX+"|"+randomY);
                // 如果没有设置过
                boolean isSet = false;
                for(int j =0;j<i;j++){
                    if(mines[j].getX() == randomX && mines[j].getY() == randomY){
                        // 设置过了,重新产生随机数
                        isSet = true;
                    }
                }
                if (!isSet){ // 没有设置过则设置
                    mines[i].setXY(randomX,randomY);
                    break;
                }
                System.out.println("重复雷区:"+randomX+"|"+randomY);
            }
        }
        for (int i=0;i< minenumber;i++){
            System.out.println("地雷数据:"+mines[i].getX()+"|"+mines[i].getY());
        }
    }
    // 雷类 一个雷
    class Mine {
        private int x1 =0;
        private int y1 =0;
        Mine(){
        }
        public void setXY(int x,int y){
            this.x1 = x;
            this.y1 = y;
        }
        public int getX(){return x1;}
        public int getY(){return y1;}
    }

}
