package cqupt.swxxxy.oyc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ������
 * @author Administrator
 *
 */
public class Tools {
	
	/**
	 * ��ȡ�������Ŀ
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
	 * ��ȡϵͳ��ǰʱ��
	 * @return
	 */
	public static String getCurrentTime(){
		//�������ڸ�ʽ
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// new Date()Ϊ��ȡ��ǰϵͳʱ��
		String time = df.format(new Date());
		return time;
	}
}
