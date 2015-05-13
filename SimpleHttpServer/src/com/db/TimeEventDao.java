package com.db;

import java.sql.Connection;
import java.util.List;

import com.application.datamodel.TimeEventData;

public class TimeEventDao {



    private DatabaseAccess connection=null;
    
	public TimeEventDao(DatabaseAccess con){
		
		connection = con;
	}
	
	public boolean save(TimeEventData t){
		boolean ret = false;
		
			
			try {
				connection.addTimeEvent(t);
				ret= true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret= false;
			}
		
		
		return ret;
		
	}
	
	


}
