package cqupt.swxxxy.oyc.service;

import java.util.List;

import cqupt.swxxxy.oyc.bean.Page;
import cqupt.swxxxy.oyc.bean.FileInfo;

public interface FileListService {
    /**
     * 获取上传文件路径
     * @return
     */
    public String getInputFilePath();
    
    /**
     * 获取文件夹中包含的文件个数
     * @param filePath
     * @return
     */
    public int getFileSize(String filePath);
    
    /**
     * 设置分页信息
     * @param filePath
     * @param currentPage
     */
    public Page getPageInfos(String filePath,int currentPage);
    
    /**
     * 获取上一页文件信息
     * @param page
     * @return
     */
    public List<FileInfo> getPreviouPage(List<FileInfo> fileInfoList,Page page);
    
    /**
     * 获取下一页文件信息
     * @param page
     * @return
     */
    public List<FileInfo> getNextPage(List<FileInfo> fileInfoList,Page page);
    
    /**
     * 获取首页文件信息
     * @param page
     * @return
     */
    public List<FileInfo> getFirstPage(List<FileInfo> fileInfoList,Page page);
    
    /**
     * 获取最后一页文件信息
     * @param page
     * @return
     */
    public List<FileInfo> getLastPage(List<FileInfo> fileInfoList,Page page);
    
    /**
     * 获取某一页码的文件信息
     * @param index
     * @return
     */
    public List<FileInfo> getAnyPage(List<FileInfo> fileInfoList,int index,Page page);
    
}
