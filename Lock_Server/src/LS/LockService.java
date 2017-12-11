package LS;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import LS.FileServerDao;

@Path("/ls")
public class LockService {
	@POST
	@Consumes({"application/json"})
	@Path("/getLock")
	public String lock(String param1){
		PropertyLoader.loadProperties();
		String reply = null;
		System.out.println("param1 = " + param1);

		LockRequest lr = new LockRequest();
		lr = lr.getClassFromJson(param1);

		String client_filename = lr.getFilename();
		String client_email = lr.getEmail();

		String client_username = lr.getUsername();
		
		
		
		
		String client_token = lr.getToken();
		
		LockResponse lresponse = new LockResponse();
		lresponse.setToken(client_token);
		lresponse.setEncryptedUsername(client_username);
		String jsonstr = lresponse.getJsonString();
		Sender obj = new Sender();
		
		String resp = obj.sendAuthRequest(jsonstr);
		ResponseFromAS as = new ResponseFromAS();
		as=as.getClassFromJson(resp);
		
		String key1 = as.getKey1();
		try {
		
		
		String auth = as.getAuthstatus();
		Connection conn = FileServerDao.sqlconnect();
		ResponseToCLient rtc = new ResponseToCLient();
		
		//To process lock request
		if (auth.equals("Y"))
		{
			String decrypt_client_filename = CryptoFunctions.decrypt(client_filename, key1);
			String decrypt_client_email = CryptoFunctions.decrypt(client_email, key1);
			
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select locked from lookup where filename = '" + decrypt_client_filename +"';");
			if(rs.next())
			{
				if(rs.getString(1).equals("Y"))
				{
					rtc.setLockstatus("N");
					
					reply= rtc.getJsonString();
					
				}
				else 
				{
					String query = "update lookup set locked = 'Y' where filename = '" + decrypt_client_filename +"';  ";
			        PreparedStatement preparedStmt = conn.prepareStatement(query);
			        
			        preparedStmt.execute();
			        
			        rtc.setLockstatus("Y");
			        reply=  rtc.getJsonString();
				}
				
			}
			else
			{
				String query = " insert into lookup values (?,?,?,'Y')";
		        PreparedStatement preparedStmt = conn.prepareStatement(query);
		        preparedStmt.setString (1, client_username);
		        preparedStmt.setString (2, decrypt_client_filename);
		        preparedStmt.setString (3, decrypt_client_email);
		        preparedStmt.execute();
		        rtc.setLockstatus("Y");
		        reply=  rtc.getJsonString();
				
			}
			
			
		}
		else
		{	
			
			rtc.setAuthstatus("N");
			reply=  rtc.getJsonString();
		}
		}catch(SQLException sq) {
			System.out.println("Exceptin in LockService Select Lookup");
			sq.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reply;
			
	}
	
	
		@POST
		@Consumes({"application/json"})
		@Path("/releaseLock")
		public String release(String param1){
			
			PropertyLoader.loadProperties();
			String reply = null;
			
			LockRequest lr = new LockRequest();
			lr = lr.getClassFromJson(param1);

			String client_filename = lr.getFilename();
			String client_email = lr.getEmail();

			String client_username = lr.getUsername();
			String client_token = lr.getToken();
			
			LockResponse lresponse = new LockResponse();
			lresponse.setToken(client_token);
			lresponse.setEncryptedUsername(client_username);
			String jsonstr = lresponse.getJsonString();
			Sender obj = new Sender();
			
			String resp = obj.sendAuthRequest(jsonstr);
			ResponseFromAS as = new ResponseFromAS();
			as=as.getClassFromJson(resp);
			
			String key1 = as.getKey1();
			
			try {
				
				
				String auth = as.getAuthstatus();
				Connection conn = FileServerDao.sqlconnect();
				ResponseToCLient rtc = new ResponseToCLient();
				if (auth.equals("Y"))
				{
					String decrypt_client_filename = CryptoFunctions.decrypt(client_filename, key1);
					Statement stmt=conn.createStatement();
					
					ResultSet rs=stmt.executeQuery("select locked from lookup where filename = '" + decrypt_client_filename +"';");
					if(rs.next())
					{
						String query = "update lookup set locked = 'N' where filename = '" + decrypt_client_filename +"';  ";
				        PreparedStatement preparedStmt = conn.prepareStatement(query);
				        
				        preparedStmt.execute();
				        
				        rtc.setReleaseStatus("0");
				        reply=  rtc.getJsonString();
					}
					else
					{
						rtc.setReleaseStatus("0");
				        reply=  rtc.getJsonString();
					}
					
					
				}
				else
				{	
					
					rtc.setAuthstatus("N");
					reply=  rtc.getJsonString();
				}
					
				
				
			}
			catch(SQLException sq) {
				
				System.out.println("Exception in release lock in LockService"+sq);
				sq.printStackTrace();
				ResponseToCLient rtc = new ResponseToCLient();
				rtc.setReleaseStatus("1");
		        reply=  rtc.getJsonString();
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return reply;
		}
	
	
}
