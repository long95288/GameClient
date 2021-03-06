package com.event;

import com.Config.BlockType;
import com.Config.Config;
import com.Config.EvenType;
import com.JsonData.JsonData;
import com.Store.GameOverType;
import com.Store.Store;

import java.awt.event.MouseEvent;
import java.util.Map;

// 核心处理类
public class Core extends Handle {
    private int[][] map;
    private int rows;
    private int column;
    private int mineNumber; // 雷的数量
    private Mine[] mines; // 雷的实例
    private int flagNumber; // 旗子的数量

    public Core(){
        // this.map = map;
        init(); // 初始化
    }

    // 初始化
    private void init(){
        // Config config = new Config();
        rows = Config.getRows(); // 获得行数
        column = Config.getColumn(); // 获得列数
        mineNumber = Config.getMineNumber(); // 获得雷的数目
        flagNumber = mineNumber; // 获得旗子的初始值
        // 设置地雷
        SetMines();
    }

    // 设置地雷
    private void SetMines(){
        map = new int[rows][column]; // 游戏地图
        mines = new Mine[mineNumber]; // 定义雷的实列
        // 初始化为-3
        for (int i = 0;i<rows;i++)
            for (int j =0;j<column;j++){
                map[i][j] = BlockType.UNDEFINE; // 初始化为未定义状态
            }
        // 随机数设置雷区。设置每个雷的位置 当雷的数目比地图的数目还多的时候会发生死循环，待解决
        for (int i = 0; i< mineNumber; i++){
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
        for (int i = 0; i< mineNumber; i++){
            System.out.println("地雷数据:"+mines[i].getX()+"|"+mines[i].getY());
        }
    }
    // 重新布雷和旗子
    public void resetMines(){
        flagNumber = Config.getMineNumber();
        SetMines();
        throwFlagUpdateRequest(); // 发送更新请求
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
            for (int i = 0; i < mineNumber; i++){
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
        if (number <= mineNumber){
            // 当剩余的方块数小于等于地雷数，便可以判断为游戏结束
            return true;
        }
        return false;
    }

    // 向职责链发送UPDATE请求和SENDDATA请求
    private void ThrowUpdateRequest(int x,int y,int value){
        // 更新本人雷区游戏数据请求，
        String requestData = setFormatData(x,y,value);
        // 同步本地数据
        map[x][y] = value;
        // 发送更新雷区请求
        this.successor.handleRequest(new EventRequest(EvenType.UPDATE,requestData));
        // 向服务器发送更新的数据
        // 生成服务器数据
        String sendData = JsonData.formatTransmitToJsonData(String.valueOf(x),String.valueOf(y),String.valueOf(value));
        System.out.println("转发雷区方块数据"+sendData);
        this.successor.handleRequest(new EventRequest(EvenType.SENDDATA,sendData));
    }

    // 发送游戏结束请求
    private void throwGameOverRequest(String data){
        // 向服务器发送游戏结束通知
        String gameOverData = JsonData.getGameOverJson(data);
        this.successor.handleRequest(new EventRequest(EvenType.SENDDATA,gameOverData));
        this.successor.handleRequest(new EventRequest(EvenType.GAMEOVER,data));
    }

    // 发送旗子更新请求
    private void throwFlagUpdateRequest(){
        String data = String.valueOf(flagNumber);
        this.successor.handleRequest(new EventRequest(EvenType.FLAGEUPDATE,data));
    }

    @Override
    public void handleRequest(EventRequest request){
        // 接收鼠标点击事件
        if (request.getEventType().equals(EvenType.CLICK)){
            String data = request.getEventData();

            Map<String,Object> valueMap=null;
            try {
                valueMap = JsonData.getJsonMap(data);
            }catch (Exception e){e.printStackTrace();}
            int x = Integer.parseInt(valueMap.get("x").toString());
            int y = Integer.parseInt(valueMap.get("y").toString());
            int type = Integer.parseInt(valueMap.get("type").toString());
            System.out.println("Core类接收CLICK事件"+data);
            if (type == MouseEvent.BUTTON3){
                // 鼠标右键事件处理
                System.out.println("右键点击");
                if (map[x][y] == BlockType.UNDEFINE){
                    // 旗子数目还剩下->更新雷区请求
                    if (flagNumber > 0){
                        // 将旗子数目减一
                        flagNumber-=1;
                        // 抛出旗子更新请求
                        throwFlagUpdateRequest();
                        // 抛出更新雷区数据请求
                        ThrowUpdateRequest(x,y,BlockType.FLAG);
                        // 同步本地地图数据
                         map[x][y] = BlockType.FLAG;
                    }
                }
            }else if (type == MouseEvent.BUTTON1){
                // 鼠标左键事件处理
                // System.out.println("左键单击");

                // 将要挖开的地区是雷，则结束游戏
                if (isMine(x,y) && map[x][y] == BlockType.UNDEFINE ){
                    // 触雷，游戏结束
                    System.out.println("你触雷了,输了");
                    // 触雷结束游戏
                    GameOver(GameOverType.DEFEAT1);
                }else if (map[x][y] == BlockType.UNDEFINE){
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
                    // 旗子加一
                    flagNumber += 1;
                    // 抛出旗子更新的请求
                    throwFlagUpdateRequest();

                    // 抛出更新雷区数据请求
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
                // 最先扫完雷
                GameOver(GameOverType.WIN1);
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
    private void GameOver(String data){
        for (int i = 0; i < mineNumber; i ++){
            // 设置地图
            ThrowUpdateRequest(mines[i].getX(),mines[i].getY(),BlockType.MINE);
        }
        // 发送游戏结束请求
        throwGameOverRequest(data);
    }

}
