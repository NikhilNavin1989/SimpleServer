package com.application.services;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.application.datamodel.ApplicationInfoData;
import com.application.datamodel.Data;
import com.application.datamodel.NetworkData;
import com.application.datamodel.TimeEventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.core.Iservice;
import com.services.core.ServiceRequest;

public class DataService implements Iservice{
	
	@Override
	public void handleGet(ServiceRequest request) {
		System.out.println("Handling the uri "+request.getPayload().getProperty("id"));
		request.setResponse("Data Service Activated");
		ApplicationInfoData app = new ApplicationInfoData();
		app.setId(1);
		app.setName("Facebook");
		NetworkData ndata = new NetworkData();
		ndata.setAppid(app);
		ndata.setId(1);
		ndata.setType(1);
		ndata.setDownload(100000);
		ndata.setUpload(30000);
		TimeEventData time = new TimeEventData();
		List<NetworkData>  nlist = new ArrayList<NetworkData>();
		nlist.add(ndata);
		time.set_id(1);
		time.setNetworkdatalist(nlist);
		Date date = new Date(System.currentTimeMillis());
		//System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
		time.setTime(date);
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setResponse(mapper.writeValueAsString(time));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void handlePost(ServiceRequest request) {
		// TODO Auto-generated method stub
		
	}
}
