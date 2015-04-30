package com.config.resources;

import java.io.File;
import java.util.HashMap;

import com.services.core.Iservice;

public class ResourceManager implements IResourceManager{
	
	private HashMap<String,Iservice> ResourceMap = new HashMap<String,Iservice>();
	
	private static IResourceManager rmanager = new ResourceManager();
	
	private ResourceManager(){}
	
	public static IResourceManager getInstance(){
		
		return rmanager;
	}

	@Override
	public Iservice getService(String resource) {
		// TODO Auto-generated method stub
		return ResourceMap.get(resource);
	}

	@Override
	public void init(File configxml) {
		
		
		HashMap<String, String> conf  = new ConfigReader().getResources(configxml);
		for(String key : conf.keySet()){
			
			try {
				Class<?> serviceclass = Class.forName(conf.get(key));
				ResourceMap.put(key,(Iservice)serviceclass.newInstance());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
