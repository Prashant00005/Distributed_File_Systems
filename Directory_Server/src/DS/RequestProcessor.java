package DS;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;



@Path("/dslookup")
public class RequestProcessor {

	
	@POST
	@Consumes({"application/json"})
	@Path("/getFileInfo")
	public String getFileInfo(String input) {

		PropertyLoader.loadProperties();
		ResponseFromDS respDS = new ResponseFromDS();
		RequestToDS reqDS = new RequestToDS();
		String getFileInfoFromDSResponseString=new String();
		Sender send = new Sender();
		RequestToAS reqAS = new RequestToAS();
		ResponseFromAS respAS = new ResponseFromAS();
		reqDS = reqDS.getClassFromJsonString(input);
		HashMap<String, String> fileStats = new HashMap<String,String>();

		reqAS.setToken(reqDS.getToken());
		reqAS.setEncryptedUsername(reqDS.getEncryptedUsername());
		String authCheckRequest = reqAS.getJsonString();

		String authCheckResponse = send.AuthCheck(authCheckRequest);

		respAS = respAS.getClassFromJsonString(authCheckResponse);
		if(respAS.getAuthstatus().equals("Y")) {

			try {
				if(reqDS.getOperation().equals("r"))
					fileStats = send.LocationFileRequestRead(CryptoFunctions.decrypt(reqDS.getFilename(),respAS.getKey1()));
				else
					fileStats = send.LocationFileRequestWrite(CryptoFunctions.decrypt(reqDS.getFilename(),respAS.getKey1()));
				if(fileStats.isEmpty()) {
					respDS.setAuthstatus("Directory Information Not Available for the file"); 
					getFileInfoFromDSResponseString = respDS.getJsonString();
					return getFileInfoFromDSResponseString;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			respDS.setServerurl(CryptoFunctions.encrypt(fileStats.get("serverurl"),respAS.getKey1()));
			respDS.setDirectory(CryptoFunctions.encrypt(fileStats.get("directory"),respAS.getKey1()));
			respDS.setAuthstatus("Y");
		}else {
			respDS.setAuthstatus("Validation of token Failed");
		}

		getFileInfoFromDSResponseString = respDS.getJsonString();
		System.out.println(getFileInfoFromDSResponseString);
		return getFileInfoFromDSResponseString;
	}

	/*
	 * Get complete information about the directory
	 */
	@POST
	@Consumes({"application/json"})
	@Path("/getDirInfo")
	public String getDirInfo(String input) {
		PropertyLoader.loadProperties();
		RequestToDS reqDS = new RequestToDS();
		ResponseFromClient respClient = new ResponseFromClient();
		String strFile=new String();
		Sender send = new Sender();
		RequestToAS reqAS = new RequestToAS();
		ResponseFromAS respAS = new ResponseFromAS();
		reqDS = reqDS.getClassFromJsonString(input);
		HashMap<String, String> InfoFile = new HashMap<String,String>();
		reqAS.setToken(reqDS.getToken());
		reqAS.setEncryptedUsername(reqDS.getEncryptedUsername());
		String authCheckRequest = reqAS.getJsonString();
		String authCheckResponse = send.AuthCheck(authCheckRequest);
		respAS = respAS.getClassFromJsonString(authCheckResponse);
		if(respAS.getAuthstatus().equals("Y")) {
			HashMap<String, String> fileList = send.AllFileList();
			respClient.setFilenamearr(fileList.get("filename"));
			respClient.setDirectoryarr(fileList.get("directory"));
			return respClient.getJsonString();
		}else {
			return "{\"authstatus\":\"N\"}";
		}
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) {
		return "up and running";
	}
}