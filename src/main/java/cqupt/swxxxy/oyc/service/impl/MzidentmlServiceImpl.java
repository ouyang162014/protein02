package cqupt.swxxxy.oyc.service.impl;

import cqupt.swxxxy.oyc.dao.MzIdentMLDao;
import cqupt.swxxxy.oyc.dao.impl.MzIdentMLDaoImpl;
import cqupt.swxxxy.oyc.service.MzidentmlService;
import cqupt.swxxxy.oyc.util.ReadFileProperties;

public class MzidentmlServiceImpl implements MzidentmlService{
	private String filePathName = null;
	
	public MzidentmlServiceImpl(){}
	
	public MzidentmlServiceImpl(String fileName){
		String path = new ReadFileProperties().getProperties().get("windowInputFilePath");
		StringBuilder sb = new StringBuilder(path);
		sb.append("\\").append(fileName);
		filePathName = sb.toString();
	}
	
	@Override
	public int insertAll() {
		MzIdentMLDao mzIdentmlDao=new MzIdentMLDaoImpl(filePathName);
		return mzIdentmlDao.insertAll();
	}

}
