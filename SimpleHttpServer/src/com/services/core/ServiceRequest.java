package com.services.core;

import java.util.Properties;



public class ServiceRequest {
	
	
	
	public int getRequestId() {
		return requestId;
	}


	private EventCodes event;
	
	private Properties payload;
	
	private String resource;
	
	private String Response;
	
	private int requestId;
	
	
	public String getResponse() {
		return Response;
	}


	public void setResponse(String response) {
		Response = response;
	}


	public ServiceRequest(EventCodes code,String Resource,Properties p,int id) {
		
		
		this.event = code;
		this.payload = p;
		this.resource=Resource;
		this.requestId=id;
	}


	public String getResource() {
		return resource;
	}


	public void setResource(String resource) {
		this.resource = resource;
	}


	public EventCodes getEvent() {
		return event;
	}


	public void setEvent(EventCodes event) {
		this.event = event;
	}


	public Properties getPayload() {
		return payload;
	}


	public void setPayload(Properties payload) {
		this.payload = payload;
	}

}
