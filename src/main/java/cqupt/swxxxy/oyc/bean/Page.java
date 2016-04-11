package cqupt.swxxxy.oyc.bean;

public class Page {
    //页数
    private int pageSize;
    //每页条目数
    private int pageCounts;
    //当前页码
    private int currentPage;
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageCounts() {
        return pageCounts;
    }
    public void setPageCounts(int pageCounts) {
        this.pageCounts = pageCounts;
    }

}
