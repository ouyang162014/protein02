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

	// ����ParseProteinDetectionListģ���������
	ParseProteinDetectionList parseProteinDetectionList;
	// ����ParseSequenceCollectionIntf��������
	ParseSequenceCollection parseSequenceCollection;
	// ����ParseSpectrumIdentificationģ���������
	ParseSpectrumIdentification parseSpectrumIdentification;
	// ��������MzIdentMLControllerImplʵ�ֶ�mzIdentMl�ļ�����
	MzIdentMLControllerImpl mzIdentMLControllerImpl;
	// �浰���ʱ�����
	List<ProteinInfos> proteinInfoList = new ArrayList<ProteinInfos>();
	// peptide������Ϣ
	List<ArrayList<PeptideInfos>> peptideInfoList = new ArrayList<ArrayList<PeptideInfos>>();
	// spectrum������Ϣ
	List<SpectrumInfos> spectrumInfoList = new ArrayList<SpectrumInfos>();
	// ����session����
	SqlSession sqlSession = DBAccess.getSqlSession();
	int lastProteinId;
	int lastPeptideId;
	int lastSpectrumId;
	int lastPepProPsmSubId;
	String fileName;
	String createFilePath;
	// �����־
	Logger log = Logger.getLogger(Logger.class);

	public MzIdentMLDaoImpl(String inputFilePathName) {
		// ������ȡ�����ļ�����
		File inputFile = new File(inputFilePathName);
		log.info(inputFile);
		mzIdentMLControllerImpl = new MzIdentMLControllerImpl(inputFile);
		// �ж��ļ��Ƿ���ȷ
		if (!mzIdentMLControllerImpl.isValidFormat(inputFile)) {
			log.info("�ļ�������Ҫ��");
			// return;
		}
		// ��ʼ��ParseProteinDetectionListģ���������
		parseProteinDetectionList = new ParseProteinDetectionListImpl(
				mzIdentMLControllerImpl);
		// ��ʼ��ParseSequenceCollectionIntf��������
		parseSequenceCollection = new ParseSequenceCollectionImpl(
				mzIdentMLControllerImpl);
		// ��ʼ��ParseSpectrumIdentificationģ���������
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
		// ProteinInfoģ��
		ProteinInfos proteinInfos = null;
		for (Comparable proteinId : proteinIds) {
			proteinInfos = new ProteinInfos();
			// ����ID�����Ӧ����
			Protein protein = parseProteinDetectionList
					.getProteinById(proteinId);
			// coverage ��ʱ����
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
			// ��ӵ�������
			proteinInfoList.add(proteinInfos);
			proteinInfos = null;
		}
		// ��ȡsqlSession����
		sqlSession.insert("ProteinInfos.batchInsertProteins", proteinInfoList);
		log.info(sqlSession);
		return 0;
	}

	@Override
	public int insertPeptide(Comparable proteinId) throws Exception {
		// sql���
		// String sql =
		// "insert into t_peptide(peptide,charge,precursor_m_z,modifications,lons,mascot_score,length,start,stop,psm,identification_id) values(?,?,?,?,?,?,?,?,?,?,?)";
		// �������϶���洢PeptideInfos��Ϣ
		List<PeptideInfos> peptideInfosList = new ArrayList<PeptideInfos>();
		// ����PeptideInfos����
		PeptideInfos peptideInfos = null;
		// ����proteinId��ȡprotein
		Protein protein = parseProteinDetectionList.getProteinById(proteinId);
		// ��ȡpeptide����
		List<Peptide> peptideList = protein.getPeptides();
		// identificationId
	    int identificationId = getIdentificationId(proteinId);
		for (Peptide peptide : peptideList) {
			// ��ȡSpectrumIdentification��д���ļ�
			SpectrumIdentification spectrumIdentification = peptide.getSpectrumIdentification();
			String spectrumId = peptide.getSpectrumIdentification().getId().toString();
			// sequence
			String sequence = peptide.getSequence();
			// fit ����
			String fit;
			// deltaMZ ����
			float deltaMZ;
			// charge
			int charge = spectrumIdentification.getChargeState();
			// precusorMZ
			double precusorMZ = spectrumIdentification
					.getExperimentalMassToCharge();
			// precusorm ����
			float precusorm;
			// readablemod ����
			String readablemod = null;
			// modifications ���޸Ĺ�ϵ��
			String modifications = parseSequenceCollection.getModifications(peptide);
			// missedcleave ����
			int missedcleave = 0;
			// fragmentIon ������
			int lons = peptide.getFragmentation().size();
			// mascotScore
			String cvParams = parseSpectrumIdentification.getCvParams(
					spectrumIdentification).get("mascot:score");
			// ��ȡmascotScore
			float mascotScore = (cvParams != null ? Float.parseFloat(cvParams)
					: 0);
			// ��ʼ��
			int start = peptide.getPeptideEvidence().getStartPosition();
			// ������
			int stop = peptide.getPeptideEvidence().getEndPosition();
			// ����
			int length = stop - start + 1;
			// ��ȡ������ͬ���Ķ���
			int pms = parseSequenceCollection.getNumberOfQuantPTMs(proteinId);
			// proteinInfoList.get(Integer.parseInt(peptideId.toString())).getId();
			// cuttingSize ����
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
		// ��ȡsqlSession����
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
		// ����sql���
		// �Զ���ȡ��������
		// ��ȡ����spectrumIdentification id
		Map<String, Map<String, List<IndexElement>>> spectrumIdentificationItemIds = mzIdentMLUnmarshallerAdaptor.getScannedIdMappings();
		// ����SpectrumIdentificationResult id
		Iterator spectrumIdentificationResultId = spectrumIdentificationItemIds
				.keySet().iterator();
		while (spectrumIdentificationResultId.hasNext()) {
			// ��ȡSpectrumIdentificationItemIds
			String key = spectrumIdentificationResultId.next().toString();
			// ��ȡһ��spectrumIdentificationResultId��Ӧ������spectrumIdentificationItemId
			Map<String, List<IndexElement>> spectrumIdentificationItemIdsMap = spectrumIdentificationItemIds
					.get(key);
			// ������ȡÿ��spectrumIdentificationItemId
			Iterator spectrumIdentificationItemId = spectrumIdentificationItemIdsMap
					.keySet().iterator();
			while (spectrumIdentificationItemId.hasNext()) {
				spectrumInfos = new SpectrumInfos();
				String spectrumId = spectrumIdentificationItemId.next().toString();
				// ����id��ȡSpectrumIdentificationItem����
				SpectrumIdentificationItem spectrumIdentificationItem = mzIdentMLUnmarshallerAdaptor.getSpectrumIdentificationsById	(spectrumId);
				//int msLevel = parseSpectrumIdentification.getSpectrumMsLevel(spectrumId);
				int identified = (parseSpectrumIdentification.isIdentifiedSpectrum(spectrumId)) ? 1 : 0;
				// charge
				int charge = spectrumIdentificationItem.getChargeState();
				// precusorMZ
				double precusorMZ = spectrumIdentificationItem
						.getExperimentalMassToCharge();
				// sumOfIntensity ���е���ǿ��֮��
				double sumOfIntensity = parseSpectrumIdentification
						.getSumOfIntensity(spectrumIdentificationItem);
				// peaks ��ò���ĸ���
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
		// ��ȡspectrum ID
		// �����ļ�
		File file = new File(fileName);
		// �ж��ļ������Ƿ�Ϊ��
		/*
		 * if (file.exists() && file.length() > 0) { return
		 * spectrumName.toString(); }
		 */
		// д�������ַ�ʱ���������������
		FileOutputStream fos = new FileOutputStream(file);
		// �����ļ����ı����ʽ
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		// д���ļ���
		bw.write("NAME: "
				+ (spectrumIdentificationItem.getPeptide().getPeptideSequence()
						.toString()).toUpperCase() + "/2" + "\t\n");
		// д�벨�����
		bw.write("Num peaks: "
				+ parseSpectrumIdentification
						.getNumberOfSpectrumPeaks(spectrumIdentificationItem)
				+ "\t\n");
		List<IonType> ionTypeList = spectrumIdentificationItem
				.getFragmentation().getIonType();
		if (ionTypeList != null && ionTypeList.size() > 0) {
			// д��sequence
			for (IonType ionType : ionTypeList) {
				// ��ȡm_mz
				List<Float> mz = ionType.getFragmentArray().get(0).getValues();
				// ��ȡm_intensity
				List<Float> intensity = ionType.getFragmentArray().get(1)
						.getValues();
				// ��ȡm_error
				List<Float> error = ionType.getFragmentArray().get(2)
						.getValues();
				// ��ȡionֵ
				String ion = ionType.getCvParam().getValue();
				// ��ȡcharge
				int charge = ionType.getCharge();
				// д���ļ�
				for (int i = 0; i < mz.size(); i++)
					bw.write(mz.get(i) + " " + intensity.get(i) + " \"" + "y"
							+ (ion != null ? ion : "")
							+ new Tools().getAddNum(charge) + "/"
							+ error.get(i) + "\"" + "\t\n");
			}
		}
		log.info("--------����д���ļ��ɹ�----------");
		// �����
		bw.flush();
		// �ر���
		bw.close();
		osw.close();
		fos.close();
		return null;
	}
	
	public int insertFileInfo(){
		FileInfo fileInfo = new FileInfo();
		fileInfo.setName(fileName);
		fileInfo.setStatus("�ѷ���");
		fileInfo.setTime(Tools.getCurrentTime());
		sqlSession.insert("FileInfo.insertFileInfo",fileInfo);
		return 0;
	}

	/**
	 * ��ȡ�Ѷ����Ķζ�Ӧ�ĵ�����id
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
		// ��ȡ���е�����ID
		Collection<Comparable> proteinIds = parseProteinDetectionList
				.getProteinIds();
		try {
			// ����ʱ��в�������
			insertProtein(proteinIds);
			// ��ȡMzIdentMLUnmarshallerAdaptor����
			MzIdentMLUnmarshallerAdaptor mzIdentMLUnmarshallerAdaptor = parseSpectrumIdentification.getMzIdentMLUnmarshallerAdaptor();
			// ����ͼ���в�������
			insertSpectrum(mzIdentMLUnmarshallerAdaptor);
			// insertSpectrum(spectrumIds);
			for (Comparable proteinId : proteinIds) {
				// ���Ķα��в�������
				insertPeptide(proteinId);
			}
			// ��PepProPsmSub���в�������
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
