package DFTS;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jersey.core.util.Base64;



public class CryptoFunctions {

	public static String encrypt(String strClearText,String strKey) {
		String strData="";

		try {
			
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted=cipher.doFinal(strClearText.getBytes());
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
		}
		return strData;
	}

	public static String decrypt(String strEncrypted,String strKey) throws UnsupportedEncodingException {
		String strData="";
System.out.println(strEncrypted);
System.out.println(strKey);
		try {
			byte[] encByte= strEncrypted.getBytes();
			strEncrypted = new String(Base64.decode(encByte));
			System.out.println(strEncrypted.length());
			byte[] mew = strEncrypted.getBytes();
			
			System.out.println(mew.length);
			System.out.println(mew);
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("UTF-8"),"Blowfish");
			System.out.println("hello");
			Cipher cipher=Cipher.getInstance("Blowfish");
			System.out.println("hello");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			System.out.println("hello");
			byte[] decrypted=cipher.doFinal(strEncrypted.getBytes());
			System.out.println("hello");
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
	public static Key getNewKey()  {
		Key symKey=null;
		try {
			symKey = KeyGenerator.getInstance("Blowfish").generateKey();
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return symKey;
	}
}