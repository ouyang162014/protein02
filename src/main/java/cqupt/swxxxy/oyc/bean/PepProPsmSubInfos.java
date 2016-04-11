package cqupt.swxxxy.oyc.bean;
/**
 * 蛋白质、多肽、谱图及所属子目录列表
 * @author Administrator
 *
 */
public class PepProPsmSubInfos {
	private int id;
	private int proteinId;
	private int peptideId;
	private int spectrumId;
	private int subId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProteinId() {
		return proteinId;
	}
	public void setProteinId(int proteinId) {
		this.proteinId = proteinId;
	}
	public int getPeptideId() {
		return peptideId;
	}
	public void setPeptideId(int peptideId) {
		this.peptideId = peptideId;
	}
	public int getSpectrumId() {
		return spectrumId;
	}
	public void setSpectrumId(int spectrumId) {
		this.spectrumId = spectrumId;
	}
	public int getSubId() {
		return subId;
	}
	public void setSubId(int subId) {
		this.subId = subId;
	}
	

}
