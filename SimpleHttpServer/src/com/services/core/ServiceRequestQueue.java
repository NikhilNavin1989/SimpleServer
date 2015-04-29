package com.services.core;

import java.util.concurrent.ArrayBlockingQueue;

public class ServiceRequestQueue {
	
	private ArrayBlockingQueue<ServiceRequest> requestque=new ArrayBlockingQueue<ServiceRequest>(100);
	private static ServiceRequestQueue instance = new ServiceRequestQueue();
	
	
	private ServiceRequestQueue(){}
	
	
	public static ServiceRequestQueue getInstance(){
		
		return instance;
		
	}
	
	
	public ServiceRequest getRequest() {
		ServiceRequest r = null;

		try {
			r = requestque.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return r;

	}
	
	
	public boolean addRequest(ServiceRequest r){
		
		try {
			requestque.put(r);
			return true;
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
		
	}
	
	

}
