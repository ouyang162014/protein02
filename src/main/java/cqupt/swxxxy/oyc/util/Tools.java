package cqupt.swxxxy.oyc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 * @author Administrator
 *
 */
public class Tools {
	
	/**
	 * 获取正电荷数目
	 * @param num
	 * @return
	 */
	public String getAddNum(int num) {
		StringBuilder sb = new StringBuilder();
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				sb.append("+");
			}
			return sb.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取系统当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		//设置日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// new Date()为获取当前系统时间
		String time = df.format(new Date());
		return time;
	}
}
