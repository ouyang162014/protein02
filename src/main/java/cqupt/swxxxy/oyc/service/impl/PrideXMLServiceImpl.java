package cqupt.swxxxy.oyc.service.impl;

import cqupt.swxxxy.oyc.dao.PrideXMLDao;
import cqupt.swxxxy.oyc.dao.impl.PrideXMLDaoImpl;
import cqupt.swxxxy.oyc.service.PrideXMLService;
import cqupt.swxxxy.oyc.util.ReadFileProperties;

public class PrideXMLServiceImpl implements PrideXMLService{
	private String filePathName;
	public PrideXMLServiceImpl(){}
	
	public PrideXMLServiceImpl(String fileName){
		String path = new ReadFileProperties().getProperties().get("windowInputFilePath");
		StringBuilder sb = new StringBuilder(path);
		sb.append("\\").append(fileName);
		filePathName = sb.toString();
	}

	@Override
	public int insertAll() {
		PrideXMLDao prideXMLDao=new PrideXMLDaoImpl(filePathName);
		return prideXMLDao.insertAll();
	}

}
