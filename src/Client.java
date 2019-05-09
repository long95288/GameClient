import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.CollationElementIterator;

public class Client extends JFrame {
    private JPanel contentPane;
    int rows = 20;
    int column  = 9;
    int minenumber = 20; // 雷的数目
    int[][] map; // 雷区地图
    Mine[] mines; // 地雷数组
    Boolean GameEnd = false; // 游戏结束标记
    MineField mineField;
    public Client() throws HeadlessException {
        contentPane = (JPanel) this.getContentPane();
        this.setSize(new Dimension(1000, 1000));
        this.setTitle("客户端");
        mineField = new MineField();
        contentPane.setLayout(null);
        contentPane.add(mineField);
    }
    // 游戏结束
    public void GameOver(){
        // 设置地雷显示
        for (int i = 0;i < minenumber;i++){
            map[mines[i].getX()][mines[i].getY()] = -2;
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

    // 雷区类
    class MineField extends JPanel {
        BufferedImage flagimg = null; // 旗子图像
        BufferedImage mineimg = null; // 地雷图像

        public MineField() {
            this.addMouseListener(new MyMouseListenr());
            this.setSize(new Dimension(column*40, rows*40));
            this.setBackground(Color.cyan);
            try{
                flagimg = ImageIO.read(new File("flag.png"));
                mineimg = ImageIO.read(new File("mine.png"));
            }catch (Exception e){
                System.err.println(e.getStackTrace().toString());
            }
            SetMines();// 设置雷区
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.black);
            // g.fillOval(10,10,10,10);
            // g.drawLine(0,0,x,y);
            int rowendpoint = column*40; // 绘制列数的终点
            int columnendpoint = rows*40; // 绘制行数的终点

            // 绘制行数
            for (int i =0;i <= rows; i++  ){
                g.drawLine(0,40*i,rowendpoint,40*i);
            }
            // 绘制列数
            for (int i = 0; i <= column; i++) {
                g.drawLine(40*i,0,40*i,columnendpoint);
            }
            // 绘制雷区各个模块
            for(int i = 0;i< rows;i++)
                for (int j =0; j < column;j++){
                    int status = map[i][j];
                    if (status == 0){
                        // 绘制白色的
                        g.setColor(Color.white);
                        g.fillRect(j*40+2,i*40+2,37,37);
                        g.setColor(Color.black);// 换回黑色
                    }else if (status == -1){
                        // 绘制旗子
                        g.drawImage(flagimg,j*40,i*40,this);
                    }else if (status == -2){
                        // 绘制地雷
                        g.drawImage(mineimg,j*40,i*40,this);
                    }else if (status == -3){
                        // 绘制灰色未探索的
                        g.setColor(Color.gray);
                        g.fillRect(j*40+2,i*40+2,37,37);
                        g.setColor(Color.black);
                    }
                    else if (status > 0){
                        // 绘制数字
                        g.setFont(new Font("黑体",Font.BOLD,20));
                        String numstr = Integer.toString(status);
                        g.drawString(numstr,j*40+15,i*40-15+40);
                    }
                }
        }
        // 布雷函数
        private void SetMines(){
            map = new int[rows][column]; // 游戏地图
            mines = new Mine[minenumber]; // 定义雷的实列
            // 初始化为-3
            for (int i = 0;i<rows;i++)
                for (int j =0;j<column;j++){
                    map[i][j] = -3; // 初始化为-3 未知状态
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
    }

    // 鼠标监听类
    class MyMouseListenr extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            System.out.println("xp"+e.getX()+"yp"+e.getY());
            // 计算出单击的格数
            int clickX = e.getY()/40; // 行数
            int clickY = e.getX()/40; // 列数
            int clicknum = e.getButton();
            System.out.println("单击:"+clickX+"|"+clickY);
            if (clicknum == 1 && isValid(clickX,clickY)) { // 按下鼠标左键
                if (map[clickX][clickY] == -1) {
                    // 如果是旗子，去掉旗子
                    map[clickX][clickY] = -3; // 恢复默认灰色
                }else if (isMine(clickX,clickY)) {
                    // 如果触雷，则结束游戏
                    GameOver();
                    System.out.println("碰雷");
                }else if (map[clickX][clickY] == -3){
                    // 挖开
                    int surroundmines = GetSurroundMines(clickX,clickY);
                    if (surroundmines == 0){
                        // 该点没有雷。挖开,并挖开周围的八块区域
                        map[clickX][clickY] = 0;
                        SetSurround(clickX,clickY);
                    } else {
                      // 该点周围有雷,显示数字
                      map[clickX][clickY] = surroundmines;
                    }
                    // map[clickX][clickY] = dd; // 设置周围雷区
                }
                System.out.println("按下鼠标左键");
                repaint();
            }else if (clicknum == 3 && isValid(clickX,clickY)){ // 按下鼠标右键
                //x-=100;
                // 如果该点为0，则打上旗子标记
                if (map[clickX][clickY] == -3){
                    map[clickX][clickY] = -1;
                }
                repaint();
                System.out.println("按下鼠标右键");
            }else { // 按下其他键位
                System.out.println(clicknum);
            }

            System.out.println(clickX+":"+clickY);

        }

        // 判断是不是雷区
        public boolean isMine(int x,int y){
            for (int i =0 ;i < minenumber;i++){
                if ((mines[i].getX() == x) && (mines[i].getY() == y)){
                    return true; // 当前坐标是雷
                }
            }
            return false;
        }
        // 获得x,y四方的雷区数
        public int GetSurroundMines(int x,int y){
            int number = 0;
            int tmpx =0;
            int tmpy =0;
            // 如果x不合法，返回-1
            for (int i=-1;i <= 1;i++)
                for (int j=-1;j <= 1;j++) {
                    // 一个坐标内周围八个方块
                    tmpx = x+i;
                    tmpy = y+j;
                    if (isValid(tmpx,tmpy) && isMine(tmpx,tmpy)){
                       number ++;
                    }
                }
            System.out.println("周围雷数"+number);
            return number;
        }
        // 设置x,y周围的八块地方的雷区显示
        public void SetSurround(int x,int y){
              int minenum = 0;
              int tmpx = 0;
              int tmpy = 0;
            for (int i=-1;i<=1;i++)
                for (int j=-1;j<=1;j++){
                    tmpx = x+i;
                    tmpy = y+j;
                    if (isValid(tmpx,tmpy)){
                        if (map[tmpx][tmpy] == -3){
                            // 没有挖开
                            minenum =GetSurroundMines(tmpx,tmpy);
                            if (minenum >0){
                                // 设置该点的值
                                map[tmpx][tmpy] =minenum;
                            }
                            else if (minenum == 0){
                                map[tmpx][tmpy] = 0;
                                System.out.println("X:"+tmpx+"Y:"+tmpy+"周围无雷");
                                SetSurround(tmpx,tmpy);
                            }

                        }
                    }
                }
        }
        // 判断该点是否有效
        public boolean isValid(int x ,int y){
            if (x >= 0 && x < rows && y >= 0 && y < column){
                System.out.println("X"+x+"Y"+y+"该点有效");
                return true;
            }
            //
            System.out.println("X"+x+"Y"+y+"该点无效");
            return false;
        }
    }
    public static void main(String[] argv){
        System.out.println("Hello World");
        Client frm = new Client();
        frm.setVisible(true);
    }
}