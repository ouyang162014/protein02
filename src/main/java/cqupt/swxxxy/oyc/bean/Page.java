package cqupt.swxxxy.oyc.bean;

public class Page {
    //ҳ��
    private int pageSize;
    //ÿҳ��Ŀ��
    private int pageCounts;
    //��ǰҳ��
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
