package com.example.monitorservices;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.http.server.myHTTPServer;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class AppMonitorService extends Service {

	
	private Map<String,String> appNamee= new HashMap<String, String>();

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(this, "servicecreated", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "servicestarted", Toast.LENGTH_LONG).show();
		try {
			myHTTPServer.startServer(CopyReadAssets());
			monitor();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void monitor() throws NameNotFoundException, IOException{
		

		Thread t=null;
		
		printInstalledAPPInfo();
		Runnable i = new Runnable() {
			
			@Override
			public void run() {
			int counter=1;
			   while(counter <50){
				ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		        List<RunningAppProcessInfo> runningApps = manager.getRunningAppProcesses();
		       // Log.d("#######################"," :::::"+runningApps.size());
		        try {
					getCurrentRunningApp();
				} catch (NameNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        for(RunningAppProcessInfo runningApp : runningApps){
		            // Get UID of the selected process
		              int uid = runningApp.uid;
		              
		              String name = runningApp.processName;
		              long received = TrafficStats.getUidRxBytes(uid);
		              long send   = TrafficStats.getUidTxBytes(uid);
		              Log.d("##nikhil", "uid:"+uid+",name:"+appNamee.get(name)+",package name:"+name+",Send:"+send +",Received:"+received);
		          }
		       
		        try {
					Thread.sleep(counter*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        counter++;
			}
			   stopSelf();
			}
		};

	    
		 t = new Thread(i);
		t.start();
		
	    Log.d("BGSERVICE", "update db finished");
	
	}
	
	
	void printInstalledAPPInfo() throws IOException{
		
		AssetManager am = this.getAssets();
		InputStream is = am.open("Conf.xml");
		
	    List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
	    for(int i=0;i<packs.size();i++) {
	        PackageInfo p = packs.get(i);
	        
	        //Log.d("Installed APPS:","APP name:"+p.applicationInfo.loadLabel(getPackageManager()).toString()+"  package :"+p.packageName);
	       // Log.d("###  am here ","1");
	        appNamee.put(p.packageName, p.applicationInfo.loadLabel(getPackageManager()).toString());
	        //Log.d("### am here ", "2");
	     }
	}
	
	
	void getCurrentRunningApp() throws NameNotFoundException{
		//Log.d("cuuuuuuuuuuuuuuuuuuuuuuuuuu","AAAAAAAAAAAAAAAAAAAAP");
		ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		// The first in the list of RunningTasks is always the foreground task.
		//Log.d("cuuuuuuuuuuuuuuuuuuuuuuuuuu","AAAAAAAAAAAAAAAAAAAAP");
		RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
		//Log.d("cuuuuuuuuuuuuuuuuuuuuuuuuuu","AAAAAAAAAAAAAAAAAAAAP "+foregroundTaskInfo);
		String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
		PackageManager pm = this.getPackageManager();
		//Log.d("cuuuuuuuuuuuuuuuuuuuuuuuuuu","AAAAAAAAAAAAAAAAAAAAP");
		PackageInfo foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName, 0);
		String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString();
		Log.d("###Current App:", "  "+foregroundTaskAppName);
		
	}
	
	private File CopyReadAssets() {
	    AssetManager assetManager = getAssets();

	    InputStream in = null;
	    OutputStream out = null;
	    File file = new File(getFilesDir(), "conf_prod.xml");
	    try {
	        in = assetManager.open("Conf.xml");
	        out = openFileOutput(file.getName(), this.MODE_WORLD_READABLE);
	        copyFile(in, out);
	        in.close();
	        in = null;
	        out.flush();
	        out.close();
	        out = null;
	    } catch (Exception e) {
	        Log.e("tag", e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return file;
	}

	private void copyFile(InputStream in, OutputStream out) {
		
		byte[] data = new byte[2048];
		try {
			while(in.read(data) != -1){
				
				out.write(data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("###file copy","  EXCEPTION NOT DONE");
		}
		
		Log.d("###file copy","  it done..............................");
	}

}
