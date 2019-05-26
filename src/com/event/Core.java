package com.event;

import com.Config.BlockType;
import com.Config.Config;
import com.Config.EvenType;

import java.awt.event.MouseEvent;

// 核心处理类
public class Core extends Handle {
    private int[][] map;
    private int rows;
    private int column;
    private int minenumber; // 雷的数量
    private Mine[] mines; // 雷的实例
    public Core(){
        // this.map = map;
        init(); // 初始化
    }

    // 初始化
    private void init(){
        // Config config = new Config();
        rows = Config.getRows(); // 获得行数
        column = Config.getColumn(); // 获得列数
        minenumber = Config.getMineNumber(); // 获得雷的数目
        // 设置地雷
        SetMines();
    }

    // 设置地雷
    private void SetMines(){
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
                // System.out.println("产生雷区:"+randomX+"|"+randomY);
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
                // System.out.println("重复雷区:"+randomX+"|"+randomY);
            }
        }
        for (int i=0;i< minenumber;i++){
            System.out.println("地雷数据:"+mines[i].getX()+"|"+mines[i].getY());
        }
    }

    // 获得坐标x,y周围的雷数
    private int GetSurroundMines(int x, int y) {
        int number = 0;
        int tmpx = 0;
        int tmpy = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++){
                tmpx = x + i;
                tmpy = y + j;
                if (isMine(tmpx,tmpy) && isValid(tmpx,tmpy)){
                    number++;
                }
            }
        System.out.println("x"+x+"y"+"mines"+number);
        return number;
    }

    // 判断坐标x,y是不是雷
    private boolean isMine(int x,int y){
            for (int i =0 ;i < minenumber;i++){
                if ((mines[i].getX() == x) && (mines[i].getY() == y)){
                    return true; // 当前坐标是雷
                }
            }
            return false;
    }

    // 设置x,y周围的八块地方的雷区显示
    private void SetSurround(int x,int y){
        int minenum = 0;
        int tmpx = 0;
        int tmpy = 0;
        for (int i=-1; i <= 1; i++)
            for (int j=-1;j <= 1;j++){
                tmpx = x+i;
                tmpy = y+j;
                if (isValid(tmpx,tmpy)) {
                    if (map[tmpx][tmpy] == BlockType.UNDEFINE) {
                        // 没有挖开
                        minenum = GetSurroundMines(tmpx, tmpy);
                        if (minenum > 0) {
                            // 设置该点的值
                            ThrowUpdateRequest(tmpx, tmpy, minenum);
                        } else if (minenum == 0) {
                            // 递归挖开
                            ThrowUpdateRequest(tmpx, tmpy, BlockType.EMPTY);
                            SetSurround(tmpx, tmpy);
                        }
                    }
                }
            }
    }

    // 判断该点是否有效
     private boolean isValid(int x ,int y){
        if (x >= 0 && x < rows && y >= 0 && y < column){
            // System.out.println("X"+x+"Y"+y+"该点有效");
            return true;
        }
        // System.out.println("X"+x+"Y"+y+"该点无效");
        return false;
    }

    // 格式化数据 x,y 坐标点，value是该点的值
    private String setFormatData(int x, int y, int value){
        return x+"|"+y+"|"+value;
    }

    // 判断是否已经赢了
    private boolean CheckWin(){
        int number = rows * column; // 初始化为都没有被揭开过的
        for (int i = 0;i < rows;i ++)
            for (int j =0; j < column;j ++){
                if (map[i][j] != BlockType.UNDEFINE  && map[i][j] != BlockType.FLAG){
                    // 去掉揭开了的方块,剩下的就是未揭开的和旗子的
                    number --;
                }
            }
        if (number <= minenumber){
            // 当剩余的方块数小于等于地雷数，便可以判断为游戏结束
            return true;
        }
        return false;
    }

    // 向职责链发送UPDATE请求
    private void ThrowUpdateRequest(int x,int y,int value){
        String requestdata = setFormatData(x,y,value);
        // this.successor.handleRequest(new EventRequest());
        // 同步本地数据
        map[x][y] = value;
        this.successor.handleRequest(new EventRequest(EvenType.UPDATE,requestdata));
    }

    @Override
    public void handleRequest(EventRequest request) {
        // 接收鼠标点击事件
        if (request.getEventType().equals(EvenType.CLICK)){
            String data = request.getEventData();
            String[] values = data.split("\\|"); // 分割值

            // for (int i=0;i<values.length;i++) System.out.println(values[i]);

            int x = Integer.parseInt(values[0]);
            int y = Integer.parseInt(values[1]);
            int type = Integer.parseInt( values[2]);
            System.out.println("Core类接收CLICK事件"+data);
            if (type == MouseEvent.BUTTON3){
                // 鼠标右键事件处理
                System.out.println("右键点击");
                if (map[x][y] == BlockType.UNDEFINE){
                    // 更新雷区请求
                    ThrowUpdateRequest(x,y,BlockType.FLAG);
                    // 同步本地地图数据
                    map[x][y] = BlockType.FLAG;
                }
            }else if (type == MouseEvent.BUTTON1){
                // 鼠标左键事件处理
                // System.out.println("左键单击");

                // 将要挖开的地区是雷，则结束游戏
                if (isMine(x,y) && map[x][y] == BlockType.UNDEFINE ){
                    // 触雷，游戏结束
                    System.out.println("你输了");
                    GameOver();
                }
                //
                if (map[x][y] == BlockType.UNDEFINE){
                    // 挖开该点
                    int surroundmines = GetSurroundMines(x,y);
                    if (surroundmines == 0){
                        // 没有雷，挖开周围8块地区
                        ThrowUpdateRequest(x,y,BlockType.EMPTY);
                        // 设置周围的区块
                        SetSurround(x,y);
                    }else {
                        // 显示雷数
                        ThrowUpdateRequest(x,y,surroundmines);
                    }
                }else if (map[x][y] == BlockType.FLAG){
                    // 去掉旗子
                    ThrowUpdateRequest(x,y,BlockType.UNDEFINE);
                    // 同步数据
                    map[x][y] = BlockType.UNDEFINE;
                }

            }else{
                // 其他键位
            }
            // 判断是否已经赢了
            if (CheckWin()){
                // 赢了
                System.out.println("你赢了");
                GameOver();
            }
        }else if (this.successor != null){
            // 将请求传递下去
            this.successor.handleRequest(request);
        }
    }

    // 雷类 一个雷
    class Mine {
        private int x1 =0;
        private int y1 =0;
        Mine(){
        }
        private void setXY(int x,int y){
            this.x1 = x;
            this.y1 = y;
        }
        private int getX(){return x1;}
        private int getY(){return y1;}
    }

    // 游戏结束
    private void GameOver(){
        for (int i = 0; i < minenumber; i ++){
            // 设置地图
            // map[mines[i].getX()][mines[i].getY()] = BlockType.MINE;
            ThrowUpdateRequest(mines[i].getX(),mines[i].getY(),BlockType.MINE);
        }
    }

}
