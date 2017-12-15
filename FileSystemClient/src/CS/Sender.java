package CS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;



public class Sender {

	public String connection(String url, String input,String type) {
		
		String output="";
		String reply="";
		
		try 
		
		{
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			
			System.out.println("*******\nSending "+type+" Request:\n"+input+"\n*********");
			System.out.println("*******\nSending "+type+" Request to "+url+"\n**********");
			
			post.addHeader("accept", "application/json");
			
			StringEntity se = new StringEntity(input.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			while ((output = bufferedReader.readLine()) != null) {
				reply+=output;
			}
			System.out.println("*******\nGot  "+type+" Response:\n"+reply+"\n*********");

		}
		catch(MalformedURLException e) 
		{
			e.printStackTrace();

		}
		catch(IOException e) 
		
		{
			e.printStackTrace();
		}
		
		return reply;
	}

	
	public String Login_Request(String param) {
		String url = PropertyLoader.Url_Server+PropertyLoader.Url_loginRequest;
		String type = "Login";
		return connection(url,param,type);

	}
	
	public String Read_Request(String param, String ip) {
		String url = ip+ PropertyLoader.Url_FileRead;
		String type = "ReadFile";
		return connection(url,param,type);
	}
	
	public String Rec_DirectoryServer_Info(String param) {
		String url = PropertyLoader.Url_DirectoryServer+PropertyLoader.Url_FileInfo;
		String type = "GetDirectoryInfo";
		return connection(url,param,type);
	}

	public String LockRequest(String input) {
		String url = PropertyLoader.Url_LockServer+PropertyLoader.Url_GetLock;
		String type = "GetLock";
		return connection(url,input,type);
	}
	public String WriteRequest(String input, String serverurl) {
		String url = serverurl+ PropertyLoader.Url_File_Write;
		String type = "WriteFile";
		return connection(url,input,type);
	}
	public String sendUnLockRequest(String input) {
		String url = PropertyLoader.Url_LockServer+PropertyLoader.Url_Realease_Lock;
		String type = "ReleaseLock";
		return connection(url,input,type);
	}
	

}
