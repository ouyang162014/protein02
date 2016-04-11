package cqupt.swxxxy.oyc.dao.parse.prideXML;

import java.util.List;
import java.util.Map;

import uk.ac.ebi.pride.utilities.data.core.ParamGroup;

/**
 * Э��ģ������ӿ�
 * @author ŷ����
 *
 */
public interface ParseProtocal {
	/**
	 * ��ȡЭ������
	 * @return protocolName
	 */
	public String getProtocolName();
	
	/**
	 * ��ȡЭ�鲽��
	 * @return
	 */
	public List<ParamGroup> getProtocolSteps();
	
	/**
	 * ��ȡparamGroup�����ƺ�ֵ
	 * @param paramGroup
	 * @return
	 */
	public Map<String,String> getProtocolStepCvParam(ParamGroup paramGroup);
}
