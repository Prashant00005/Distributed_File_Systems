package DS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;



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

	
	public String AuthCheck(String param) {
		String url = PropertyLoader.Url_Server+PropertyLoader.UrlRequest;
		String type = "AuthCheck";
		return connection(url,param,type);

	}
	public HashMap<String, String> LocationFileRequestRead(String nameFile) {
		HashMap<String, String> infoFile = new HashMap<String, String>();
		ResultSet rs;
		try {
			Connection conn = FileServerDao.sqlconnect();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select server,directory from dirservice.filelist where filename like '" + nameFile +"';");
			if(rs.next())
			{
				infoFile.put("serverurl", rs.getString(1));
				infoFile.put("directory", rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infoFile;
	}

	
	public HashMap<String, String> LocationFileRequestWrite(String nameFile) {
		HashMap<String, String> infoFile = new HashMap<String, String>();
		ResultSet rs;
		try {
			Connection conn = FileServerDao.sqlconnect();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select server,directory from dirservice.filelist where filename like '" + nameFile +"';");
			if(rs.next())
			{
				System.out.println("\n\n########################\nServer:: "+ rs.getString(1)+ "Dir:: "+ rs.getString(2));
				infoFile.put("serverurl", rs.getString(1));
				infoFile.put("directory", rs.getString(2));
			}else {
				String query = " insert into dirservice.filelist values (?,?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString (1, nameFile);
				preparedStmt.setString (2, "http://127.0.0.1:8083/"); //Ip of fileserver System
				preparedStmt.setString (3, "N");
				preparedStmt.setString (4, "");
				preparedStmt.setString (5, "Prashant/");
				preparedStmt.execute();
				infoFile.put("serverurl","http://127.0.0.1:8083/");
				infoFile.put("directory", "Prashant/");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infoFile;

	}

	
	public HashMap<String, String> AllFileList() {
		HashMap<String, String> infoFile = new HashMap<String, String>();
		ResultSet rs;
		String fileList="";
		String dirList="";
		try {
			Connection conn = FileServerDao.sqlconnect();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select filename,directory from dirservice.filelist;");
			while(rs.next())
			{
				fileList=rs.getString(1)+","+fileList;
				dirList=rs.getString(2)+","+dirList;
			}
			infoFile.put("filename", fileList);
			infoFile.put("directory", dirList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infoFile;
	}


}
