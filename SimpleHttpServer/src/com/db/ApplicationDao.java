package com.db;

import java.sql.Connection;

public class ApplicationDao {

    private DatabaseAccess connection=null;
	public ApplicationDao(DatabaseAccess con){
		
		connection = con;
	}
}
