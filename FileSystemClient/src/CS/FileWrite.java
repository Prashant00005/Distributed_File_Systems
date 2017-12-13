package CS;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FileWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Sender sender; 
       
    
    public FileWrite() {
        super();
        // TODO Auto-generated constructor stub
    }
	public void init(ServletConfig config) throws ServletException {
		sender = new Sender();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		
		RequestWriteToFile writeRequest = new RequestWriteToFile();
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("OpenFile.jsp").forward(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}