package cqupt.swxxxy.oyc.bean;

/**
 * 多肽信息模型
 * @author Administrator
 *
 */
public class PeptideInfos {
	//ID号
	private int id;
	//肽段序列
	private String peptide;
	private int fit;
	//计算获得，根据肽段序列计算理论的m/z，减去precursor m/z
	private float deltaMZ;
	//电荷
	private int charge;
	private double precursorMZ;
	private float precusorm;
	//可读的修饰数
	private String readablemod;
	//修饰个数
	private String modifications;
	private int missedcleave;
	//离子数
	private int lons;
	//搜索引擎打分
	private float mascotScore;
	//多肽长度
	private int length;
	//开始点
	private int start;
	//结束点
	private int stop;
	//该蛋白鉴定的所有肽段数，序列相同的肽段算多个
	private int psm;
	private int pi;
	//对应蛋白质id
	private int identificationId;
	private int cuttingSite;
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
	public String getPeptide() {
		return peptide;
	}
	public void setPeptide(String peptide) {
		this.peptide = peptide;
	}
	public int getFit() {
		return fit;
	}
	public void setFit(int fit) {
		this.fit = fit;
	}
	public float getDeltaMZ() {
		return deltaMZ;
	}
	public void setDeltaMZ(float deltaMZ) {
		this.deltaMZ = deltaMZ;
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
	public float getPrecusorm() {
		return precusorm;
	}
	public void setPrecusorm(float precusorm) {
		this.precusorm = precusorm;
	}
	public String getReadablemod() {
		return readablemod;
	}
	public void setReadablemod(String readablemod) {
		this.readablemod = readablemod;
	}
	public String getModifications() {
		return modifications;
	}
	public void setModifications(String modifications) {
		this.modifications = modifications;
	}
	public int getMissedcleave() {
		return missedcleave;
	}
	public void setMissedcleave(int missedcleave) {
		this.missedcleave = missedcleave;
	}
	public int getLons() {
		return lons;
	}
	public void setLons(int lons) {
		this.lons = lons;
	}
	public float getMascotScore() {
		return mascotScore;
	}
	public void setMascotScore(float mascotScore) {
		this.mascotScore = mascotScore;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getStop() {
		return stop;
	}
	public void setStop(int stop) {
		this.stop = stop;
	}
	public int getPsm() {
		return psm;
	}
	public void setPsm(int psm) {
		this.psm = psm;
	}
	public int getPi() {
		return pi;
	}
	public void setPi(int pi) {
		this.pi = pi;
	}
	public int getIdentificationId() {
		return identificationId;
	}
	public void setIdentificationId(int identificationId) {
		this.identificationId = identificationId;
	}
	public int getCuttingSite() {
		return cuttingSite;
	}
	public void setCuttingSite(int cuttingSite) {
		this.cuttingSite = cuttingSite;
	}
}
