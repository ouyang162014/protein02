package cqupt.swxxxy.oyc.bean;

public class FileInfo {
    //�ļ�id
    private int id;
    //�ļ���
    private String name;
    //�ļ���С
    private long size;
    //�ļ�״̬
    private String status;
	//�ļ��ϴ�ʱ��
    private boolean isDir;
    //�ļ���ʽ
    private String format;
    
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public boolean isDir() {
        return isDir;
    }
    public void setDir(boolean isDir) {
        this.isDir = isDir;
    }
    private String time;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    

}
