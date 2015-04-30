package com.config.resources;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigReader {
	
	public HashMap<String,String> getResources(File fXmlFile){
		
		 HashMap<String,String> resourcesMap = new HashMap<String,String>();
		
		try {
			 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			
		 
			NodeList nList = doc.getElementsByTagName("Resource");
		 
			
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					String rseourcename = eElement.getElementsByTagName("pathname").item(0).getTextContent();
					String classname = eElement.getElementsByTagName("classname").item(0).getTextContent();
					resourcesMap.put(rseourcename,classname);
		 
				}
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		
		return resourcesMap;
	}

	
}
