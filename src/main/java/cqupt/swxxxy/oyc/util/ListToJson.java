package cqupt.swxxxy.oyc.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import cqupt.swxxxy.oyc.bean.FileInfo;

public class ListToJson {

	/**
	 * ��jsonת��ΪList
	 * @param fileInfo
	 * @return
	 */
	 public static List<FileInfo> converFileInfoFormString(String fileInfo){  
	        if (fileInfo == null || fileInfo.equals(""))  
	            return new ArrayList();  
	        JSONArray jsonArray = JSONArray.fromObject(fileInfo);  
	        List<FileInfo> list = (List) JSONArray.toCollection(jsonArray,  FileInfo.class);  
	        return list;  
	    }  
	      
	    /** 
	     *  
	     * ��һ�� FileInfo �����List ����Json�ִ� 
	     * �Ǹ��ݿͻ���ҳ���û��������Ϣ���ɵ� 
	     */  
	    public static String getFileInfoString(List<FileInfo> fileInfoList) {  
	        JSONArray jsonarray = JSONArray.fromObject(fileInfoList);  
	        return jsonarray.toString();  
	    }  
}
