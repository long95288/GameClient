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
    int rows = 15;
    int column  = 10;
    int minenumber = 10; // 雷的数目
    int[][] map; // 雷区地图
    Mine[] mines; // 地雷数组
    Boolean GameEnd = false; // 游戏结束标记
    MineField mineField;
    public Client() throws HeadlessException {
        contentPane = (JPanel) this.getContentPane();
        this.setSize(new Dimension(540, 670));
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
            this.setSize(new Dimension(540, 670));
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
            // mines[0].setXY(2,2);
            // mines[8].setXY(4,4);
            //
            for (int i=0;i< minenumber;i++){
                System.out.println("地雷数据:"+mines[i].getX()+"|"+mines[i].getY());
            }
        }


        // 判断当前点是否是雷的函数
//        public boolean isMine(int x,int y){
//            for (int i =0 ;i < minenumber;i++){
//                if ((mines[i].getX() == x) && (mines[i].getX() == y)){
//                    return true; // 当前坐标是雷
//                }
//            }
//            return false;
//        }
    }

    // 鼠标监听类
    class MyMouseListenr extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            //
            int clickX = e.getX(); // 获得单击的X坐标
            int clickY = e.getY(); // 获得单击的Y坐标

            // 计算出单击的格数
            int clickrow = clickY/40; // 行数
            int clickcolumn = clickX/40; // 列数
            int clicknum = e.getButton();
            System.out.println("单击:"+clickrow+"|"+clickcolumn);
            if (clicknum == 1) { // 按下鼠标左键
                if (map[clickrow][clickcolumn] == -1) {
                    // 如果是旗子，去掉旗子
                    map[clickrow][clickcolumn] = 0;
                }else if (isMine(clickrow,clickcolumn)) {
                    // 如果触雷，则结束游戏
                    GameOver();
                    System.out.println("碰雷");
                }else if (map[clickrow][clickcolumn] == 0){
                    // 设置雷区
                    SetMap(clickrow,clickrow);
                    // map[clickrow][clickcolumn] = dd; // 设置周围雷区
                }
                System.out.println("按下鼠标左键");
                repaint();
            }else if (clicknum == 3){ // 按下鼠标右键
                //x-=100;
                // 如果该点为0，则打上旗子标记
                if (map[clickrow][clickcolumn] == 0){
                    map[clickrow][clickcolumn] = -1;
                }
                // 如果该点的值为-1，则改为0，取消旗子标记
//                if(map[clickrow][clickcolumn] == -1){
//                    map[clickrow][clickcolumn] = 0;
//                }
                // map[clickrow][clickcolumn] -= 1;
                repaint();
                System.out.println("按下鼠标右键");
            }else { // 按下其他键位
                System.out.println(clicknum);
            }

            System.out.println(clickrow+":"+clickcolumn);

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
            // 如果x不合法，返回-1
            if (x < 0 || x >= column){return -1;}
            // 如果y不合法，返回-1
            if (y<0 || y >= rows){return -1;}
            for (int i=-1;i <= 1;i++)
                for (int j=-1;j <= 1;j++) {
                    // 一个坐标内周围八个方块
                    if (isMine(x+i,y+j)){
                        number++;
                    }
                }
            System.out.println("周围雷区数"+number);
            return number;
        }
        // 设置x,y周围的八块地方的雷区显示
        public void SetMap(int x,int y){
            int minenum = 0;
            for (int i=-1;i<=1;i++)
                for (int j=-1;j<=1;j++){
                    minenum =GetSurroundMines(x+i,y+j);
                    if (minenum > 0){
                        map[x+i][y+j] = minenum;
                    }else if (minenum ==0){
                        // 设置
                        SetMap(x+i,y+j);
                    }else if (minenum == -1){
                        // 越界了
                        continue;
                    }


                }
        }
    }
    public static void main(String[] argv){
        System.out.println("Hello World");
        Client frm = new Client();
        frm.setVisible(true);
    }
}