import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Client extends JFrame {
    private JPanel contentPane;
    int rows = 15;
    int column  = 10;
    int minenumber = 5; // 雷的数目
    int[][] map;
    MineField mineField = new MineField();
    public Client() throws HeadlessException {
        map = new int[rows][column]; // 游戏地图
        InitMap();// 初始化雷区
        contentPane = (JPanel) this.getContentPane();
        this.setSize(new Dimension(540, 670));
        this.setTitle("客户端");
        contentPane.setLayout(null);
        contentPane.add(mineField);
    }

    // 雷区类
    class MineField extends JPanel {
        BufferedImage flagimg = null; // 旗子图像
        BufferedImage mineimg = null; // 地雷图像
        public MineField() {
            this.addMouseListener( new MyMouseListenr());
            this.setSize(new Dimension(540, 670));
            this.setBackground(Color.cyan);
            try{
                flagimg = ImageIO.read(new File("flag.png"));
                mineimg = ImageIO.read(new File("mine.png"));
            }catch (Exception e){
                System.err.println(e.getStackTrace().toString());
            }

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
                        // 什么都不要做
                        continue;
                    }else if (status == -1){
                        // 绘制旗子
                        g.drawImage(flagimg,j*40,i*40,this);
                    }else if (status <= -2){
                        // 绘制地雷
                        g.drawImage(mineimg,j*40,i*40,this);
                    }
                    else if (status > 0){
                        // 绘制数字
                        g.setFont(new Font("黑体",Font.BOLD,20));
                        String numstr = Integer.toString(status);
                        g.drawString(numstr,j*40+15,i*40-15+40);
                    }
                }
        }

        @Override
        public void printComponent(Graphics g) {
//            super.printComponent(g);
//            // // 绘制雷区
//            g.setColor(Color.black);
//            //for (int i =0; i<10; i ++) {
//            g.drawLine(0,0,100,100);
//           // }
//            g.fillOval(10,10,10,10);

        }
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
            if (clicknum == 1) { // 按下鼠标左键
               // x+=100;
                System.out.println("按下鼠标左键");
                map[clickrow][clickcolumn] += 1;
                repaint();
            }else if (clicknum == 3){ // 按下鼠标右键
                //x-=100;
                // 如果该点的值为-1，则改为0，取消旗子标记
//                if(map[clickrow][clickcolumn] == -1){
//                    map[clickrow][clickcolumn] = 0;
//                }
                map[clickrow][clickcolumn] -= 1;
                repaint();
                System.out.println("按下鼠标右键");
            }else { // 按下其他键位
                System.out.println(clicknum);
            }

            System.out.println(clickrow+":"+clickcolumn);

        }
    }
private void  InitMap(){
        for (int i = 0;i<rows;i++)
            for (int j =0;j<column;j++){
                map[i][j] = 1; // 初始化为

            }
}
    public static void main(String[] argv){
        System.out.println("Hello World");
        Client frm = new Client();
        frm.setVisible(true);
    }
}