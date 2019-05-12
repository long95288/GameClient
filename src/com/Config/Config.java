package com.Config;

/*
 * 配置类
 */
public class Config {
	private int[][] map; // 地图
	private static int rows = 4;  //
	private static int column = 5; //
	private static int minenumber = 10; // 雷的数量
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
	public static int getMinenumber() {
		return minenumber;
	}
}
