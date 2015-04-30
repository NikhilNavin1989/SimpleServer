package com.http.server;

import java.io.*;
import java.net.*;



public class clent {
    public static void main(String[] args) throws IOException {
    final int PORT_NUMBER = 8989;
    final String HOSTNAME = InetAddress.getLocalHost().getHostName();

    //Attempt to connect
    try {
        Socket sock = new Socket(InetAddress.getByName("127.0.0.1"),PORT_NUMBER);//new Socket(HOSTNAME, PORT_NUMBER);
        System.out.println(" addess :"+sock.getLocalAddress());
           // PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        //Output
        //out.println("Test "+sock.getLocalAddress());
        InputStream in = sock.getInputStream();
        byte[] data= new byte[10];
        in.read(data);
        System.out.println("......................... ");
        //out.flush();

        //out.close();
        sock.close();
    } catch(Exception e) {
        e.printStackTrace();
    }
    }
}
