package com.application.datamodel;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeEventData implements DataModelInterface{
	
	private List<NetworkData> networkdatalist;
	private int _id;
	private String time;
	public List<NetworkData> getNetworkdatalist() {
		return networkdatalist;
	}
	public void setNetworkdatalist(List<NetworkData> networkdatalist) {
		this.networkdatalist = networkdatalist;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getTime() {
		
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
