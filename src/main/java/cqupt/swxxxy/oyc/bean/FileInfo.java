package cqupt.swxxxy.oyc.bean;

public class FileInfo {
    //文件id
    private int id;
    //文件名
    private String name;
    //文件大小
    private long size;
    //文件状态
    private String status;
	//文件上传时间
    private boolean isDir;
    //文件格式
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
