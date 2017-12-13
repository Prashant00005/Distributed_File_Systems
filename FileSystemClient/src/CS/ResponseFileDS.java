package CS;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseFileDS {

	public String serverurl;
	public String directory;
	public String token;
	public String authstatus;
	
	public String getServerurl() {
		return serverurl;
	}
	
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}
	
	public String getDirectory() {
		return directory;
	}
	
	public void setDirectory(String directory) {
		this.directory = directory;
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
	
	public ResponseFileDS getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		ResponseFileDS resp = gson.fromJson(replyInString, ResponseFileDS.class);
		return resp;
	}
	 
}