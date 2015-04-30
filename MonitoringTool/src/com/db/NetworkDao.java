package com.db;

import java.sql.Connection;

public class NetworkDao {


    private Connection connection=null;
    
	public NetworkDao(Connection con){
		
		connection = con;
	}
}
