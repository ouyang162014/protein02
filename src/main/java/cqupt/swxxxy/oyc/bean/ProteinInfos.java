package cqupt.swxxxy.oyc.bean;

/**
 * 蛋白质信息模型
 * @author Administrator
 *
 */
public class ProteinInfos {
	
	//蛋白质ID
	private int id;
	//蛋白质名称
	private String protein;
	//状态
	private int status;
	//覆盖率
	private float coverage;
	private float pi;
	private float threshold;
	//肽段数
	private int peptides;
	//不同的肽段数
	private int distinctPeptides;
	//该蛋白鉴定的肽段的修饰个数求和
	private int ptms;
	//蛋白质组id
	private int groupId;
	//名称
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProtein() {
		return protein;
	}
	public void setProtein(String protein) {
		this.protein = protein;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getCoverage() {
		return coverage;
	}
	public void setCoverage(float coverage) {
		this.coverage = coverage;
	}
	public float getPi() {
		return pi;
	}
	public void setPi(float pi) {
		this.pi = pi;
	}
	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	public int getPeptides() {
		return peptides;
	}
	public void setPeptides(int peptides) {
		this.peptides = peptides;
	}
	public int getDistinctPeptides() {
		return distinctPeptides;
	}
	public void setDistinctPeptides(int distinctPeptides) {
		this.distinctPeptides = distinctPeptides;
	}
	public int getPtms() {
		return ptms;
	}
	public void setPtms(int ptms) {
		this.ptms = ptms;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
