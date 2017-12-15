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

		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String usernameEnc = (String)request.getSession().getAttribute("usernamenc");
		String filename = (String)request.getSession().getAttribute("filename");
		String directory = (String)request.getSession().getAttribute("directory");
		String filecontent = (String)request.getParameter("writebox");
		String serverurl = (String)request.getSession().getAttribute("serverurl");
		RequestWriteToFile ReqWrite = new RequestWriteToFile();
		ReqWrite.setDirectory(CryptoFunctions.encrypt(directory, key1));
		ReqWrite.setFilename(CryptoFunctions.encrypt(filename, key1));
		ReqWrite.setEncryptedUsername(usernameEnc);
		ReqWrite.setFilecontent(CryptoFunctions.encrypt(filecontent,key1));
		ReqWrite.setToken(token);
		String jsonWriteRequest = ReqWrite.getJsonString();
		String writeResponsereply = sender.WriteRequest(jsonWriteRequest,serverurl);
		ResponseWriteToFile respWrite = new ResponseWriteToFile();
		respWrite = respWrite.getClassFromJsonString(writeResponsereply);
		if(null!=respWrite.getAuthstatus() && respWrite.getAuthstatus().equals("Y")) {
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("message", "File Changes saved");
			response.getWriter().append("Served at: ").append(request.getContextPath());
			request.getRequestDispatcher("New.jsp").forward(request, response);
		}else if(null!=respWrite.getAuthstatus() && respWrite.getAuthstatus().equals("N")) {
			request.getSession().setAttribute("status", "0");
			request.getSession().setAttribute("message", "Token Validation Failed, please re-login and try again");
			request.getRequestDispatcher("New.jsp").forward(request, response);
		}else {
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("message", "ERROR");
			response.getWriter().append("Served at: ").append(request.getContextPath());
			request.getRequestDispatcher("New.jsp").forward(request, response);
		}
		 //Client sends request to release file after write is done
		RequestLSRelease reqRelease = new RequestLSRelease();
		reqRelease.setEmail(CryptoFunctions.encrypt("hello@gmail.com", key1));
		reqRelease.setFilename(CryptoFunctions.encrypt(filename, key1));
		reqRelease.setToken(token);
		reqRelease.setUsername(usernameEnc);
        String lockRequestStr = reqRelease.getJsonString();
        sender.sendUnLockRequest(lockRequestStr);
        
        String cache_filename = Caching.cacheFile.get(filename);
        if(cache_filename != null) {
        	
        	Caching.cacheFile.put(filename, filecontent);
        }

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}