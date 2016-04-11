package cqupt.swxxxy.oyc.dao.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;

import uk.ac.ebi.pride.utilities.data.controller.impl.ControllerImpl.PrideXmlControllerImpl;
import uk.ac.ebi.pride.utilities.data.core.FragmentIon;
import uk.ac.ebi.pride.utilities.data.core.Peptide;
import uk.ac.ebi.pride.utilities.data.core.Protein;
import uk.ac.ebi.pride.utilities.data.core.Spectrum;

import cqupt.swxxxy.oyc.bean.FileInfo;
import cqupt.swxxxy.oyc.bean.PepProPsmSubInfos;
import cqupt.swxxxy.oyc.bean.PeptideInfos;
import cqupt.swxxxy.oyc.bean.ProteinInfos;
import cqupt.swxxxy.oyc.bean.SpectrumInfos;
import cqupt.swxxxy.oyc.dao.PrideXMLDao;
import cqupt.swxxxy.oyc.dao.parse.prideXML.ParseGelFreeIdentification;
import cqupt.swxxxy.oyc.dao.parse.prideXML.ParseSpectrum;
import cqupt.swxxxy.oyc.dao.parse.prideXML.impl.ParseGelFreIdentificationImpl;
import cqupt.swxxxy.oyc.dao.parse.prideXML.impl.ParseSpectrumImpl;
import cqupt.swxxxy.oyc.util.Tools;

/**
 * ʵ�ֶ�ȡprideXML�ļ��е����ݴ洢�����ݿ����Ӧ�ļ���
 * 
 * @author chao ouyang
 * 
 */
public class PrideXMLDaoImpl extends BaseDaoImpl implements PrideXMLDao {
	// GelFreIdentificationģ���������
	ParseGelFreeIdentification parseGelFreeIdentification;
	// Spectrumģ���������
	ParseSpectrum parseSpectrum;
	// �����־
	Logger log = Logger.getLogger(Logger.class);
	// �浰���ʱ�����
	List<ProteinInfos> proteinInfoList=new ArrayList<ProteinInfos>();
	List<ArrayList<PeptideInfos>> peptideInfoList = new ArrayList<ArrayList<PeptideInfos>>();
	List<SpectrumInfos> spectrumInfoList=new ArrayList<SpectrumInfos>();
	int lastProteinId;
	int lastPeptideId;
	int lastSpectrumId;
	int lastPepProPsmSubId;
	String fileName;
	String createFilePath;
	/**
	 * @param fileName
	 * �ϴ��ļ���
	 */
	public PrideXMLDaoImpl(String filePathName)  {
		File inputFile = new File(filePathName);
		PrideXmlControllerImpl prideXmlControllerImpl = new PrideXmlControllerImpl(inputFile);
		if(prideXmlControllerImpl.isValidFormat(inputFile)){
			log.info("�ϴ��ļ����Ϸ�");
			//return;
		}
		// ParseDataProcessingImpl(prideXmlControllerImpl);
		parseGelFreeIdentification = new ParseGelFreIdentificationImpl(
				prideXmlControllerImpl);
		// ParseProtocalImpl(prideXmlControllerImpl);
		parseSpectrum = new ParseSpectrumImpl(prideXmlControllerImpl);
		lastProteinId = selectLastProteinId();
		lastPeptideId = selectLastPeptideId();
		lastSpectrumId = selectLastSpectrumId();
		lastPepProPsmSubId = selectLastPepProPsmSubId();
		createFilePath = getOutputPath();
		fileName = filePathName.substring(filePathName.lastIndexOf("\\")+1, filePathName.length());
	}

