package cqupt.swxxxy.oyc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * ��ȡ�����ļ���Ϣ����ȡWindows��linux�������ļ��ϴ�·���������ļ�·��
 * 
 * @author Administrator
 *
 */
public class ReadFileProperties {
    /**
     * ��ȡ�����ļ���Ϣ
     * 
     * @return
     */
    public Map<String, String> getProperties() {
	// ������־�������
	Properties pro = new Properties();
	// ����������
	InputStream in = null;
	// ͨ��hashMap�洢fileproperties�ļ���Ϣ
	Map<String, String> map = new HashMap<String, String>();
	try {
	    in = getClass().getResourceAsStream(
		    "/conf/file.properties");
	    // ��ȡ�ļ���Ϣ
	    pro.load(in);
	    // ��ȡö�ٶ���
	    Enumeration en = pro.propertyNames();
	    // ö���ļ����������
	    while (en.hasMoreElements()) {
		String key = (String) en.nextElement();
		String value = pro.getProperty(key);
		map.put(key, value);
	    }

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
	    //����ļ���������Ϊ�գ��ر��ļ�������
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
