package cqupt.swxxxy.oyc.dao.parse.mzIdentML.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cqupt.swxxxy.oyc.dao.parse.mzIdentML.ParseProteinDetectionList;

import uk.ac.ebi.pride.utilities.data.controller.impl.ControllerImpl.CachedDataAccessController;
import uk.ac.ebi.pride.utilities.data.core.CvParam;
import uk.ac.ebi.pride.utilities.data.core.Peptide;
import uk.ac.ebi.pride.utilities.data.core.Protein;

public class ParseProteinDetectionListImpl implements ParseProteinDetectionList{
	
	CachedDataAccessController cachedDataAccessController;
	
	public ParseProteinDetectionListImpl(CachedDataAccessController cachedDataAccessController) {
		this.cachedDataAccessController=cachedDataAccessController;
	}

	@Override
	public Collection<Comparable> getProteinIds() {
		return cachedDataAccessController. getProteinIds();
	}

	@Override
	public Protein getProteinById(Comparable proteinId) {
		return cachedDataAccessController.getProteinById(proteinId);
	}

	@Override
	public Map<String, String> getMascotScorAndDistinctPeptides(Protein protein) {
		Map<String,String> map=new HashMap<String,String>(); 
		List<CvParam> cvParamList=protein.getCvParams();
		for(CvParam cvParam:cvParamList){
			map.put(cvParam.getName(), cvParam.getValue());
		}
		return map;
	}

	@Override
	public List<Peptide> getPeptideList(Protein protein) {
		return protein.getPeptides();
	}

	@Override
	public Comparable getPeptideEvidenceId(Peptide peptide) {
		return peptide.getPeptideEvidence().getId();
	}

	@Override
	public Comparable getSpectrumIdentificationItem(Peptide peptide) {
		return peptide.getSpectrumIdentification().getId();
	}

	@Override
	public int getDistinctPeptides(Comparable proteinId) {
		return cachedDataAccessController.getNumberOfUniquePeptides(proteinId);
	}
	
	public int getNumberOfPTMs(Comparable proteinId) {
		return cachedDataAccessController.getNumberOfPTMs(proteinId);
	}

}
