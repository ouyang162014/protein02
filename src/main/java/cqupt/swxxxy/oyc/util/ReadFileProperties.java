package cqupt.swxxxy.oyc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 读取配置文件信息，获取Windows及linux环境下文件上传路径和生成文件路径
 * 
 * @author Administrator
 *
 */
public class ReadFileProperties {
    /**
     * 读取配置文件信息
     * 
     * @return
     */
    public Map<String, String> getProperties() {
	// 创建日志输出对象
	Properties pro = new Properties();
	// 创建输入流
	InputStream in = null;
	// 通过hashMap存储fileproperties文件信息
	Map<String, String> map = new HashMap<String, String>();
	try {
	    in = getClass().getResourceAsStream(
		    "/conf/file.properties");
	    // 读取文件信息
	    pro.load(in);
	    // 获取枚举对象
	    Enumeration en = pro.propertyNames();
	    // 枚举文件中相关属性
	    while (en.hasMoreElements()) {
		String key = (String) en.nextElement();
		String value = pro.getProperty(key);
		map.put(key, value);
	    }

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
	    //如果文件输入流不为空，关闭文件输入流
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    }
	}
	return map;
    }
}
