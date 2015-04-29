package com.services.core;

import java.util.HashMap;
import java.util.Properties;

import com.config.resources.ResourceManager;

public class EngineServiceFactory {
	
	

	public static Iservice getService(String resource) {
		
		return ResourceManager.getInstance().getService(resource);
		
	
	}

}
