package FSS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteResponse {

	String authstatus;

	public String getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	
	
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public WriteResponse getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		WriteResponse resp = gson.fromJson(replyInString, WriteResponse.class);
		return resp;
	}
}