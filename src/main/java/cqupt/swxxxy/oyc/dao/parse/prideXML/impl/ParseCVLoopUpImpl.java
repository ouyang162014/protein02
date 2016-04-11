package cqupt.swxxxy.oyc.dao.parse.prideXML.impl;

import java.util.List;

import cqupt.swxxxy.oyc.dao.parse.prideXML.ParseCVLoopUp;

import uk.ac.ebi.pride.utilities.data.controller.impl.ControllerImpl.PrideXmlControllerImpl;
import uk.ac.ebi.pride.utilities.data.core.CVLookup;

public class ParseCVLoopUpImpl implements ParseCVLoopUp{
	PrideXmlControllerImpl cachedDataAccessController;
	
	public ParseCVLoopUpImpl(PrideXmlControllerImpl cachedDataAccessController){
		this.cachedDataAccessController=cachedDataAccessController;
	}
	
	public List<CVLookup> getCvLookups() {
		List<CVLookup> lookUpList=cachedDataAccessController.getCvLookups();
		return lookUpList;
	}

	public String getCvLookupLable(CVLookup cVLookup) {
		return cVLookup.getCvLabel();
	}

	public String getCvLookupFullName(CVLookup cVLookup) {
		return cVLookup.getFullName();
	}

	public String getCvLookVersion(CVLookup cVLookup) {
		return cVLookup.getVersion();
	}

	public String getCvLookupAddress(CVLookup cVLookup) {
		return cVLookup.getAddress();
	}

}
