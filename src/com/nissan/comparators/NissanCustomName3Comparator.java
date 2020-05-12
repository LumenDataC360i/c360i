package com.nissan.comparators;

import org.apache.log4j.Logger;

import com.allsight.bean.MatchReason;
import com.allsight.cme.comparator.Name3Comparator;


/**
 * @author Aradhana Pandey
 * Class to change the population file for Email to "international"
 *
 */
public class NissanCustomName3Comparator extends Name3Comparator{
	
	private static final Logger logger = Logger.getLogger(NissanCustomName3Comparator.class);
	
	/* (non-Javadoc)
	 * @see com.allsight.cme.comparator.Name3Comparator#compareUsingName3(java.lang.String, java.lang.String, com.allsight.bean.MatchReason)
	 */
	protected void compareUsingName3(String sourceRecord, String fileRecord, MatchReason reason) {
		
		if(this.getName3Field().equalsIgnoreCase("Email")) {
			this.setPopulation("international");
		}
		super.compareUsingName3(sourceRecord, fileRecord, reason);
		this.setPopulation("japan");
	}
}
