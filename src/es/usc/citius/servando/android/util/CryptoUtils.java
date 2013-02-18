package es.usc.citius.servando.android.util;

import java.io.UnsupportedEncodingException;

public class CryptoUtils {

	/**
	 * 
	 * @param md5
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String MD5(String md5) throws UnsupportedEncodingException
	{
		try
		{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i)
			{
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e)
		{
		}
		return null;
	}

}