	public int insertProtein(Collection<Comparable> proteinIds)
			throws Exception {
		//ProteinInfoģ��
		ProteinInfos proteinInfos=null;
		// try {
		for (Comparable proteinId : proteinIds) {
			proteinInfos=new ProteinInfos();
			// ����ID�����Ӧ����
			Protein protein = parseGelFreeIdentification
					.getProteinById(proteinId);
			// coverage ��ʱ����
			double coverage = protein.getSequenceCoverage();
			// accession
			String accession = parseGelFreeIdentification.getProteinAccession(proteinId);
			// threshold
			float threshold = (float) parseGelFreeIdentification.getProteinThreshold(proteinId);
			// peptides
			int peptides = protein.getPeptides().size();
			// distinctPeptides
			int distinctPeptides = parseGelFreeIdentification.getNumberOfUniquePeptides(proteinId);
			// ptms
			int ptms = parseGelFreeIdentification.getNumberOfPTMs(proteinId);
			proteinInfos.setProtein(accession);
			proteinInfos.setThreshold(threshold);
			proteinInfos.setPeptides(peptides);
			proteinInfos.setDistinctPeptides(distinctPeptides);
			proteinInfos.setPtms(ptms);
			proteinInfos.setId(++lastProteinId);
			//��ӵ�������
			proteinInfoList.add(proteinInfos);
			proteinInfos=null;
		}
		//��ȡsqlSession����
		sqlSession.insert("ProteinInfos.batchInsertProteins", proteinInfoList);
		log.info(sqlSession);
		return 0;
	}

	public int insertPeptide(Collection<Comparable> peptideIds,
			Comparable proteinId) throws Exception {
		//�������϶���洢PeptideInfos��Ϣ
		List<PeptideInfos> peptideList=new ArrayList<PeptideInfos>();
		//����PeptideInfos����
		PeptideInfos peptideInfos=null;
		int index = 0;
		// ������
		for (Comparable peptideId : peptideIds) {
			peptideInfos=new PeptideInfos();
			Peptide peptide = parseGelFreeIdentification.getPeptideByIndex(proteinId, index);
			Comparable spectrumId = parseGelFreeIdentification.getPeptideSpectrumId(peptide);
			Spectrum spectrum = parseSpectrum.getSpectrum(spectrumId);
			List<FragmentIon> fragmentIonList = parseGelFreeIdentification.getFragmentIonList(peptide);	
			
			writeSpectrum(spectrum,  createFilePath + "\\" + fileName,fragmentIonList, peptide);
					
			// sequence
			String sequence = peptide.getSequence();
			// fit ����
			String fit;
			// deltaMZ ����
			float deltaMZ;
			// charge
			Map<String, String> additional = parseGelFreeIdentification.getPeptideAdditional(peptide);
			int charge = additional.get("charge state") != null ? Integer.parseInt(additional.get("charge state")) : 0;
			// precusorMZ
			double precursorMZ = Double.parseDouble(parseGelFreeIdentification.getPeptideSpectrumId(peptide).toString());
			// precusorm ����
			float precusorm;
			// readablemod ����
			String readablemod = null;
			// modifications ���޸Ĺ�ϵ��
			String modifications = parseGelFreeIdentification.getModifications(peptide);
			// missedcleave ����
			int missedcleave = 0;
			// fragmentIon ������
			int lons = peptide.getFragmentation().size();
			// mascotScore
			float mascotScore = additional.get("Mascot Score") != null ? Float.parseFloat(additional.get("Mascot Score")) : 0;
			int start = parseGelFreeIdentification.getPeptideSequenceStart(peptide);
			int stop = parseGelFreeIdentification.getPeptideSequenceEnd(peptide);
			int length = stop - start + 1;
			// ��ȡ������ͬ���Ķ���
			int pms = parseGelFreeIdentification.getNumberOfQuantPeptides(proteinId, index);
			// identificationId
			int identificationId = proteinInfoList.get(Integer.parseInt(peptideId.toString())).getId();
			// cuttingSize ����
			int cuttingSize = 0;
			peptideInfos.setId(++lastPeptideId);
			peptideInfos.setPeptide(sequence);
			peptideInfos.setCharge(charge);
			peptideInfos.setPrecursorMZ(precursorMZ);
			peptideInfos.setModifications(modifications);
			peptideInfos.setLons(lons);
			peptideInfos.setMascotScore(mascotScore);
			peptideInfos.setLength(length);
			peptideInfos.setStart(start);
			peptideInfos.setStop(stop);
			peptideInfos.setPsm(pms);
			peptideInfos.setIdentificationId(identificationId);
			peptideList.add(peptideInfos);
			peptideInfos = null;
			index++;
		}
		//��ȡsqlSession����
		sqlSession.insert("Peptide.batchInsertPeptides", peptideList);
		peptideInfoList.add((ArrayList<PeptideInfos>) peptideList);
		log.info(peptideList);
		log.info(sqlSession);
		return 0;
	}

