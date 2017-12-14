package DS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseFromClient {

	String filenamearr;
	String directoryarr;
	
	public String getFilenamearr() {
		return filenamearr;
	}
	
	public void setFilenamearr(String filenamearr) {
		this.filenamearr = filenamearr;
	}
	
	public String getDirectoryarr() {
		return directoryarr;
	}
	
	public void setDirectoryarr(String directoryarr) {
		this.directoryarr = directoryarr;
	}

	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
}