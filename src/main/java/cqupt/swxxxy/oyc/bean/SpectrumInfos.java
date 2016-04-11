package cqupt.swxxxy.oyc.bean;
/**
 * 谱图信息模型
 * @author Administrator
 *
 */
public class SpectrumInfos {
	//id号
	private int id;
	private int msLevel;
	//是否已经定义
	private int identified;
	//电荷
	private int charge;
	private double precursorMZ;
	//求和
	private double sumOfIntensity;
	//波峰数
	private int peaks;
	//存放路径
	private String path;
	private double precusorIndensity;
	//修饰数
	private String modifications;
	private String spectrumId;
	
	public String getSpectrumId() {
		return spectrumId;
	}
	public void setSpectrumId(String spectrumId) {
		this.spectrumId = spectrumId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMsLevel() {
		return msLevel;
	}
	public void setMsLevel(int msLevel) {
		this.msLevel = msLevel;
	}
	public int getIdentified() {
		return identified;
	}
	public void setIdentified(int identified) {
		this.identified = identified;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public double getPrecursorMZ() {
		return precursorMZ;
	}
	public void setPrecursorMZ(double precursorMZ) {
		this.precursorMZ = precursorMZ;
	}
	public double getSumOfIntensity() {
		return sumOfIntensity;
	}
	public void setSumOfIntensity(double sumOfIntensity) {
		this.sumOfIntensity = sumOfIntensity;
	}
	public int getPeaks() {
		return peaks;
	}
	public void setPeaks(int peaks) {
		this.peaks = peaks;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public double getPrecusorIndensity() {
		return precusorIndensity;
	}
	public void setPrecusorIndensity(double precusorIndensity) {
		this.precusorIndensity = precusorIndensity;
	}
	public String getModifications() {
		return modifications;
	}
	public void setModifications(String modifications) {
		this.modifications = modifications;
	}
	
}
