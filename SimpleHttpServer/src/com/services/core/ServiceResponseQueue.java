package com.services.core;

import java.util.concurrent.ConcurrentHashMap;

public class ServiceResponseQueue {
	
	private ConcurrentHashMap<Integer,ServiceRequest> serviceresponse = new ConcurrentHashMap<>();
	private static ServiceResponseQueue instance_ = new ServiceResponseQueue();
	
	private ServiceResponseQueue(){};
	
	public static ServiceResponseQueue getInstance(){
		
		return instance_;
	}
	
	public synchronized ServiceRequest getResponse(int id){
		System.out.println("Response for id "+id+"  "+serviceresponse.get(id));
		return serviceresponse.get(id);
	}
	
	public synchronized void addResponse(ServiceRequest request){
		
		serviceresponse.put(request.getRequestId(),request);
	}

}
