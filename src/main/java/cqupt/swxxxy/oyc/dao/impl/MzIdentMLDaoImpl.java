package cqupt.swxxxy.oyc.dao.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import psidev.psi.tools.xxindex.index.IndexElement;
import uk.ac.ebi.jmzidml.model.mzidml.IonType;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.pride.utilities.data.controller.impl.ControllerImpl.MzIdentMLControllerImpl;
import uk.ac.ebi.pride.utilities.data.core.Peptide;
import uk.ac.ebi.pride.utilities.data.core.Protein;
import uk.ac.ebi.pride.utilities.data.core.SpectrumIdentification;
import uk.ac.ebi.pride.utilities.data.io.file.MzIdentMLUnmarshallerAdaptor;
import cqupt.swxxxy.oyc.bean.FileInfo;
import cqupt.swxxxy.oyc.bean.PepProPsmSubInfos;
import cqupt.swxxxy.oyc.bean.PeptideInfos;
import cqupt.swxxxy.oyc.bean.ProteinInfos;
import cqupt.swxxxy.oyc.bean.SpectrumInfos;
import cqupt.swxxxy.oyc.dao.DBAccess;
import cqupt.swxxxy.oyc.dao.MzIdentMLDao;
import cqupt.swxxxy.oyc.dao.parse.mzIdentML.ParseProteinDetectionList;
import cqupt.swxxxy.oyc.dao.parse.mzIdentML.ParseSequenceCollection;
import cqupt.swxxxy.oyc.dao.parse.mzIdentML.ParseSpectrumIdentification;
import cqupt.swxxxy.oyc.dao.parse.mzIdentML.impl.ParseProteinDetectionListImpl;
import cqupt.swxxxy.oyc.dao.parse.mzIdentML.impl.ParseSequenceCollectionImpl;
import cqupt.swxxxy.oyc.dao.parse.mzIdentML.impl.ParseSpectrumIdentificationImpl;
import cqupt.swxxxy.oyc.util.Tools;

public class MzIdentMLDaoImpl extends BaseDaoImpl implements MzIdentMLDao {

	// 创建ParseProteinDetectionList模块解析对象
	ParseProteinDetectionList parseProteinDetectionList;
	// 创建ParseSequenceCollectionIntf解析对象
	ParseSequenceCollection parseSequenceCollection;
	// 创建ParseSpectrumIdentification模块解析对象
	ParseSpectrumIdentification parseSpectrumIdentification;
	// 创建对象MzIdentMLControllerImpl实现对mzIdentMl文件解析
	MzIdentMLControllerImpl mzIdentMLControllerImpl;
	// 存蛋白质表主键
	List<ProteinInfos> proteinInfoList = new ArrayList<ProteinInfos>();
	// peptide主键信息
	List<ArrayList<PeptideInfos>> peptideInfoList = new ArrayList<ArrayList<PeptideInfos>>();
	// spectrum主键信息
	List<SpectrumInfos> spectrumInfoList = new ArrayList<SpectrumInfos>();
	// 创建session对象
	SqlSession sqlSession = DBAccess.getSqlSession();
	int lastProteinId;
	int lastPeptideId;
	int lastSpectrumId;
	int lastPepProPsmSubId;
	String fileName;
	String createFilePath;
	// 输出日志
	Logger log = Logger.getLogger(Logger.class);

	public MzIdentMLDaoImpl(String inputFilePathName) {
		// 创建读取数据文件对象
		File inputFile = new File(inputFilePathName);
		log.info(inputFile);
		mzIdentMLControllerImpl = new MzIdentMLControllerImpl(inputFile);
		// 判断文件是否正确
		if (!mzIdentMLControllerImpl.isValidFormat(inputFile)) {
			log.info("文件不符合要求");
			// return;
		}
		// 初始化ParseProteinDetectionList模块解析对象
		parseProteinDetectionList = new ParseProteinDetectionListImpl(
				mzIdentMLControllerImpl);
		// 初始化ParseSequenceCollectionIntf解析对象
		parseSequenceCollection = new ParseSequenceCollectionImpl(
				mzIdentMLControllerImpl);
		// 初始化ParseSpectrumIdentification模块解析对象
		parseSpectrumIdentification = new ParseSpectrumIdentificationImpl(
				mzIdentMLControllerImpl);
		lastProteinId = selectLastProteinId();
		lastPeptideId = selectLastPeptideId();
		lastSpectrumId = selectLastSpectrumId();
		lastPepProPsmSubId = selectLastPepProPsmSubId();
		fileName = inputFilePathName.substring(inputFilePathName.lastIndexOf("\\")+1, inputFilePathName.length());
		createFilePath = getOutputPath();
	}

