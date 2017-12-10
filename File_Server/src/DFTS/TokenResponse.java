package DFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TokenResponse {
	String authstatus;
	String key1;

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String tokenResponse = gson.toJson(this);
		return tokenResponse;
	}

}
