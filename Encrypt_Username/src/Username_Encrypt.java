import java.io.UnsupportedEncodingException;
import java.security.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jersey.core.util.Base64;



public class Username_Encrypt {
	
	
	public static String encrypt(String strClearText,String strKey) {
		String strData="";

		try {
			
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted=cipher.doFinal(strClearText.getBytes("Cp1252"));
			strData=new String(Base64.encode(encrypted));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strData;
	}
	
	public static String decrypt(String strEncrypted,String strKey) throws UnsupportedEncodingException {
		String strData="";

		try {
			byte[] encByte= strEncrypted.getBytes("Cp1252");
			byte[] bytEncrypted = Base64.decode(encByte);
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted=cipher.doFinal(bytEncrypted);
			strData=new String(decrypted);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return strData;
	}
	
	
	
	public static void main(String args[]) {
	
	try{	
		
		
		String user = encrypt("Smarth.Katyal","Myname123");
		//String decrypt = decrypt(user,"Myname123");
		//System.out.println("decrypted value is"+user);
		//System.exit(0);
		Class.forName("com.mysql.jdbc.Driver");  
	    	Connection con=DriverManager.getConnection(  
	    	"jdbc:mysql://localhost:3306/fileserver","root","Prashant123!");  
	    	
	    	 
	        String query = " insert into users values ('Smarth.Katyal','Myname123','Tau','N','1',?)";
	        PreparedStatement preparedStmt = con.prepareStatement(query);
	        preparedStmt.setString (1, user);
	        preparedStmt.execute();
	        
	        con.close();
	    
	}    
        catch (SQLException e)
        {
          System.err.println("Got an exception!");
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
	

}
