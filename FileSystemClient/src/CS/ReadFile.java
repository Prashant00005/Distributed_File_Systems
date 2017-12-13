package CS;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ReadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Sender sender; 
   
    public ReadFile() {
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
		String filename = request.getParameter("fn");
		
		
		RequestFileDS req = new RequestFileDS();
		
		req.setFilename(CryptoFunctions.encrypt(filename, key1));
		req.setToken(token);
		req.setEncryptedUsername(usernameEnc);
		
		String reqJson = req.getJsonString();
		String replyInfoRequest = sender.Rec_DirectoryServer_Info(reqJson);
		
		ResponseFileDS resp_fileDS = new ResponseFileDS();
		resp_fileDS=resp_fileDS.getClassFromJsonString(replyInfoRequest);
		
		if(resp_fileDS.getAuthstatus().equals("Y"))
		
		{
			resp_fileDS.setServerurl(CryptoFunctions.decrypt(resp_fileDS.getServerurl(),key1));
			RequestToFS req_ToFS = new RequestToFS();
			req_ToFS.setFilename(CryptoFunctions.encrypt(filename, key1));
			req_ToFS.setToken(token);
			req_ToFS.setEncryptedUsername(usernameEnc);
			req_ToFS.setDirectory(resp_fileDS.getDirectory());//This is also encrypted with key1
			String jsonReadRequest = req_ToFS.getJsonString();
			String readResponsereply = sender.Read_Request(jsonReadRequest,resp_fileDS.getServerurl());
			ResponseFromFS respFS = new ResponseFromFS();
			respFS = respFS.getClassFromJsonString(readResponsereply);
			String filecontent = CryptoFunctions.decrypt(respFS.getFilecontent(),key1);
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("filecontent", filecontent);
			request.getSession().setAttribute("filename", filename);
			request.getRequestDispatcher("read.jsp").forward(request, response);
		}
		else 
		{
			request.getSession().setAttribute("status", "0");
			if(resp_fileDS.getAuthstatus()==null)
			{
				resp_fileDS.setAuthstatus("");
			}
			request.getSession().setAttribute("message", resp_fileDS.getAuthstatus());
			request.getRequestDispatcher("read.jsp").forward(request, response);
		}
		

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}