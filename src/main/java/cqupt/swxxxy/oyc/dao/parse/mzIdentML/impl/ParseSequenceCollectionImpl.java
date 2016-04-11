package cqupt.swxxxy.oyc.dao.parse.mzIdentML.impl;

import java.util.List;

import cqupt.swxxxy.oyc.dao.parse.mzIdentML.ParseSequenceCollection;

import uk.ac.ebi.pride.utilities.data.controller.impl.ControllerImpl.CachedDataAccessController;
import uk.ac.ebi.pride.utilities.data.core.Modification;
import uk.ac.ebi.pride.utilities.data.core.Peptide;

public class ParseSequenceCollectionImpl implements ParseSequenceCollection{
	
	CachedDataAccessController cachedDataAccessController;
	
	public ParseSequenceCollectionImpl(){}
	
	public ParseSequenceCollectionImpl(CachedDataAccessController cachedDataAccessController){
		this.cachedDataAccessController=cachedDataAccessController;
	}
	
	@Override
	public int getStart(Peptide peptide) {
		return peptide.getPeptideEvidence().getStartPosition();
	}

	@Override
	public int getEnd(Peptide peptide) {
		return peptide.getPeptideEvidence().getEndPosition();
	}

	@Override
	public Comparable getDBSequence_ref(Peptide peptide) {
		return peptide.getPeptideEvidence().getDbSequence().getId();
	}

	@Override
	public String getPeptideSequence(Peptide peptide) {
		return peptide.getPeptideEvidence().getDbSequence().getSequence();
	}
	
	public String getModifications(Peptide peptide) {
		//获取Modification对象集合
		List<Modification> modificationList=peptide.getModifications();
		StringBuilder sb=new StringBuilder();
		if(modificationList!=null && modificationList.size()>0){
			sb.append("ModificationItem:").append(modificationList.size()).append("{");
			for(Modification modification:modificationList){
				//sb.append("name:").append(modification.getName()).append(",");
				//sb.append("ModAccession:").append(modification.getId().toString()).append(",");
				List<Double> modMonoDeltaList=modification.getMonoisotopicMassDelta();
				if(modMonoDeltaList!=null && modMonoDeltaList.size()>0){
					for(double modMonoDelta:modMonoDeltaList){
						sb.append("modMonoDelta:").append(modMonoDelta).append(",");
					}
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append(";");
			}
			sb.append("}");
			return sb.toString();
		}else{
			return "";
		}
		
	}

	@Override
	public int getNumberOfQuantPTMs(Comparable proteinId) {
		return cachedDataAccessController.getNumberOfQuantPTMs(proteinId);
	}

}
