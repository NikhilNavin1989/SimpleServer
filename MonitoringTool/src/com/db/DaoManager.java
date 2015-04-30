package com.db;

import java.sql.Connection;

public class DaoManager {

	private Connection connection;
	private ApplicationDao appDao;
	private NetworkDao netDao;
	private TimeEventDao timedao;
	
	public DaoManager(Connection con){
		this.connection = con;
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
	
}
