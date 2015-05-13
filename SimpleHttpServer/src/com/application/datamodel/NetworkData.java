package com.application.datamodel;

public class NetworkData implements DataModelInterface{
	
	private int id;
	private int type;
	private ApplicationInfoData appid;
	private long upload;
	private long download;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ApplicationInfoData getAppid() {
		return appid;
	}
	public void setAppid(ApplicationInfoData appid) {
		this.appid = appid;
	}
	public long getUpload() {
		return upload;
	}
	public void setUpload(long upload) {
		this.upload = upload;
	}
	public long getDownload() {
		return download;
	}
	public void setDownload(long download) {
		this.download = download;
	}
	

}
