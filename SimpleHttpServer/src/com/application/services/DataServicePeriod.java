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
import com.db.DaoManager;
import com.db.DatabaseAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.core.ServiceRequest;

public class DataServicePeriod extends DataService {
	
	@Override
	public void handleGet(ServiceRequest request) {
		
        String fromtime = request.getPayload().getProperty("from");
        String to = request.getPayload().getProperty("to");
        System.out.println("Handling for a Month  from :"+fromtime+"     to:"+to);
        DaoManager dao = new DaoManager(new DatabaseAccess());
        List<NetworkData> tlist = dao.getNetworkdata(fromtime, to);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setResponse(mapper.writeValueAsString(tlist));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	
}
