package com.nissan.enrichments;


import java.util.Date;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;


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
 * Class to convert japanese date to english date format
 *
 */

public class JapanToEngDate extends EnrichmentFunction {

	private static final Logger logger = Logger.getLogger(JapanToEngDate.class);

	/* (non-Javadoc)
	 * @see com.allsight.enrichment.common.EnrichmentFunction#applyEnrichment(com.allsight.entity.impl.Entity)
	 */
	@Override
	public Object applyEnrichment(Entity<?> asParty) throws Exception {
		// TODO Auto-generated method stub

		if (asParty instanceof Party) {
			logger.debug("Enrichment to convert Japanese Date to English Date");

			ParamContext paramContext = getParamContext();

			if (paramContext != null && paramContext.getPrimitiveParams() != null) {
				String japanDateAttr = (String)paramContext.getPrimitiveParams().get("input_date_att_name");
				String businessObject = (String)paramContext.getPrimitiveParams().get("business_object_name");
				String engDateAttrName = (String)paramContext.getPrimitiveParams().get("output_date_attr_name");

				logger.debug("party before enrichment : "  + asParty);

				if (((Party) asParty).getDemographics() != null && ((Party) asParty).getDemographics().getBusinessObjects(businessObject)!= null) {

					Collection<BusinessObject> boColl = ((Party) asParty).getDemographics().getBusinessObjects(businessObject);
					logger.debug("Collection : " + boColl);

					for (BusinessObject bo : boColl) {
						logger.debug("inside for");
						if(bo.getAttribute(japanDateAttr)!=null && !TextUtils.isNullOrEmpty((String) bo.getAttribute(japanDateAttr))) {
							String jpDate = (String) bo.getAttribute(japanDateAttr);
							logger.debug("input japan date : " + jpDate);
							SimpleDateFormat west = new SimpleDateFormat("yyyy-MM-dd");
							SimpleDateFormat jp1= new SimpleDateFormat("yyyy年MM月dd日",Locale.JAPAN);      			   //Gregorian
							DateFormat jp2 =new SimpleDateFormat("GGGGyy年MM月dd日",new Locale("ja", "JP", "JP"));      //Imperial

							try{
								Date date = (Date) jp1.parse(jpDate);  //convert the String to date
								logger.debug("orig date : " + date);
								logger.debug("Date is from Gregorian calender");
								String westDate = west.format(date);
								bo.setAttribute(engDateAttrName, westDate);
								logger.debug(west.format(date)); //format the date to Western 
							}catch (Exception ex){       
								try {          //imperial
									Date date = jp2.parse(jpDate);
									logger.debug("Parsed date : " + date);
									String westDate = west.format(date);
									bo.setAttribute(engDateAttrName, westDate);
									logger.debug(west.format(date)); 
								} catch (ParseException e) {
									System.out.println(e.getMessage());
									e.printStackTrace();
								}
							}
						}

					}
				}	 
			}
		}
		return asParty;
	}

	@Override
	public String standardize(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public static void main(String[] args) {

		//String jpDate = "2008年12月31日";
		String jpDate = "平成17年10月25日";


		SimpleDateFormat west = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat jp1= new SimpleDateFormat("yyyy年MM月dd日",Locale.JAPAN);      			   //Gregorian
		DateFormat jp2 =new SimpleDateFormat("GGGGyy年MM月dd日",new Locale("ja", "JP", "JP"));      //Imperial

		try{
			Date date = (Date) jp1.parse(jpDate);  //convert the String to date
			System.out.println("orig date : " + date);
			System.out.println("Date is from Gregorian calender");
			System.out.println(west.format(date)); //format the date to Western 
		}catch (Exception ex){       
			try {          //imperial
				Date date = jp2.parse(jpDate);
				System.out.println("Parsed date : " + date);
				System.out.println(west.format(date)); 
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
