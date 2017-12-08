package DFTS;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/dfts")
public class Login_Server {
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayhello() {
		
		String response = "<?xml version= '1.0'?>"+
				"<hello> Hello XML </hello>";
		return response;
	}

}
