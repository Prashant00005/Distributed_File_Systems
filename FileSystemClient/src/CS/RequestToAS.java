package CS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RequestToAS {

	String username;
	String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String str = gson.toJson(this);
		return str;
	}
	
}