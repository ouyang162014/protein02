package cqupt.swxxxy.oyc.dao.parse.prideXML.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cqupt.swxxxy.oyc.dao.parse.prideXML.ParseProtocal;

import uk.ac.ebi.pride.utilities.data.controller.impl.ControllerImpl.PrideXmlControllerImpl;
import uk.ac.ebi.pride.utilities.data.core.CvParam;
import uk.ac.ebi.pride.utilities.data.core.ExperimentProtocol;
import uk.ac.ebi.pride.utilities.data.core.ParamGroup;

public class ParseProtocalImpl implements ParseProtocal{
	PrideXmlControllerImpl cachedDataAccessController;
	
	public ParseProtocalImpl(PrideXmlControllerImpl cachedDataAccessController){
		this.cachedDataAccessController=cachedDataAccessController;
	}
	
	public String getProtocolName() {
		ExperimentProtocol experimentProtocol=cachedDataAccessController.getProtocol();
		return experimentProtocol.getName();
	}

	public List<ParamGroup> getProtocolSteps() {
		ExperimentProtocol experimentProtocol=cachedDataAccessController.getProtocol();
		List<ParamGroup> paramGroupList=experimentProtocol.getProtocolSteps();
		return paramGroupList;
	}

	public Map<String, String> getProtocolStepCvParam(ParamGroup paramGroup) {
		Map<String,String> cvParams=new HashMap<String,String>();
		List<CvParam> cvParamList=paramGroup.getCvParams();
		for(CvParam cvParam:cvParamList){
			String name=cvParam.getName();
			String value=cvParam.getValue();
			cvParams.put(name, value);
		}
		return cvParams;
	}

}
