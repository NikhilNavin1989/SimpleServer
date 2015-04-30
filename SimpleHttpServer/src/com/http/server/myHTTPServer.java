package com.http.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.config.resources.ResourceManager;
import com.services.core.EventCodes;
import com.services.core.ServiceEngine;
import com.services.core.ServiceRequest;
import com.services.core.ServiceRequestQueue;
import com.services.core.ServiceResponseQueue;

public class myHTTPServer extends Thread {

	static final String HTML_START = "<html>"
			+ "<title>HTTP Server in java</title>" + "<body>";

	static final String HTML_END = "</body>" + "</html>";

	Socket connectedClient = null;
	BufferedReader inFromClient = null;
	DataOutputStream outToClient = null;
	public static int requestcount=0;

	public myHTTPServer(Socket client) {
		connectedClient = client;
	}
	
	public Properties printallQueryPrams(String queryline){
		
		
		System.out.println("Query line "+queryline);
		Properties qparams = new Properties();
		if(queryline != null){
		StringTokenizer params = new StringTokenizer(queryline, "&");
		while(params.hasMoreElements()){
			String keyvalue =params.nextToken();
			StringTokenizer kv = new StringTokenizer(keyvalue, "=");
			String key=kv.nextToken();
			String val = kv.nextToken();
			qparams.put(key, val);
		}
		}
		return qparams;
	}
	
	public void prinall(String line) throws Exception{
		StringTokenizer tokenizer = new StringTokenizer(line);
		String verb = tokenizer.nextToken();
		 
		if(verb.equals("GET")){
			EventCodes event = EventCodes.GET;
			String resourcepath = tokenizer.nextToken();
			
			StringTokenizer querypattern = new StringTokenizer(resourcepath,"?");
			ServiceRequest request=null;
			String queryparams=null;
			if(!(querypattern.countTokens() == 1)){
				System.out.println("QUERY PARAM IS "+resourcepath);
				resourcepath = querypattern.nextToken();
				queryparams = querypattern.nextToken();
				request = new ServiceRequest(event,resourcepath,printallQueryPrams(queryparams),requestcount++);
			}
			else{
				
				request = new ServiceRequest(event,resourcepath,printallQueryPrams(queryparams),requestcount++);
			}
			
			
			
			boolean isadded = ServiceRequestQueue.getInstance().addRequest(request);
			System.out.println("Request added "+isadded);
			if(isadded){
				
				while(ServiceResponseQueue.getInstance().getResponse(request.getRequestId()) == null){
					
					sleep(10);
					System.out.println("sleeeping.........................");
				}
				System.out.println("Sending response "+request.getResponse());
				sendResponse(200,request.getResponse(),false);
			}

		}
		
	}

	
	public void handle(String headerLine) throws Exception{
		
		String requestString = headerLine;
		
        StringTokenizer tokenizer = new StringTokenizer(headerLine);
		String httpMethod = tokenizer.nextToken();
		String httpQueryString = tokenizer.nextToken();

		StringBuffer responseBuffer = new StringBuffer();
		responseBuffer
				.append("<b> This is the HTTP Server Home Page.... </b><BR>");
		responseBuffer.append("The HTTP Client request is ....<BR>");

		//System.out.println("The HTTP request string is ....");
		while (inFromClient.ready()) {
			// Read the HTTP complete HTTP Query
			responseBuffer.append(requestString + "<BR>");
			//System.out.println(requestString);
			requestString = inFromClient.readLine();
		}

		if (httpMethod.equals("GET")) {
			if (httpQueryString.equals("/")) {
				// The default home page
				sendResponse(200, responseBuffer.toString(), false);
			} else {
				// This is interpreted as a file name
				String fileName = httpQueryString.replaceFirst("/", "");
				fileName = URLDecoder.decode(fileName);
				if (new File(fileName).isFile()) {
					sendResponse(200, fileName, true);
				} else {
					sendResponse(
							404,
							"<b>The Requested resource not found ...."
									+ "Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>",
							false);
				}
			}
		} else
			sendResponse(
					404,
					"<b>The Requested resource not found ...."
							+ "Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>",
					false);
	
	}
	public void run() {

		try {

			System.out.println("The Client " + connectedClient.getInetAddress()
					+ ":" + connectedClient.getPort() + " is connected");

			inFromClient = new BufferedReader(new InputStreamReader(
					connectedClient.getInputStream()));
			outToClient = new DataOutputStream(
					connectedClient.getOutputStream());
			String header =inFromClient.readLine();
			prinall(header);
			//sendResponse(200,"accepted",false);
			 
			//System.out.println("#########main continues");
			//handle(header);

			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendResponse(int statusCode, String responseString,
			boolean isFile) throws Exception {

		String statusLine = null;
		String serverdetails = "Server: Java HTTPServer";
		String contentLengthLine = null;
		String fileName = null;
		String contentTypeLine = "Content-Type: text/html" + "\r\n";
		FileInputStream fin = null;

		if (statusCode == 200)
			statusLine = "HTTP/1.1 200 OK" + "\r\n";
		else
			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";

		if (isFile) {
			fileName = responseString;
			fin = new FileInputStream(fileName);
			contentLengthLine = "Content-Length: "
					+ Integer.toString(fin.available()) + "\r\n";
			if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
				contentTypeLine = "Content-Type: \r\n";
		} else {
			responseString = myHTTPServer.HTML_START + responseString
					+ myHTTPServer.HTML_END;
			contentLengthLine = "Content-Length: " + responseString.length()
					+ "\r\n";
		}

		outToClient.writeBytes(statusLine);
		outToClient.writeBytes(serverdetails);
		outToClient.writeBytes(contentTypeLine);
		outToClient.writeBytes(contentLengthLine);
		outToClient.writeBytes("Connection: close\r\n");
		outToClient.writeBytes("\r\n");

		if (isFile)
			sendFile(fin, outToClient);
		else
			outToClient.writeBytes(responseString);

		outToClient.close();
	}

	public void sendFile(FileInputStream fin, DataOutputStream out)
			throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = fin.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		fin.close();
	}


	
	public static void main(String args[]) throws Exception {
		/*ResourceManager.getInstance().init("C:\\Users\\Lenovo\\Desktop\\conf.xml");
        Thread serv = new Thread(ServiceEngine.IDLE);
        serv.start();
		ServerSocket Server = new ServerSocket(5001, 10,
				InetAddress.getByName("192.168.75.1"));
		System.out.println("TCPServer Waiting for client on port 5001");

		while (true) {
			Socket connected = Server.accept();
			System.out.println("Accepted...........................");
			myHTTPServer s = new myHTTPServer(connected);
			s.start();
			
			//(new myHTTPServer(connected)).start();
		}*/
	
	startServer();	
	}
	
	public static void  startServer()throws Exception{

		ResourceManager.getInstance().init("C:\\Users\\Lenovo\\Desktop\\conf.xml");
        Thread serv = new Thread(ServiceEngine.IDLE);
        serv.start();
        
        Thread httpserv = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ServerSocket Server=null;
				try {
					
					Server = new ServerSocket(5001, 10,InetAddress.getByName("127.0.0.1"));
					
				 
				System.out.println("TCPServer Waiting for client on port 5001");

				while (true) {
					Socket connected = Server.accept();
					System.out.println("Accepted...........................");
					myHTTPServer s = new myHTTPServer(connected);
					s.start();
					
					//(new myHTTPServer(connected)).start();
				}
			}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        httpserv.start();
        

	
	}
}