package cqupt.swxxxy.oyc.service.impl;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cqupt.swxxxy.oyc.bean.FileInfo;

public class FileListServiceBase{
	
	public static List<FileInfo> getAllFile(String filePath,String fileName,String inputTime) {
		// �������϶��󣬴洢�ļ���Ϣ
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		// Ŀ¼�ļ�
		File file = new File(filePath);
		// ��ȡ��Ŀ¼�µ������ļ�
		File[] files = file.listFiles();
		int id = 1;
		for (File fi : files) {
			//��ȡ�ļ���
			String fName=fi.getName();
			//��ȡ�ļ��ϴ�ʱ��
			String fInputTime=getFileTime(fi.getAbsolutePath());
			//�ж��ļ������ļ��ϴ�ʱ��
			if(!("".equals(fileName)) && !(null==fileName) && !("".equals(inputTime)) && !(null==inputTime)){
				if(fileName.equals(fName) && inputTime.equals(fInputTime)){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setId(id++);
					fileInfo.setName(fName);
					fileInfo.setSize(fi.length());
					fileInfo.setTime(fInputTime);
					fileList.add(fileInfo);
				}
			}else if(!("".equals(fileName)) && !(null==fileName)){
				if(fileName.equals(fName)){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setId(id++);
					fileInfo.setName(fName);
					fileInfo.setSize(fi.length());
					fileInfo.setTime(fInputTime);
					fileList.add(fileInfo);
				}
			}else if(!("".equals(inputTime)) && !(null==inputTime)){
				if(inputTime.equals(fInputTime)){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setId(id++);
					fileInfo.setName(fName);
					fileInfo.setSize(fi.length());
					fileInfo.setTime(fInputTime);
					fileList.add(fileInfo);
				}
			}else{
				FileInfo fileInfo = new FileInfo();
				fileInfo.setId(id++);
				fileInfo.setName(fName);
				fileInfo.setSize(fi.length());
				fileInfo.setTime(fInputTime);
				fileList.add(fileInfo);
			}
			
		}
		return fileList;
	}

	/**
	 * ��ȡ�ļ�����
	 * 
	 * @param fileAbsolutePath
	 * @return
	 */
	public static String getFileTime(String fileAbsolutePath) {
		File file = new File(fileAbsolutePath);
		// ��ȡ�ļ�����޸�ʱ��
		Long time = file.lastModified();
		// �������ڶ���
		Date d = new Date(time);
		// �������ڸ�ʽ
		Format simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// ������ת��Ϊָ����ʽ
		String dateString = simpleFormat.format(d);
		return dateString;
	}
}
