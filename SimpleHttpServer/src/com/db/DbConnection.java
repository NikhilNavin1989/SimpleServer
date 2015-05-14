package com.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.application.datamodel.ApplicationInfoData;
import com.application.datamodel.NetworkData;
import com.application.datamodel.TimeEventData;


public interface DbConnection {
	
	
	public void addTimeEvent(TimeEventData time) throws Exception ;
	
	public void addNetworkEvent(NetworkData n,String tid) throws Exception;
	
	public void addAPP(ApplicationInfoData app) throws Exception;
	


}
