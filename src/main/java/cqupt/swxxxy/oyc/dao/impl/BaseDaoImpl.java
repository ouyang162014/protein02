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
	
	//����session����
	SqlSession sqlSession = DBAccess.getSqlSession();
	
	/**
	 * ��ȡ���һ�������ʵ�id
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
	 * ��ȡ���һ���Ķ�id
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
	 * �������һ����ͼ��id
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
