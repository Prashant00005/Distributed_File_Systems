package DS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RequestToAS {

	String token;
	String encryptedUsername;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getEncryptedUsername() {
		return encryptedUsername;
	}
	
	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String req = gson.toJson(this);
		return req;
	}
}