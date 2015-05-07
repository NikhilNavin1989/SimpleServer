package com.application.services;

import java.util.Date;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.application.datamodel.ApplicationInfoData;
import com.application.datamodel.Data;
import com.application.datamodel.NetworkData;
import com.application.datamodel.TimeEventData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.core.Iservice;
import com.services.core.ServiceRequest;

public class DataService implements Iservice{
	
	@Override
	public void handleGet(ServiceRequest request) {

		System.out.println("base data called....");
		request.setResponse("Data Service Activated");
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setResponse(mapper.writeValueAsString(dummy()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void handlePost(ServiceRequest request) {
		System.out.println("Handling the uri "+request.getPayload().getProperty("data"));
		ObjectMapper mapper = new ObjectMapper();
		try {
			TimeEventData tdata = mapper.readValue(request.getPayload().getProperty("data"),TimeEventData.class);
			System.out.println("## object created "+tdata.getTime());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setResponse("Data Service Activated");
	}
	
	public TimeEventData dummy(){
		
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
		List<NetworkData>  nlist = new ArrayList<>();
		nlist.add(ndata);
		time.set_id(1);
		time.setNetworkdatalist(nlist);
		Date date = new Date(System.currentTimeMillis());
		//System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		time.setTime(dateFormat.format(date));
		return time;
	}
}
