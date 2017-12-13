package CS;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseLockServer {
	String authstatus;
	String lockstatus;
	
	public String getAuthstatus() {
		return authstatus;
	}
	
	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	
	public String getLockstatus() {
		return lockstatus;
	}
	
	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}

	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public ResponseLockServer getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		ResponseLockServer resp = gson.fromJson(replyInString, ResponseLockServer.class);
		return resp;
	}
}