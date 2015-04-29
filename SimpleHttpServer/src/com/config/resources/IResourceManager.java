package com.config.resources;

import com.services.core.Iservice;

public interface IResourceManager {
	
	public void init(String configxml);
	public Iservice getService(String resource); 

}
