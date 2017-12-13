package CS;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseFromFS {

	String filecontent;

	public String getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}
	
	public ResponseFromFS getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		ResponseFromFS response = gson.fromJson(replyInString, ResponseFromFS.class);
		return response;
	}
}