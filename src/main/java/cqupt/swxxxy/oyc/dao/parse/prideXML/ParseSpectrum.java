package cqupt.swxxxy.oyc.dao.parse.prideXML;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.pride.utilities.data.core.Spectrum;

/**
 * prideXML�ļ������ӿ�
 * @author chao ouyang
 *
 */
public interface ParseSpectrum {
	
	/**
	 * ��ȡSpectrum��Ϣ
	 * @return List<Spectrum>
	 */
	public List<Spectrum> getSpectrums();
	
	
	/**
	 * ��ȡSpectrumIds
	 * @return Collection<Comparable> 
	 */
	public Collection<Comparable> getSpectrumIds();

	/**
	 * ����mzArrayBinary�������� 
	 * @param SpectrumInfos
	 * @return ����������
	 */
	public double[][] getMZArrayBinary(Spectrum spectrum);
	
	/**
	 * ��ȡ���壬m/z��time�������
	 * @param SpectrumInfos
	 * @return  ���壬m/z��time�������
	 */
	public Map<String,String> getPeakMZTimeChargeState(Spectrum spectrum);

	/**
	 * ��ȡlev
	 * @param comparable
	 * @return lev
	 */
	public int getSpectrumMsLevel(Comparable comparable);
	
	/**
	 * ��ȡ�ʺ˱ȷ�Χ
	 * @param spectrum
	 * @return
	 */
	public Map<String,String> getMzRange(Spectrum spectrum);
	
	/**
	 * ��ȡ����
	 * @param spectrum
	 * @return
	 */
	public int getCount(Spectrum spectrum);
	
	/**
	 * ����spectrumId��ȡSpectrum
	 * @param spectrumId
	 * @return
	 */
	public Spectrum getSpectrum(Comparable spectrumId);
	
	/**
	 * ������е���ǿ��֮��
	 * @param specId
	 * @return
	 */
	public double getSumOfIntensity(Comparable specId);
	
	/**
	 * ��ò���ĸ���
	 * @param specId
	 * @return
	 */
	public int getNumberOfSpectrumPeaks(Comparable specId);
	
	/**
	 * �Ƿ�Ϊ�Ѷ���Ĺ���
	 * @param specId
	 * @return
	 */
	public boolean isIdentifiedSpectrum(Comparable specId);
	
}
