package DS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class RequestToDS {

	String token;
	String filename;
	String encryptedUsername;
	String operation;
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getEncryptedUsername() {
		return encryptedUsername;
	}
	
	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}
	
	public RequestToDS getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		RequestToDS req = gson.fromJson(replyInString, RequestToDS.class);
		return req;
	}

}