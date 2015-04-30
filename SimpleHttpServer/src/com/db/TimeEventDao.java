package com.db;

import java.sql.Connection;

public class TimeEventDao {



    private Connection connection=null;
    
	public TimeEventDao(Connection con){
		
		connection = con;
	}


}
