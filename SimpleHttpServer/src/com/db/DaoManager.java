package com.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.application.datamodel.NetworkData;
import com.application.datamodel.TimeEventData;

public class DaoManager {

	private DatabaseAccess connection;
	private ApplicationDao appDao;
	private NetworkDao netDao;
	private TimeEventDao timedao;
	
	public DaoManager(DatabaseAccess con){
		this.connection = con;
		if(timedao == null){
			timedao = new TimeEventDao(connection);
		}
		if(netDao == null){
			netDao = new NetworkDao(connection);
		}
		
	}
	
	public ApplicationDao getApplicationDao(){
		
		if(appDao == null){
			
			appDao = new ApplicationDao(this.connection);
		}
		
		return appDao;
	}
	
	public NetworkDao getNetworkDao(){
		if(netDao == null){
			netDao = new NetworkDao(connection);
		}
		return netDao;
	}
   	
	public TimeEventDao getTimeDao(){
		if(timedao == null){
			timedao = new TimeEventDao(connection);
		}
		return timedao;
	}
	
	public void Insert(TimeEventData timeevent){
		
		timedao.save(timeevent);
		netDao.save(timeevent.getNetworkdatalist(),timeevent.getTime());
		
	}
	
	public List<NetworkData> getNetworkdata(String start,String end){
		
		return netDao.get(start, end);
	}
	
}
