package com.db;

import java.sql.Connection;

public class ApplicationDao {

    private Connection connection=null;
	public ApplicationDao(Connection con){
		
		connection = con;
	}
}
