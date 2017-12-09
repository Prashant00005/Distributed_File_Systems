package DFTS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.catalina.connector.Response;
import javax.ws.*;



//@Path("/message")
//public class Login_Server {
//	
////	@GET
////	@Produces(MediaType.APPLICATION_JSON)
////	public String sayhello() {
////		
//////		String response = "<?xml version= '1.0'?>"+
//////				"<hello> Hello XML </hello>";
////		return "{'authstatus': 'Y','name': 'Smarth','token': '1'}";
////	}
//	
//	 @POST
//	    @Consumes({MediaType.APPLICATION_JSON})
//	    @Produces({MediaType.TEXT_PLAIN})
//	    @Path("/post")
//	    public String postMessage(Message msg) throws Exception{
//	        
//	        System.out.println("First Name = "+msg.getFirstName());
//	        System.out.println("Last Name  = "+msg.getLastName());
//	        
//	        return "ok";
//	    }
//
//}


@Path("/dfts")
public class Login_Server {
	@POST
	@Consumes({"application/json"})
	@Path("/signIn")
	public String create(String param1){

		System.out.println("param1 = " + param1);

		LoginRequest lr = new LoginRequest();
		lr = lr.getClassFromJson(param1);

		String client_username = lr.getUsername();

		String client_password = lr.getPassword();


		LoginResponse lresponse = new LoginResponse();

		try

		{

			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/fileserver","root","Prashant123!");  

			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select fname,usertype,token,pswd from users where encrypt_username = '" + client_username +"';");

			if(rs.next())
			{


				if(rs.getString(4).equals(CryptoFunctions.decrypt(client_password, rs.getString(4))))
				{
					lresponse.setAuthstatus("Y");
					lresponse.setName(rs.getString(1));
					lresponse.setUsertype(rs.getString(2));
					lresponse.setToken(rs.getString(3));
					return lresponse.getJsonString();
				}


			}


			lresponse.setAuthstatus("N");


			con.close();  
		}

		catch(Exception e)
		{

			System.out.println("Exception in main"+e);

		}  

		return lresponse.getJsonString();
	}

	@POST
	@Consumes({"application/json"})
	@Path("/fileRead")
	public void file_read(String param1) throws IOException {
		System.out.println("Execution of File read start here!");

		LoginRequest lr = new LoginRequest();
		lr = lr.getClassFromJson(param1);
		String filename = lr.getFilename();
		System.out.println("filename entered is"+filename);
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {

			//stream.forEach(System.out::println);
			stream.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@POST
	@Consumes({"application/json"})
	@Path("/fileWrite")
	public void file_write() {








	}






}







