package cqupt.swxxxy.oyc.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.pride.utilities.data.io.file.MzIdentMLUnmarshallerAdaptor;

public interface MzIdentMLDao {
	/**
	 * ���뵰������Ϣ
	 * @param proteinId ������ID
	 * @return
	 * @throws Exception 
	 */
	public int insertProtein(Collection<Comparable> proteinIds) throws Exception;

	/**
	 * �����Ķ���Ϣ
	 * @param peptides
	 * @param proteinId
	 * @return
	 * @throws Exception 
	 */
	public int insertPeptide(Comparable proteinIds) throws Exception;
	
	/**
	 * ���������Ϣ
	 * @param mzIdentMLUnmarshallerAdaptor
	 * @return
	 * @throws Exception 
	 */
	public int insertSpectrum(MzIdentMLUnmarshallerAdaptor mzIdentMLUnmarshallerAdaptor) throws Exception;
	
	/**
	 * �����û���Ϣ
	 * @return
	 */
	public int insertAll();
	
	/**
	 * ��PepProPsmSub��ϵ���в�������
	 * @return
	 * @throws Exception 
	 */
	public int insertPepProPsmSub() throws Exception;
	
	/**
	 * ���ļ���д��spectrum��Ϣ
	 * @param peptide
	 * @param fileName
	 * @return �ļ��洢·��
	 * @throws TransformerException 
	 * @throws FileNotFoundException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public String writeSpectrum(SpectrumIdentificationItem spectrumIdentificationItem,String fileName) throws FileNotFoundException, TransformerException, ParserConfigurationException, IOException;
}