	@Override
	public int insertProtein(Collection<Comparable> proteinIds)
			throws Exception {
		// ProteinInfo模型
		ProteinInfos proteinInfos = null;
		for (Comparable proteinId : proteinIds) {
			proteinInfos = new ProteinInfos();
			// 根据ID获得相应蛋白
			Protein protein = parseProteinDetectionList
					.getProteinById(proteinId);
			// coverage 暂时留空
			double coverage = protein.getSequenceCoverage();
			// threshold
			float threshold = (float) protein.getThreshold();
			// peptides
			int peptides = protein.getPeptides().size();
			// distinctPeptides
			int distinctPeptides = parseProteinDetectionList
					.getDistinctPeptides(proteinId);
			// ptms
			int ptms = parseProteinDetectionList.getNumberOfPTMs(proteinId);
			proteinInfos.setProtein(proteinId.toString());
			proteinInfos.setThreshold(threshold);
			proteinInfos.setPeptides(peptides);
			proteinInfos.setDistinctPeptides(distinctPeptides);
			proteinInfos.setPtms(ptms);
			proteinInfos.setId(++lastProteinId);
			// 添加到集合中
			proteinInfoList.add(proteinInfos);
			proteinInfos = null;
		}
		// 获取sqlSession对象
		sqlSession.insert("ProteinInfos.batchInsertProteins", proteinInfoList);
		log.info(sqlSession);
		return 0;
	}

	@Override
	public int insertPeptide(Comparable proteinId) throws Exception {
		// sql语句
		// String sql =
		// "insert into t_peptide(peptide,charge,precursor_m_z,modifications,lons,mascot_score,length,start,stop,psm,identification_id) values(?,?,?,?,?,?,?,?,?,?,?)";
		// 创建集合对象存储PeptideInfos信息
		List<PeptideInfos> peptideInfosList = new ArrayList<PeptideInfos>();
		// 创建PeptideInfos对象
		PeptideInfos peptideInfos = null;
		// 根据proteinId获取protein
		Protein protein = parseProteinDetectionList.getProteinById(proteinId);
		// 获取peptide集合
		List<Peptide> peptideList = protein.getPeptides();
		// identificationId
	    int identificationId = getIdentificationId(proteinId);
		for (Peptide peptide : peptideList) {
			// 获取SpectrumIdentification并写入文件
			SpectrumIdentification spectrumIdentification = peptide.getSpectrumIdentification();
			String spectrumId = peptide.getSpectrumIdentification().getId().toString();
			// sequence
			String sequence = peptide.getSequence();
			// fit 留空
			String fit;
			// deltaMZ 留空
			float deltaMZ;
			// charge
			int charge = spectrumIdentification.getChargeState();
			// precusorMZ
			double precusorMZ = spectrumIdentification
					.getExperimentalMassToCharge();
			// precusorm 留空
			float precusorm;
			// readablemod 留空
			String readablemod = null;
			// modifications 需修改关系表
			String modifications = parseSequenceCollection.getModifications(peptide);
			// missedcleave 留空
			int missedcleave = 0;
			// fragmentIon 的数量
			int lons = peptide.getFragmentation().size();
			// mascotScore
			String cvParams = parseSpectrumIdentification.getCvParams(
					spectrumIdentification).get("mascot:score");
			// 获取mascotScore
			float mascotScore = (cvParams != null ? Float.parseFloat(cvParams)
					: 0);
			// 开始点
			int start = peptide.getPeptideEvidence().getStartPosition();
			// 结束点
			int stop = peptide.getPeptideEvidence().getEndPosition();
			// 长度
			int length = stop - start + 1;
			// 获取序列相同的肽段数
			int pms = parseSequenceCollection.getNumberOfQuantPTMs(proteinId);
			// proteinInfoList.get(Integer.parseInt(peptideId.toString())).getId();
			// cuttingSize 留空
			int cuttingSize = 0;
			peptideInfos = new PeptideInfos();
			peptideInfos.setId(++lastPeptideId);
			peptideInfos.setPeptide(sequence);
			peptideInfos.setCharge(charge);
			peptideInfos.setPrecursorMZ(precusorMZ);
			peptideInfos.setModifications(modifications);
			peptideInfos.setLons(lons);
			peptideInfos.setMascotScore(mascotScore);
			peptideInfos.setLength(length);
			peptideInfos.setStart(start);
			peptideInfos.setStop(stop);
			peptideInfos.setPsm(pms);
			peptideInfos.setIdentificationId(identificationId);
			peptideInfos.setSpectrumId(spectrumId);
			peptideInfosList.add(peptideInfos);
			peptideInfos = null;
		}
		System.out.println("debug");
		// 获取sqlSession对象
		sqlSession.insert("Peptide.batchInsertPeptides", peptideInfosList);
		peptideInfoList.add((ArrayList<PeptideInfos>) peptideInfosList);
		log.info(peptideList);
		log.info(sqlSession);
		return 0;
	}

