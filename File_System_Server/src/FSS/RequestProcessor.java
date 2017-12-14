package FSS;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


@Path("/reader")
public class RequestProcessor {
	
	@POST
	@Consumes({"application/json"})
	@Path("/readFile")
	public String readFile(String input) throws UnsupportedEncodingException {
		PropertyLoader.loadProperties();
		RequestRead reqRead = new RequestRead();
		reqRead = reqRead.getClassFromJsonString(input);
		String token = reqRead.getToken();
		String encryptedUsername = reqRead.getEncryptedUsername();
		String dir = reqRead.getDirectory();
		String nameFile = reqRead.getFilename();
		
		RequestToAS reqAS = new RequestToAS();
		ResponseFromAS respAS = new ResponseFromAS();
		Sender sender = new Sender();
		ResponseRead respRead = new ResponseRead();
		String path = "";
		reqAS.setToken(token);
		reqAS.setEncryptedUsername(encryptedUsername);
		String authCheckRequest = reqAS.getJsonString();
		String authCheckResponse = sender.AuthCheck(authCheckRequest);
		respAS = respAS.getClassFromJsonString(authCheckResponse);
		if(respAS.getAuthstatus().equals("Y")) {
			nameFile = CryptoFunctions.decrypt(nameFile, respAS.getKey1());
			dir = CryptoFunctions.decrypt(dir, respAS.getKey1());
			path = "/Users/WolfDen/Desktop/Testing/"+dir+nameFile;
			String fcontent="";
			try {
				fcontent = new String(Files.readAllBytes(Paths.get(path)));
				System.out.println(fcontent);
				fcontent = CryptoFunctions.encrypt(fcontent, respAS.getKey1());
				
				respRead.setAuthstatus(respAS.getAuthstatus());
				respRead.setFilecontent(fcontent);
			} catch (NoSuchFileException e) {
				fcontent = "";
				fcontent = CryptoFunctions.encrypt(fcontent, respAS.getKey1());
				
				respRead.setAuthstatus(respAS.getAuthstatus());
				respRead.setFilecontent(fcontent);
			}catch(IOException e) {
				respRead.setAuthstatus(respAS.getAuthstatus());
				e.printStackTrace();
			}
		}else {
			respRead.setAuthstatus(respAS.getAuthstatus());
		}
		String readResponseJson = respRead.getJsonString();
		System.out.println(readResponseJson);
		return readResponseJson;
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/writeFile")
	public String writeFile(String input) throws UnsupportedEncodingException {
		PropertyLoader.loadProperties();
		WriteRequest reqWrite = new WriteRequest();
		WriteResponse respWrite = new WriteResponse();
		reqWrite = reqWrite.getClassFromJsonString(input);
		String token = reqWrite.getToken();
		String encryptedUsername = reqWrite.getEncryptedUsername();
		String dir = reqWrite.getDirectory();
		String nameFile = reqWrite.getFilename();
		String contentFile = reqWrite.getFilecontent();
		
		RequestToAS reqAS = new RequestToAS();
		ResponseFromAS respAS = new ResponseFromAS();
		Sender sender = new Sender();
		String path = "";
		reqAS.setToken(token);
		reqAS.setEncryptedUsername(encryptedUsername);
		String authCheckRequest = reqAS.getJsonString();
		String authCheckResponse = sender.AuthCheck(authCheckRequest);
		respAS = respAS.getClassFromJsonString(authCheckResponse);
		if(respAS.getAuthstatus().equals("Y")) {
			
			nameFile = CryptoFunctions.decrypt(nameFile, respAS.getKey1());
			dir = CryptoFunctions.decrypt(dir, respAS.getKey1());
			contentFile = CryptoFunctions.decrypt(contentFile, respAS.getKey1());
		
			path = "/Users/WolfDen/Desktop/Testing/"+dir+nameFile;
			try {
				Files.write(Paths.get(path), contentFile.getBytes());
				respWrite.setAuthstatus(respAS.getAuthstatus());
			}catch(IOException e) {
				respWrite.setAuthstatus("N");
				e.printStackTrace();
			}
		}else {
			respWrite.setAuthstatus("N");
		}
		return respWrite.getJsonString();
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) {
		return "up and running";
	}

}