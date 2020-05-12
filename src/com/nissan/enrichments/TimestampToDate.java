package com.nissan.enrichments;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.allsight.entity.bean.BusinessObject;
import com.allsight.entity.impl.Entity;
import com.allsight.Party;


import com.allsight.enrichment.common.EnrichmentFunction;
import com.allsight.enrichment.common.ParamContext;
import com.allsight.util.TextUtils;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author Aradhana Pandey
 * Class to extract date from timestamp
 *
 */
@SuppressWarnings("deprecation")
public class TimestampToDate extends EnrichmentFunction {
	private static final Logger logger = Logger.getLogger(TimestampToDate.class);
	

	/* (non-Javadoc)
	 * @see com.allsight.enrichment.common.EnrichmentFunction#applyEnrichment(com.allsight.entity.impl.Entity)
	 */
	@Override
	public Object applyEnrichment(Entity asParty) throws Exception {
		
		 if (asParty instanceof Party) {
			 logger.debug("Enrichment to populate Date attribute");
			 
			 ParamContext paramContext = getParamContext();
			    
			    if (paramContext != null && paramContext.getPrimitiveParams() != null) {
			        String timestampAttrName = (String)paramContext.getPrimitiveParams().get("timestamp_attr_name");
			        String businessObject = (String)paramContext.getPrimitiveParams().get("business_object_name");
			        String dateAttrName = (String)paramContext.getPrimitiveParams().get("date_attr_name");
			        
			        logger.debug("party before enrichment : "  + asParty);
			        
			        if (((Party) asParty).getInteractions() != null && ((Party) asParty).getInteractions().getBusinessObjects(businessObject)!= null) {
			        	
			        	 Collection<BusinessObject> boColl = ((Party) asParty).getInteractions().getBusinessObjects(businessObject);
			        	 logger.debug("Collection : " + boColl);
			        	 
			        	 for (BusinessObject interact : boColl) {
			        		logger.debug("inside for");
			        		 if(interact.getAttribute(timestampAttrName) != null) {
			        			 logger.debug("inside if");
			        			 Timestamp timestamp = (Timestamp) interact.getAttribute(timestampAttrName);
			        			 Date date=new Date(timestamp.getTime());
			        			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			                     String strDate = dateFormat.format(date);  
			                     logger.debug("strDate : " + strDate);
			        			 interact.setAttribute(dateAttrName, strDate);
			        		 }
			        	 }
			        }
			    }
		 }
		logger.debug("party after enrichment : "  + asParty);
		return asParty;
	}

	@Override
	public String standardize(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}	 
	 