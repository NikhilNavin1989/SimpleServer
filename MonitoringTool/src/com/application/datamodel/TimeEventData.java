package com.application.datamodel;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeEventData {
	
	private List<NetworkData> networkdatalist;
	private int _id;
	private Date time;
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(time);
	}
	public void setTime(Date time) {
		this.time = time;
	}

}
