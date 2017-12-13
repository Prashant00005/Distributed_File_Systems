package CS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseFromAS {

	String name;
	String token;
	String authstatus;
	String usertype;
	String key1;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getAuthstatus() {
		return authstatus;
	}
	
	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	
	
	public String getUsertype() {
		return usertype;
	}
	
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public String getKey1() {
		return key1;
	}
	
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public ResponseFromAS getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		ResponseFromAS resp = gson.fromJson(replyInString, ResponseFromAS.class);
		return resp;
	}
}