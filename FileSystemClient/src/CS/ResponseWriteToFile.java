package CS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseWriteToFile {

	String authstatus;
	String filestatus;
	
	public String getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}

	public String getFilestatus() {
		return filestatus;
	}

	public void setFilestatus(String filestatus) {
		this.filestatus = filestatus;
	}


	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public ResponseWriteToFile getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		ResponseWriteToFile resp = gson.fromJson(replyInString, ResponseWriteToFile.class);
		return resp;
	}
}