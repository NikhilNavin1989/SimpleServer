package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.application.datamodel.ApplicationInfoData;
import com.application.datamodel.NetworkData;
import com.application.datamodel.TimeEventData;

public class DatabaseAccess implements DbConnection {

	private static String db="";
	private Connection connection ;
    private static HashMap<String,Integer> timeIdMap = new HashMap<>(); 
    private static HashMap<String,Integer> AppIdMap = new HashMap<>(); 
	private static boolean created = false;
	private static Random ran = new Random();
	private static Random appran = new Random();
	
	private PreparedStatement addTimeevent,addNetworkData,addApp,getNetwork,getNetworkPeriod,gettimeDataID,gettimeDataPeriod;
	
	{
		
		
	}

	public DatabaseAccess(){
		
		try {
			connection = DriverManager.getConnection(db);
			this.createpreparedstatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
	
	
	public  static boolean createDB(String Path){
		
		if(!created){
			try {
				db=Path;//"jdbc:sqlite:C://Users//Lenovo//Documents//understanding//myjava//Application//db//mydb.db";
				init();
				created=true;
				return true;
				
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		
		return false;
	}
	
	private  static void init() throws Exception{
		
		System.out.println("init called :"+db);
		Connection connection = DriverManager.getConnection(db);
		if(! created){
			Statement s = connection.createStatement();
			s.executeUpdate("CREATE TABLE timeEvent_  (eid_ int,time timestamp)");
			s.executeUpdate("CREATE TABLE AppData_  (aid_ int,name varchar(200))");
			s.executeUpdate("CREATE TABLE networkdata_  (nid_  int,timeid_ int ,appid_ int ,nettype int,upload int,download int,FOREIGN KEY(timeid_) REFERENCES timeEvent_(eid_),FOREIGN KEY(appid_) REFERENCES AppData_(aid_))");
			System.out.println("created table succesfully");
			}
		
	}

	private void createpreparedstatement() throws Exception {
		
		addTimeevent = connection.prepareStatement("INSERT INTO timeEvent_ (eid_ ,time) VALUES (?,?)");
		addNetworkData = connection.prepareStatement("INSERT INTO networkdata_ (nid_ ,timeid_,appid_,nettype,upload,download) VALUES (?,?,?,?,?,?)");
		addApp = connection.prepareStatement("INSERT INTO AppData_ (aid_ ,name) VALUES (?,?)");
		getNetwork= connection.prepareStatement("SELECT *  FROM networkdata_  WHERE timeid_ = ?");
		getNetworkPeriod = connection.prepareStatement("SELECT time,nettype,appid_,upload,download  FROM timeEvent_ INNER JOIN networkdata_ on  timeEvent_.eid_ = networkdata_.timeid_ and timeEvent_.time > ?");
		//getphototag= connection.prepareStatement("SELECT pid_  FROM (SELECT *  FROM photo_ INNER JOIN tag_   ON photo_.pid_ = tag_.imageid_) WHERE tagname_ IN []");
	}

	
	

	@Override
	public void addTimeEvent(TimeEventData time) throws Exception {
		Integer pid = ran.nextInt();//time.get_id();// hav a generator here
		
		String timeinfo = time.getTime();
		
		Timestamp timestamp=null;
		try{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		    Date parsedDate = dateFormat.parse(timeinfo);
		     timestamp = new java.sql.Timestamp(parsedDate.getTime());
		}catch(Exception e){//this generic but you can control another types of exception
		 
			e.printStackTrace();
		}
		addTimeevent.setInt(1, pid);
		addTimeevent.setTimestamp(2,timestamp);
		addTimeevent.execute();
		timeIdMap.put(timeinfo, pid);
		System.out.println("added photo succesfully");

	}
	
	
	@Override
	public void addNetworkEvent(NetworkData n,int tid) throws Exception {
		
		
			
			int nid = n.getId();
			int appid =getAppid(n.getAppid().getName());
			long upload = n.getUpload();
			long download = n.getDownload();
			int type = n.getType();
			addNetworkData.setInt(1, nid);
			addNetworkData.setInt(2,tid);
			addNetworkData.setInt(3,appid);
			addNetworkData.setInt(4,type);
			addNetworkData.setLong(5,upload);
			addNetworkData.setLong(6,download);
			addNetworkData.execute();
			System.out.println("added photo succesfully");
		
		
		

	}

	@Override
	public void addAPP(ApplicationInfoData app) throws Exception {
		
		int aid = app.getId();
		String name  = app.getName();
		
		addApp.setInt(1,aid);
		addApp.setString(2,name);
		addApp.execute();
		
	}
	
	private String formatdate(String raw){
		
		char[] arr = raw.toCharArray();
		char[] out = new char[arr.length+1];
		int count=0;
		for(char i : arr){
			if(count == 10){
				
				out[count++]=' ';
			}
			out[count++]=i;
			
			
		}
		return new String(out);
	}
	
	public List<NetworkData> getNetworkData(String start,String end) throws SQLException{
		List<NetworkData> result = new ArrayList<>();
		Timestamp timestamp=null;
		
		try{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		    Date parsedDate = dateFormat.parse(formatdate(start));
		     timestamp = new java.sql.Timestamp(parsedDate.getTime());
		}catch(Exception e){//this generic but you can control another types of exception
		 
			e.printStackTrace();
		}
		
		getNetworkPeriod.setTimestamp(1,timestamp);
		
		ResultSet rs=getNetworkPeriod.executeQuery();
		int c =0;
		
		while(rs.next())
	      {
			c=c+1;
	        NetworkData n = new NetworkData();
	        
	       n.setType(rs.getInt("nettype"));
	       n.setUpload(rs.getLong("upload"));
	       n.setDownload(rs.getLong("download"));
	       ApplicationInfoData ap = new ApplicationInfoData();
	       ap.setId(rs.getInt("appid_"));
	       ap.setName("sample");
	       n.setAppid(ap);
	       result.add(n);
	        
	      }
		
        

	      
	      return result;
		
	
		
		}
	
	public List<NetworkData> getNetworkData(int timeid,int appid) throws SQLException{
		
		List<NetworkData> result = new ArrayList<>();
		
		getNetwork.setInt(1,timeid);
		
		ResultSet rs=getNetwork.executeQuery();
		int c =0;
		
		while(rs.next())
	      {
			c=c+1;
	        NetworkData n = new NetworkData();
	        
	       n.setId(rs.getInt("pid_"));// read the result set
	       n.setType(rs.getInt("nettype"));
	       n.setUpload(rs.getLong("upload"));
	       n.setDownload(rs.getLong("download"));
	       result.add(n);
	        
	      }
		
        

	      
	      return result;
	}


	
	String makePlaceholders(int len) {
	    if (len < 1) {
	        // It will lead to an invalid query anyway ..
	        throw new RuntimeException("No placeholders");
	    } else {
	        StringBuilder sb = new StringBuilder(len * 2 - 1);
	        sb.append("?");
	        for (int i = 1; i < len; i++) {
	            sb.append(",?");
	        }
	        return sb.toString();
	    }
	}
	
	public static  Integer getTimeId(String time ){
		
		return timeIdMap.get(time);
	}
	
	public static Integer getAppid(String appname){
		
		       if(!AppIdMap.containsKey(appname)){
		    	   
		    	   AppIdMap.put(appname,appran.nextInt());
		       }
				return AppIdMap.get(appname);
	}
	
}
