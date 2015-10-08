package com.newput.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;

/**
 * {@link}
 * 
 * @author Newput Description- Method use to send email to the registred mail id
 *         for the verification in this method mailSender variable is @Autowired
 *         with bean defined in the applicationContext.xml
 */
@Service
public class VerificationMailSend {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Employee emp;

	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STRING_LENGTH = 8;

	public void sendMail() {

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(emp.getEmail());
		email.setSubject("Confirmation Mail");
		email.setText("Welcome, You are successfully register Please click here http://tracker/login?a="
				+ emp.getvToken());
		mailSender.send(email);
	}

	/**
	 * Description : use to create a AlphaNumeric token for the verification of
	 * email
	 * 
	 * @return it return a random generated string e.g.k3gctKho,7lKfGnIV
	 */
	public String generateRandomString() {
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	/**
	 * Description- method call internally to add numeric value in verification token
	 * @return
	 */
	private int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}
}
