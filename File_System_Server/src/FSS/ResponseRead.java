package FSS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseRead {

	public String filecontent;
	public String authstatus;
	
	public String getFilecontent() {
		return filecontent;
	}
	
	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}
	
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
	
	public ResponseRead getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		ResponseRead resp = gson.fromJson(replyInString, ResponseRead.class);
		return resp;
	}
}