package com.newput.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.openjpa.lib.util.Base16Encoder;
import org.springframework.stereotype.Service;

@Service
public class TTUtil {


    public String createSessionKey(Long id,String email) {
    	try {
			return Base16Encoder.encode(MessageDigest.getInstance(
					"MD5").digest(
					(email+"-"+System.currentTimeMillis() + id).getBytes()));
		} catch (NoSuchAlgorithmException e) {  
			return e.getMessage();
//			LOG.debug(e.getMessage());
//			throw new AppException(CommonConstants.Error.FAILED);
		}
		 
    }
	
	public String md5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}
		messageDigest.reset();
		messageDigest.update(str.getBytes());
		return new String(Hex.encodeHex(messageDigest.digest()));
	}

}
