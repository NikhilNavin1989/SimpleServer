package com.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.w3c.dom.ls.LSInput;

import com.application.datamodel.NetworkData;
import com.application.datamodel.TimeEventData;

public class NetworkDao {


    private DatabaseAccess connection=null;
    
	public NetworkDao(DatabaseAccess con){
		
		connection = con;
	}
	
	public boolean save(List<NetworkData> netlist,String time){
		boolean ret = false;
		Integer tid = connection.getTimeId(time);
		if(tid != null){
			
			try {
				for(NetworkData n : netlist){
					System.out.println("#########  nikihl Appid "+n.getAppid().getName());
					connection.addNetworkEvent(n, tid);
				}
				ret= true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret= false;
			}
		}
		
		return ret;
		
	}
	
	
	public List<NetworkData> get(String start,String end){
		List<NetworkData> res =null;
		try {
			 res =connection.getNetworkData(start, end);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
}
