package cqupt.swxxxy.oyc.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cqupt.swxxxy.oyc.bean.PepProPsmSubInfos;
import cqupt.swxxxy.oyc.bean.PeptideInfos;
import cqupt.swxxxy.oyc.bean.ProteinInfos;
import cqupt.swxxxy.oyc.bean.SpectrumInfos;
import cqupt.swxxxy.oyc.dao.DBAccess;
import cqupt.swxxxy.oyc.util.ReadFileProperties;

public class BaseDaoImpl {
	
	//创建session对象
	SqlSession sqlSession = DBAccess.getSqlSession();
	
	/**
	 * 获取最后一个蛋白质的id
	 * @return
	 */
	public int selectLastProteinId(){
		List<ProteinInfos> proteinList = sqlSession.selectList("selectLastProteinId");
		if(proteinList == null || proteinList.size() < 1){
			return 1;
		}
		else{
			return proteinList.get(0).getId();
		}
	}
	
	/**
	 * 获取最后一个肽段id
	 * @return
	 */
	public int selectLastPeptideId(){
		List<PeptideInfos> peptideList = sqlSession.selectList("selectLastPeptideId");
		if(peptideList != null && peptideList.size() < 1){
			return 1;
		}
		else{
			return peptideList.get(0).getId();
		}
	}
	
	/**
	 * 查找最后一个谱图的id
	 * @return
	 */
	public int selectLastSpectrumId(){
		List<SpectrumInfos> spectrumList = sqlSession.selectList("selectLastSpectrumId");
		if(spectrumList != null && spectrumList.size() < 1){
			return 1;
		}
		else{
			return spectrumList.get(0).getId();
		}
	}
	
	public int selectLastPepProPsmSubId(){
		List<PepProPsmSubInfos> pepProPsmSubList = sqlSession.selectList("selectLastPepProPsmSubId");
		if(pepProPsmSubList != null && pepProPsmSubList.size() < 1){
			return 1;
		}
		else{
			return pepProPsmSubList.get(0).getId();
		}
	}
	
	public String getOutputPath(){
		String outputPath = new ReadFileProperties().getProperties().get("windowCreateFilePath");
		return outputPath;
	}

}
