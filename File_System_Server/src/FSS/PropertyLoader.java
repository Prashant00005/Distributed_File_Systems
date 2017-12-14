package FSS;

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
	

	public static void loadProperties() {
		Properties config = new Properties();
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("configFile.properties"));
			
			
			Url_Server = config.getProperty("Url_Server");
		
			UrlRequest = config.getProperty("UrlRequest");
			
			
			
		} catch (IOException e) {
			System.out.println("Failed to load property file"+e);
		}
	}
}