	public int insertSpectrum(
			MzIdentMLUnmarshallerAdaptor mzIdentMLUnmarshallerAdaptor)
			throws Exception {
		SpectrumInfos spectrumInfos=null;
		// 创建sql语句
		// 自动获取插入主键
		// 获取所有spectrumIdentification id
		Map<String, Map<String, List<IndexElement>>> spectrumIdentificationItemIds = mzIdentMLUnmarshallerAdaptor.getScannedIdMappings();
		// 迭代SpectrumIdentificationResult id
		Iterator spectrumIdentificationResultId = spectrumIdentificationItemIds
				.keySet().iterator();
		while (spectrumIdentificationResultId.hasNext()) {
			// 获取SpectrumIdentificationItemIds
			String key = spectrumIdentificationResultId.next().toString();
			// 获取一个spectrumIdentificationResultId对应的所有spectrumIdentificationItemId
			Map<String, List<IndexElement>> spectrumIdentificationItemIdsMap = spectrumIdentificationItemIds
					.get(key);
			// 迭代获取每个spectrumIdentificationItemId
			Iterator spectrumIdentificationItemId = spectrumIdentificationItemIdsMap
					.keySet().iterator();
			while (spectrumIdentificationItemId.hasNext()) {
				spectrumInfos = new SpectrumInfos();
				String spectrumId = spectrumIdentificationItemId.next().toString();
				// 根据id获取SpectrumIdentificationItem对象
				SpectrumIdentificationItem spectrumIdentificationItem = mzIdentMLUnmarshallerAdaptor.getSpectrumIdentificationsById	(spectrumId);
				//int msLevel = parseSpectrumIdentification.getSpectrumMsLevel(spectrumId);
				int identified = (parseSpectrumIdentification.isIdentifiedSpectrum(spectrumId)) ? 1 : 0;
				// charge
				int charge = spectrumIdentificationItem.getChargeState();
				// precusorMZ
				double precusorMZ = spectrumIdentificationItem
						.getExperimentalMassToCharge();
				// sumOfIntensity 所有电子强度之和
				double sumOfIntensity = parseSpectrumIdentification
						.getSumOfIntensity(spectrumIdentificationItem);
				// peaks 获得波峰的个数
				int peaks = parseSpectrumIdentification
						.getNumberOfSpectrumPeaks(spectrumIdentificationItem);
				StringBuffer path = new StringBuffer(createFilePath).append("\\").append(fileName).append(++lastSpectrumId).append(".msp");
				spectrumInfos.setId(lastSpectrumId);
				//spectrumInfos.setMsLevel(msLevel);
				spectrumInfos.setIdentified(identified);
				spectrumInfos.setCharge(charge);
				spectrumInfos.setPrecursorMZ(precusorMZ);
				spectrumInfos.setSumOfIntensity(sumOfIntensity);
				spectrumInfos.setPeaks(peaks);
				spectrumInfos.setSpectrumId(spectrumId);
				spectrumInfos.setPath(path.toString());
				writeSpectrum(spectrumIdentificationItem,path.toString());
				spectrumInfoList.add(spectrumInfos);
				spectrumInfos = null;
			}
		}
		sqlSession.insert("Spectrum.batchInsertSpectrums", spectrumInfoList);
		log.info(spectrumInfoList);
		log.info(sqlSession);
		return 0;
	}

	@Override
	public int insertPepProPsmSub() throws Exception {
		List<PepProPsmSubInfos> pepProPsmSubInfosList = new ArrayList<PepProPsmSubInfos>();
		PepProPsmSubInfos pepProPsmSub = null;
		int proteinIndex = 0;
		for(ProteinInfos protein : proteinInfoList){
			for(PeptideInfos peptideInfo : peptideInfoList.get(proteinIndex)){
				pepProPsmSub = new PepProPsmSubInfos();
				int spectrumId = 0;
				for(SpectrumInfos spectrumInfo : spectrumInfoList){
					if(spectrumInfo.getSpectrumId().equals(peptideInfo.getSpectrumId())){
						spectrumId = spectrumInfo.getId();
						break;
					}
				}
				pepProPsmSub.setId(++lastPepProPsmSubId);
				pepProPsmSub.setPeptideId(peptideInfo.getId());
				pepProPsmSub.setSpectrumId(spectrumId);
				pepProPsmSub.setProteinId(protein.getId());
				pepProPsmSubInfosList.add(pepProPsmSub);
				pepProPsmSub = null;
			}
			proteinIndex++;
		}
		sqlSession.insert("PepProPsmSubInfos.batchInsertPepProPsmSub", pepProPsmSubInfosList);
		return 0;
	}