	/**
	 * ��spectrum���в�������
	 */
	public int insertSpectrum(Collection<Comparable> spectrumIds)
			throws Exception {
		//String sql = "insert into t_spectrum(ms_level,identified,charge,precursor_m_z,sum_of_intensity,peaks,path) values(?,?,?,?,?,?,?)";
		SpectrumInfos spectrumInfos=null;
		for (Comparable spectrumId : spectrumIds) {
			spectrumInfos=new SpectrumInfos();
			Spectrum spectrum = parseSpectrum.getSpectrum(spectrumId);		
			String path = writeSpectrum(spectrum, createFilePath + "\\" + fileName,null,null);
			// msLevel
			int msLevel = parseSpectrum.getSpectrumMsLevel(spectrumId);
			// identified
			int identified = (parseSpectrum
					.isIdentifiedSpectrum(spectrumId)) ? 1 : 0;
			Map<String, String> cvParams = parseSpectrum
					.getPeakMZTimeChargeState(spectrum);
			// charge
			int charge = (cvParams.get("charge state") != null) ? Integer
					.parseInt(cvParams.get("charge state")) : 0;
			// precusorMZ
			double precursorMZ = (cvParams.get("selected ion m/z") != null) ? Double
					.parseDouble(cvParams.get("selected ion m/z")) : 0;
			// sumOfIntensity ���е���ǿ��֮��
			double sumOfIntensity = parseSpectrum
					.getSumOfIntensity(spectrumId);
			// peaks ��ò���ĸ���
			int peaks = parseSpectrum.getNumberOfSpectrumPeaks(spectrumId);
			spectrumInfos.setId(++lastSpectrumId);
			spectrumInfos.setMsLevel(msLevel);
			spectrumInfos.setIdentified(identified);
			spectrumInfos.setCharge(charge);
			spectrumInfos.setPrecursorMZ(precursorMZ);
			spectrumInfos.setSumOfIntensity(sumOfIntensity);
			spectrumInfos.setPeaks(peaks);
			spectrumInfos.setPath(path);
			spectrumInfoList.add(spectrumInfos);
			spectrumInfos = null;
		}
		sqlSession.insert("Spectrum.batchInsertSpectrums", spectrumInfoList);
		log.info(spectrumInfoList);
		log.info(sqlSession);
		return 0;
	}
	
	
	public int insertPepProPsmSub() throws Exception {
		List<PepProPsmSubInfos> pepProPsmSubInfosList = new ArrayList<PepProPsmSubInfos>();
		PepProPsmSubInfos pepProPsmSub = null;
		int proteinIndex = 0;
		for(ProteinInfos protein : proteinInfoList){
			int peptideIndex = 0;
			for(PeptideInfos peptideInfo : peptideInfoList.get(proteinIndex)){
				pepProPsmSub = new PepProPsmSubInfos();
				pepProPsmSub.setId(++lastPepProPsmSubId);
				pepProPsmSub.setProteinId(protein.getId());
				pepProPsmSub.setPeptideId(peptideInfo.getId());
				Peptide peptide = parseGelFreeIdentification.getPeptideByIndex(proteinIndex, peptideIndex);
				// ��ȡ�Ķζ�Ӧ�Ĺ���ID
				int spectrunId = (Integer) parseGelFreeIdentification.getSpectrumReference(peptide);
				pepProPsmSub.setSpectrumId(spectrumInfoList.get(spectrunId-1).getId());
				pepProPsmSubInfosList.add(pepProPsmSub);
				pepProPsmSub = null;
				peptideIndex++;
			}
			proteinIndex++;
		}
		sqlSession.insert("PepProPsmSubInfos.batchInsertPepProPsmSub", pepProPsmSubInfosList);
		return 0;
	}
	
	public int insertFileInfo(){
		FileInfo fileInfo = new FileInfo();
		fileInfo.setName(fileName);
		fileInfo.setStatus("�ѷ���");
		fileInfo.setTime(Tools.getCurrentTime());
		sqlSession.insert("FileInfo.insertFileInfo",fileInfo);
		return 0;
	}

