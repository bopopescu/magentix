
package es.upv.dsic.gti_ia.organization;

import java.util.*;
import java.io.*;

public class Configuration {
	
	
	public String serverName;
	public String databaseName;
	public String userName;
	public String password;
	public static String OMSServiceDesciptionLocation;
	public static String SFServiceDesciptionLocation;
	public static String THServiceDesciptionLocation;
	


	public Configuration()
	{

	
	//Cargamos los valores desde un archivo .xml 
	Properties properties = new Properties();

	   try {
		   properties.loadFromXML(Configuration.class.getResourceAsStream("/"+"THOMASDemoConfiguration.xml"));
			for (Enumeration<Object> e = properties.keys(); e.hasMoreElements() ; ) {
			    // Obtenemos el objeto
			    Object obj = e.nextElement();
			    if (obj.toString().equalsIgnoreCase("serverName"))
			    {
			    	serverName= properties.getProperty(obj.toString());	
			    }
			    else if (obj.toString().equalsIgnoreCase("databaseName"))
			    {
			    	databaseName= properties.getProperty(obj.toString());
			    }
			    else    if (obj.toString().equalsIgnoreCase("userName"))
			    {
			    	userName= properties.getProperty(obj.toString());
			    }
			    else    if (obj.toString().equalsIgnoreCase("password"))
			    {
			    	password= properties.getProperty(obj.toString());
			    }
			    else    if (obj.toString().equalsIgnoreCase("OMSServiceDesciptionLocation"))
			    {
			    	OMSServiceDesciptionLocation= properties.getProperty(obj.toString());
			    }
			    else    if (obj.toString().equalsIgnoreCase("SFServiceDesciptionLocation"))
			    {
			    	SFServiceDesciptionLocation= properties.getProperty(obj.toString()); 	
			    }else    if (obj.toString().equalsIgnoreCase("THServiceDesciptionLocation"))
			    {
			    	THServiceDesciptionLocation= properties.getProperty(obj.toString()); 	
			    }
			}

	    } catch (IOException e) {
	    	System.out.print(e);
	    }
	  

	
}

}