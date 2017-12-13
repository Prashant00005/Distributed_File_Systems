package CS;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Sender sender;
       
   
    public LoginRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	public void init(ServletConfig config) throws ServletException {
		sender = new Sender();
		PropertyLoader.loadProperties();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		RequestToAS request_to_AS= new RequestToAS();
	
		request_to_AS.setUsername(CryptoFunctions.encrypt((String) request.getParameter("u"), (String) request.getParameter("p")));
		request_to_AS.setPassword(CryptoFunctions.encrypt((String) request.getParameter("p"), (String) request.getParameter("p")));
		
		String reply = sender.Login_Request(request_to_AS.getJsonString());
		
		ResponseFromAS resp_from_AS = new ResponseFromAS();
		resp_from_AS = resp_from_AS.getClassFromJsonString(reply);
		
		if(resp_from_AS.getAuthstatus().equals("Y")&&resp_from_AS.getUsertype().equals("N")) 
		
		{
			request.getSession().setAttribute("fname", resp_from_AS.getName());
			request.getSession().setAttribute("token", resp_from_AS.getToken());
			request.getSession().setAttribute("key1", resp_from_AS.getKey1());
			request.getSession().setAttribute("usernamenc", request_to_AS.getUsername());
			request.getRequestDispatcher("New.jsp").forward(request, response);
		}
		
		else
		{
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}