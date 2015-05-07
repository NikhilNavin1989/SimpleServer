package com.application.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.application.datamodel.ApplicationInfoData;
import com.application.datamodel.NetworkData;
import com.application.datamodel.TimeEventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.core.ServiceRequest;

public class DataServicePeriod extends DataService {
	
	@Override
	public void handleGet(ServiceRequest request) {
		
        String fromtime = request.getPayload().getProperty("from");
        String to = request.getPayload().getProperty("to");
        System.out.println("Handling for a Month  from :"+fromtime+"     to:"+to);

		
		
		List<TimeEventData> tlist = new ArrayList<>();
		tlist.add(dummy());
		tlist.add(dummy());
		tlist.add(dummy());
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setResponse(mapper.writeValueAsString(tlist));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	
}
