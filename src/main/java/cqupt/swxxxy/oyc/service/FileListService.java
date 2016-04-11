package cqupt.swxxxy.oyc.service;

import java.util.List;

import cqupt.swxxxy.oyc.bean.Page;
import cqupt.swxxxy.oyc.bean.FileInfo;

public interface FileListService {
    /**
     * ��ȡ�ϴ��ļ�·��
     * @return
     */
    public String getInputFilePath();
    
    /**
     * ��ȡ�ļ����а������ļ�����
     * @param filePath
     * @return
     */
    public int getFileSize(String filePath);
    
    /**
     * ���÷�ҳ��Ϣ
     * @param filePath
     * @param currentPage
     */
    public Page getPageInfos(String filePath,int currentPage);
    
    /**
     * ��ȡ��һҳ�ļ���Ϣ
     * @param page
     * @return
     */
    public List<FileInfo> getPreviouPage(List<FileInfo> fileInfoList,Page page);
    
    /**
     * ��ȡ��һҳ�ļ���Ϣ
     * @param page
     * @return
     */
    public List<FileInfo> getNextPage(List<FileInfo> fileInfoList,Page page);
    
    /**
     * ��ȡ��ҳ�ļ���Ϣ
     * @param page
     * @return
     */
    public List<FileInfo> getFirstPage(List<FileInfo> fileInfoList,Page page);
    
    /**
     * ��ȡ���һҳ�ļ���Ϣ
     * @param page
     * @return
     */
    public List<FileInfo> getLastPage(List<FileInfo> fileInfoList,Page page);
    
    /**
     * ��ȡĳһҳ����ļ���Ϣ
     * @param index
     * @return
     */
    public List<FileInfo> getAnyPage(List<FileInfo> fileInfoList,int index,Page page);
    
}
