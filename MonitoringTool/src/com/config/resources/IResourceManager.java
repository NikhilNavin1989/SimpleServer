package com.config.resources;

import java.io.File;

import com.services.core.Iservice;

public interface IResourceManager {
	
	public void init(File configxml);
	public Iservice getService(String resource); 

}
