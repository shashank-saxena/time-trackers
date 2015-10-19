package com.newput.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;

/**
 * {@link}
 * 
 * @author Newput Description : Method use to send email to the registered mail
 *         id for the verification in this method mailSender variable
 *         is @Autowired with bean defined in the applicationContext.xml
 */
@Service
public class VerificationMailSend {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Employee emp;

	public void sendMail(String module) {

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(emp.getEmail());
		email.setSubject("Confirmation Mail");
		if (module.equalsIgnoreCase("registration")) {
			email.setText("Welcome, You are successfully register Please click here http://tracker/login?ET="
					+ emp.getvToken());

		} else if (module.equalsIgnoreCase("resetPassword")) {
			email.setText("Welcome, Please confirm your mail id. click here http://tracker/login?PT=" + emp.getpToken()
					+ "&id=" + emp.getId());
		}
		mailSender.send(email);
	}

}
