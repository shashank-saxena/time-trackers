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

	public String createSessionKey(Long id, String email) {
		try {
			return Base16Encoder.encode(MessageDigest.getInstance("MD5")
					.digest((email + "-" + System.currentTimeMillis() + id).getBytes()));
		} catch (NoSuchAlgorithmException e) {
			return e.getMessage();
			// LOG.debug(e.getMessage());
			// throw new AppException(CommonConstants.Error.FAILED);
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

	public static String getAlphaNum(String s) {
		if (s == null) {
			return "-";
		} else {
			return s.replaceAll("[^A-Za-z0-9]+", "-");
		}
	}

	public boolean mailFormat(String email) {
		Boolean valid = false;
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		valid = email.matches(EMAIL_REGEX);
		return valid;
	}

	public String getIntNum(String contact) {
		if (contact.startsWith("+"))
			contact = contact.substring(1);
		if (contact.matches("\\d{12}") && contact.startsWith("91")) {
			contact = contact.substring(2);
		}
		if (contact.matches("\\d{11}") && contact.startsWith("0")) {
			contact = contact.substring(1);
		}
		if (contact.matches("\\d{10}")) {
			return contact;
		} else {
			return "invalid number";
		}
	}
}
