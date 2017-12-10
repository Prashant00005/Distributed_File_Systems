package DFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TokenRequest {
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
	public TokenRequest getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		TokenRequest tokenRequest = gson.fromJson(param,TokenRequest.class);
		return tokenRequest;
		
	}

}