	public int insertAll() {
		int flag = 1;
		// ��ȡ���е�����ID
		Collection<Comparable> proteinIds = parseGelFreeIdentification
				.getProteinIds();
		Collection<Comparable> spectrumIds = parseSpectrum.getSpectrumIds();
		try {
			insertProtein(proteinIds);
			for (Comparable proteinId : proteinIds) {
				// ��ȡ�����ʶ�Ӧ�������Ķε�ID
				Collection<Comparable> peptideIds = parseGelFreeIdentification.getPeptideIds(proteinId);
				insertPeptide(peptideIds, proteinId);
			}
			insertSpectrum(spectrumIds);
			insertPepProPsmSub();
			insertFileInfo();
			sqlSession.commit();
		} catch (Exception e) {
			flag = 0;
			log.info("�����쳣���������ݻع�");
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return flag;
	}

	/**
	 * ���ļ���д��spectrum��Ϣ
	 * 
	 * @param spectrum
	 *            �����spectrum
	 * @param �ļ���
	 * @param fragmentIonList
	 */
	public String writeSpectrum(Spectrum spectrum, String fileName,
			List<FragmentIon> fragmentIonList, Peptide peptide)
			throws TransformerException, ParserConfigurationException,
			IOException {
		// ��ȡspectrum ID
		String id = spectrum.getId() + "";
		// ��ȡԭ�ļ��ļ���
		StringBuilder spectrumName = new StringBuilder(fileName);
		// spectrum�ļ�
		spectrumName.append(id).append(".msp");
		// �����ļ�
		File file = new File(spectrumName.toString());
		// �ж��ļ������Ƿ�Ϊ��
		if (file.exists() && file.length() > 0) {
			return spectrumName.toString();
		}
		// д�������ַ�ʱ���������������
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		// ��ȡ���ܺ��mz����
		double[] mz = parseSpectrum.getMZArrayBinary(spectrum)[0];
		// ���ܺ��intensity����
		double[] intensity = parseSpectrum.getMZArrayBinary(spectrum)[1];
		// �ж�fragmentIonList�����Ƿ�Ϊ��,��Ϊ����FragmentIon��Ϣд���ļ���
		if (fragmentIonList != null && fragmentIonList.size() > 0
				&& peptide != null) {
			// ����map�������� keyΪmz valueΪfragmentIonList��indexֵ
			Map<String, Integer> fragmentIonMap = new HashMap<String, Integer>();
			// ��¼��fragmentIonList�е�λ��
			int theNumOfFragmentIon = 0;
			for (FragmentIon fragmentIon : fragmentIonList) {
				fragmentIonMap.put(fragmentIon.getMz() + "",
						theNumOfFragmentIon);
				theNumOfFragmentIon++;
			}
			// д��sequence
			bw.write("NAME: " + (peptide.getSequence()).toUpperCase() + "/2"
					+ "\t\n");
			// д�벨�����
			bw.write("Num peaks: " + intensity.length + "\t\n");
			// ѭ��д��mz intensity ���������������
			for (int i = 0; i < mz.length; i++) {
				if (fragmentIonMap.get(mz[i] + "") != null) {
					int index = fragmentIonMap.get(mz[i] + "");
					int yIon = fragmentIonList.get(index).getLocation();
					int charge = fragmentIonList.get(index).getCharge();
					double error = fragmentIonList.get(index).getMassError();
					bw.write(mz[i] + " " + intensity[i] + " \"" + "y" + yIon
							+ getAddNum(charge) + "/" + error + "\"" + "\t\n");
				} else {
					bw.write(mz[i] + " " + intensity[i] + "\t\n");
				}
			}
		} else {
			// д��sequence
			bw.write("NAME: " + "\t\n");
			// д�벨�����
			bw.write("Num peaks: " + intensity.length + "\t\n");
			for (int i = 0; i < mz.length; i++) {
				bw.write(mz[i] + " " + intensity[i] + "\t\n");
			}
		}
		// �ر���
		bw.close();
		osw.close();
		fos.close();
		return spectrumName.toString();
	}

	/**
	 * ��ȡ�������Ŀ
	 * 
	 * @param num
	 * @return
	 */
	public String getAddNum(int num) {
		StringBuilder sb = new StringBuilder();
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				sb.append("+");
			}
			return sb.toString();
		} else {
			return "";
		}
	}

}