	@Override
	public String writeSpectrum(
			SpectrumIdentificationItem spectrumIdentificationItem,
			String fileName) throws FileNotFoundException,
			TransformerException, ParserConfigurationException, IOException {
		// 获取spectrum ID
		// 创建文件
		File file = new File(fileName);
		// 判断文件内容是否为空
		/*
		 * if (file.exists() && file.length() > 0) { return
		 * spectrumName.toString(); }
		 */
		// 写入中文字符时解决中文乱码问题
		FileOutputStream fos = new FileOutputStream(file);
		// 设置文件流的编码格式
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		// 写入文件名
		bw.write("NAME: "
				+ (spectrumIdentificationItem.getPeptide().getPeptideSequence()
						.toString()).toUpperCase() + "/2" + "\t\n");
		// 写入波峰个数
		bw.write("Num peaks: "
				+ parseSpectrumIdentification
						.getNumberOfSpectrumPeaks(spectrumIdentificationItem)
				+ "\t\n");
		List<IonType> ionTypeList = spectrumIdentificationItem
				.getFragmentation().getIonType();
		if (ionTypeList != null && ionTypeList.size() > 0) {
			// 写入sequence
			for (IonType ionType : ionTypeList) {
				// 获取m_mz
				List<Float> mz = ionType.getFragmentArray().get(0).getValues();
				// 获取m_intensity
				List<Float> intensity = ionType.getFragmentArray().get(1)
						.getValues();
				// 获取m_error
				List<Float> error = ionType.getFragmentArray().get(2)
						.getValues();
				// 获取ion值
				String ion = ionType.getCvParam().getValue();
				// 获取charge
				int charge = ionType.getCharge();
				// 写入文件
				for (int i = 0; i < mz.size(); i++)
					bw.write(mz.get(i) + " " + intensity.get(i) + " \"" + "y"
							+ (ion != null ? ion : "")
							+ new Tools().getAddNum(charge) + "/"
							+ error.get(i) + "\"" + "\t\n");
			}
		}
		log.info("--------数据写入文件成功----------");
		// 输出流
		bw.flush();
		// 关闭流
		bw.close();
		osw.close();
		fos.close();
		return null;
	}
	
	public int insertFileInfo(){
		FileInfo fileInfo = new FileInfo();
		fileInfo.setName(fileName);
		fileInfo.setStatus("已发布");
		fileInfo.setTime(Tools.getCurrentTime());
		sqlSession.insert("FileInfo.insertFileInfo",fileInfo);
		return 0;
	}

	/**
	 * 获取已定义肽段对应的蛋白质id
	 * 
	 * @return
	 */
	public int getIdentificationId(Comparable proteinId) {
		int identification = 0;
		for(ProteinInfos proteinInfo:proteinInfoList){
			if(proteinInfo.getProtein().equals(proteinId.toString())){
				identification = proteinInfo.getId();
				break;
			}
		}
		return identification;
	}

	@Override
	public int insertAll() {
		int flag = 1;
		// 获取所有蛋白质ID
		Collection<Comparable> proteinIds = parseProteinDetectionList
				.getProteinIds();
		try {
			// 项蛋白质表中插入数据
			insertProtein(proteinIds);
			// 获取MzIdentMLUnmarshallerAdaptor对象
			MzIdentMLUnmarshallerAdaptor mzIdentMLUnmarshallerAdaptor = parseSpectrumIdentification.getMzIdentMLUnmarshallerAdaptor();
			// 向谱图表中插入数据
			insertSpectrum(mzIdentMLUnmarshallerAdaptor);
			// insertSpectrum(spectrumIds);
			for (Comparable proteinId : proteinIds) {
				// 向肽段表中插入数据
				insertPeptide(proteinId);
			}
			// 向PepProPsmSub表中插入数据
			insertPepProPsmSub();
			insertFileInfo();
			sqlSession.commit();
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			//sqlSession.close();
		}
		return flag;
	}

}
