package CS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RequestLSRelease {
	String filename;
	String username;
	String token;
	String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public RequestLSRelease getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		RequestLSRelease req = gson.fromJson(param,RequestLSRelease.class);
		return req;
		
	}
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String resp = gson.toJson(this);
		return resp;
	}
}