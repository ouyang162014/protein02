package cqupt.swxxxy.oyc.bean;

/**
 * ��������Ϣģ��
 * @author Administrator
 *
 */
public class ProteinInfos {
	
	//������ID
	private int id;
	//����������
	private String protein;
	//״̬
	private int status;
	//������
	private float coverage;
	private float pi;
	private float threshold;
	//�Ķ���
	private int peptides;
	//��ͬ���Ķ���
	private int distinctPeptides;
	//�õ��׼������Ķε����θ������
	private int ptms;
	//��������id
	private int groupId;
	//����
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
