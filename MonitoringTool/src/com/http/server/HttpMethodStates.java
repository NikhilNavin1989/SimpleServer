package com.http.server;

import com.services.core.EngineServiceFactory;
import com.services.core.Iservice;
import com.services.core.ServiceRequest;

public enum HttpMethodStates {
	GET {
		@Override
		void handle(ServiceRequest request) {
			System.out.println("Handling GET Request ..........."+request.getResource());
			EngineServiceFactory.getService(request.getResource()).handle(request);
			
		}
	},POST {
		@Override
		void handle(ServiceRequest request) {
			System.out.println("Handling Post Request ...........");
			
			
		}
	};
	
	abstract void handle(ServiceRequest request);
    
	public static void handleEvent(ServiceRequest request){
    	
    	switch(request.getEvent()){
    	case GET:GET.handle(request);
    			 break;
    	case POST:POST.handle(request);
    			  break;
    	default:GET.handle(request);
    			break;
    	}
    }

}
