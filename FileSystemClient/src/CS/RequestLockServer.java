package CS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RequestLockServer {

	public String filename;
	public String username;
	public String token;
	public String email;
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public RequestLockServer getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		RequestLockServer req = gson.fromJson(replyInString, RequestLockServer.class);
		return req;
	}
}