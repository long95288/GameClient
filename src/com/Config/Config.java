package com.Config;

import java.awt.*;

/*
 * 配置类
 */
public class Config {
	private int[][] map; // 地图
	private static int rows = 6;  //
	private static int column = 7; //
	private static int minenumber = 2; // 雷的数量
	private static String mineImagePath ="mine.png";
	private static String flagImagePath="flag.png";
	private static String ip = "192.168.1.104";
	private static int port = 8080;

	public static String getFlagImagePath() {
		return flagImagePath;
	}

	public static void setFlagImagePath(String flagImagePath) {
		Config.flagImagePath = flagImagePath;
	}

	public static String getMineImagePath() {
		return mineImagePath;
	}

	public static void setMineImagePath(String mineImagePath) {
		Config.mineImagePath = mineImagePath;
	}

	public static int getPort() {
		return port;
	}

	public static String getIp() {
		return ip;
	}

	Config(){
		init();
	}
	private void init(){
		// rows = 5; // 5行4列
		// column = 4; //
		//minenumber = 3; // 3个雷
		// map = new int[rows][column]; // 实例
//		for (int i=0;i<rows;i++)
//			for (int j=0;j<column;j++){
//				// 默认map的数据为UNDEFINE
//				map[i][j] = BlockType.UNDEFINE;
//			}
	}

	public int[][] getMap() {
		// 获得地图
		return map;
	}
	// 获得列数
	public static int getColumn() {
		return column;
	}
	// 获得行数
	public static int getRows() {
		return rows;
	}
	// 获得雷的数目
	public static int getMineNumber() {
		return minenumber;
	}
	// 获得首页面板的宽和高的
    public static Dimension getIndexFrameSize(){
	    // 根据雷区的数据来初始化面板
	    return new Dimension(column*40*2+200,rows*40+130);
    }
    // 控制面板的宽和高
    public static Dimension getOperatePanelSize(){
	    return new Dimension(100,200);
    }
    // 控制面板的组件大小
    public static Dimension getOperateComponentSize(){
	    // 宽为100 高为40
	    return new Dimension(100,40);
    }
    // 获得的大标签
    public static Font getLabelFont(){
	    return new Font("黑体",Font.BOLD,24);
    }
    // 获得居中的point
    public static Point getCenterPoint(double frameWidth,double frameHeight){
	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Dimension screenSize = toolkit.getScreenSize();
	    double screenWidth = screenSize.getWidth();
	    double screenHeight = screenSize.getHeight();
	    return new Point((int)(screenWidth-frameWidth)/2,(int)(screenHeight-frameHeight)/2);
    }
}
