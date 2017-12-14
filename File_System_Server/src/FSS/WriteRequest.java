package FSS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteRequest {
	
	String filename;
	String token;
	String encryptedUsername;
	String directory;
	String filecontent;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

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

	public String getDirectory() {
		return directory;
	}


	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}

	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String req = gson.toJson(this);
		return req;
	}
	
	public WriteRequest getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		WriteRequest resp = gson.fromJson(replyInString, WriteRequest.class);
		return resp;
	}
}