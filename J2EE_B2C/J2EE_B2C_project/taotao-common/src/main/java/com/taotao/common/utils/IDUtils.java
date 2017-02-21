package com.taotao.common.utils;

import java.util.Random;

/**
 * 各種ID生成策略
 * 
 * @author mbc1996
 *
 */
public class IDUtils {
	/**
	 * 生成圖片名字
	 * 毫秒級的時間戳+3位隨機數
	 * @return
	 */
	public static String generateImageName() {
		// 取当前时间的长整形值毫秒級別
		long millis = System.currentTimeMillis();
		// long millis = System.nanoTime();
		// 加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		// 如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		return str;
	}
	
	/**
	 * 生成商品ID
	 * 毫秒級別的時間戳+2位隨機數
	 * @return
	 */
	public static long generateItemId(){
		long millis = System.currentTimeMillis();
		Random random = new Random();
		int end2 = random.nextInt(99);
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}
	
	
}
