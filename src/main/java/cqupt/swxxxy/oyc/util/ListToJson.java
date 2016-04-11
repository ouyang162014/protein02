package cqupt.swxxxy.oyc.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import cqupt.swxxxy.oyc.bean.FileInfo;

public class ListToJson {

	/**
	 * 将json转换为List
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
	     * 将一个 FileInfo 对象的List 生成Json字串 
	     * 是根据客户端页面用户输入的信息生成的 
	     */  
	    public static String getFileInfoString(List<FileInfo> fileInfoList) {  
	        JSONArray jsonarray = JSONArray.fromObject(fileInfoList);  
	        return jsonarray.toString();  
	    }  
}
