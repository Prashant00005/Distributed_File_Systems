package CS;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class WriteRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Sender sender; 
       
   
    public WriteRequest() {
        super();
        // TODO Auto-generated constructor stub
    }
	public void init(ServletConfig config) throws ServletException {
		sender = new Sender();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String usernameEnc = (String)request.getSession().getAttribute("usernamenc");
		String filename = request.getParameter("fn");
		
		RequestFileDS reqFileDS = new RequestFileDS();
		reqFileDS.setFilename(CryptoFunctions.encrypt(filename, key1));
		reqFileDS.setToken(token);
		reqFileDS.setEncryptedUsername(usernameEnc);
		String infoRequestJson = reqFileDS.getJsonString();
		String replyInfoRequest = sender.Rec_DirectoryServer_Info(infoRequestJson);
		ResponseFileDS resp_DS = new ResponseFileDS();
		resp_DS=resp_DS.getClassFromJsonString(replyInfoRequest);
		
		if(resp_DS.getAuthstatus().equals("Y"))
		
		{
			resp_DS.setServerurl(CryptoFunctions.decrypt(resp_DS.getServerurl(),key1));
			RequestToFS reqFS = new RequestToFS();
			reqFS.setFilename(CryptoFunctions.encrypt(filename, key1));
			reqFS.setToken(token);
			reqFS.setEncryptedUsername(usernameEnc);
			reqFS.setDirectory(resp_DS.getDirectory());//This is also encrypted with key1
			String jsonReadRequest = reqFS.getJsonString();
			String readResponsereply = sender.Read_Request(jsonReadRequest,resp_DS.getServerurl());
			ResponseFromFS resp_FS = new ResponseFromFS();
			resp_FS = resp_FS.getClassFromJsonString(readResponsereply);
			String filecontent = CryptoFunctions.decrypt(resp_FS.getFilecontent(),key1);
			RequestLockServer req_lock = new RequestLockServer();
			req_lock.setEmail(CryptoFunctions.encrypt("abc@abc.com", key1));
			req_lock.setFilename(CryptoFunctions.encrypt(filename, key1));
			req_lock.setToken(token);
			req_lock.setUsername(usernameEnc);
			String lockRequestStr = req_lock.getJsonString();
			String lockResponseStr = sender.LockRequest(lockRequestStr);
			ResponseLockServer resp_LS = new ResponseLockServer(); 
			resp_LS = resp_LS.getClassFromJsonString(lockResponseStr);
			if(resp_LS.getLockstatus()!=null && resp_LS.getLockstatus().equalsIgnoreCase("Y"))	{
				request.getSession().setAttribute("status", "1");
				request.getSession().setAttribute("filecontent", filecontent);
				request.getSession().setAttribute("filename", filename);
				System.out.println(filecontent);
				request.getRequestDispatcher("OpenFile.jsp").forward(request, response);
			}
			else if(resp_LS.getLockstatus()!=null && resp_LS.getLockstatus().equalsIgnoreCase("N")) {
				request.getSession().setAttribute("status", "0");
				if(resp_DS.getAuthstatus()==null)
					resp_DS.setAuthstatus("");
				request.getSession().setAttribute("message", "Unable to get lock on file, file is already locked");
				request.getRequestDispatcher("OpenFile.jsp").forward(request, response);
			} 
			else{
				request.getSession().setAttribute("status", "0");
				if(resp_DS.getAuthstatus()==null)
					resp_DS.setAuthstatus("");
				request.getSession().setAttribute("message", "Token Validation Failed");
				request.getRequestDispatcher("OpenFile.jsp").forward(request, response);
			}			
		}else {
			request.getSession().setAttribute("status", "0");
			if(resp_DS.getAuthstatus()==null)
				resp_DS.setAuthstatus("");
			request.getSession().setAttribute("message", resp_DS.getAuthstatus());
			request.getRequestDispatcher("OpenFile.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}