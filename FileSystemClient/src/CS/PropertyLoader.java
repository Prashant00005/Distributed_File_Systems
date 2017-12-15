package CS;

import java.io.IOException;
import java.util.Properties;

public  class PropertyLoader {

	public static String Url_Server;
	public static String UrlRequest;
	public static String Url_DirectoryServer;
	public static String Url_LockServer;
	public static String Url_loginRequest;
	public static String Url_FileRead;
	public static String Url_GetLock;
	public static String Url_FileInfo;
	public static String Url_File_Write;
	public static String cacheLimitTTL;
	public static String Url_Realease_Lock;

	public static void loadProperties() {
		Properties config = new Properties();
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("configFile.properties"));
			
			
			Url_Server = config.getProperty("Url_Server");
		
			UrlRequest = config.getProperty("UrlRequest");
					
			Url_DirectoryServer = config.getProperty("Url_DirectoryServer");
			
			Url_LockServer = config.getProperty("Url_LockServer");
			
			Url_loginRequest = config.getProperty("Url_loginRequest");
			
			Url_FileRead = config.getProperty("Url_FileRead");
			
			Url_GetLock = config.getProperty("Url_GetLock");
			
			Url_FileInfo = config.getProperty("Url_FileInfo");
			
			Url_File_Write = config.getProperty("Url_File_Write");
			
			cacheLimitTTL = config.getProperty("cacheLimitTTL");
			
			Url_Realease_Lock = config.getProperty("Url_Realease_Lock");
			
		} catch (IOException e) {
			System.out.println("Failed to load property file"+e);
		}
	}
}