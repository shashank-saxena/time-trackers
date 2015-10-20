package com.newput.utility;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.DateSheet;
import com.newput.domain.Employee;
import com.newput.domain.Session;
import com.newput.domain.TimeSheet;

@Service
public class ReqParseService {

	@Autowired
	private Employee emp;

	@Autowired
	private Session session;

	@Autowired
	private TimeSheet timeSheet;

	@Autowired
	private DateSheet dateSheet;

	@Autowired
	private TTUtil util;

	public Long getCurrentTime() {
		return System.currentTimeMillis() / 1000;
	}

	public void setEmployeeValue(String firstName, String lastName, String email, String dob, String doj,
			String address, String contact, String gender, String password, String token) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			firstName = util.getAlphaNum(firstName);
			lastName = util.getAlphaNum(lastName);
			contact = util.getIntNum(contact);
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			emp.setEmail(email);

			Date userDob = sdf.parse(dob);
			Date userDoj = sdf.parse(doj);

			emp.setDob(userDob);
			emp.setDoj(userDoj);
			emp.setAddress(address);
			contact = util.getIntNum(contact);
			emp.setContact(contact);
			emp.setGender(gender);

			String getPassword = util.md5(password);
			emp.setPassword(getPassword);// add encryption

			emp.setStatus(false);
			emp.setPasswordVerification(false);
			emp.setRole("guest");
			emp.setCreated(getCurrentTime());
			emp.setTimeZone(new BigDecimal("5.5"));
			emp.setvToken(token);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void setSessionValue(String email, String password, String token) {
		try {
			emp.setEmail(email);
			emp.setPassword(password);
			session.setToken(token);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void setValidationValue(String email, String token) {
		emp.setEmail(email);
		emp.setvToken(token);
		emp.setUpdated(getCurrentTime());
	}

	public void setPValidationValue(int emp_id, String token, String password) {
		emp.setPassword(password);
		emp.setId(emp_id);
		emp.setpToken(token);
		emp.setUpdated(getCurrentTime());
	}

	public TimeSheet setTimeSheetValue(String workDate, String in, String out, String chunkId, int emp_id) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date wrkdate = sdf.parse(workDate);
			long systime = wrkdate.getTime();
			timeSheet.setCreated(getCurrentTime());
			timeSheet.setWorkDate(systime);
			System.out.println("emp_id::" + emp_id);
			timeSheet.setEmpId(emp_id);
			timeSheet.setChunkId(Integer.parseInt(chunkId));
			if (in != null && !in.equalsIgnoreCase("")) {
				timeSheet.setTimeIn(util.timeMiliSec(workDate, in));
			}
			timeSheet.setTimeOut(util.timeMiliSec(workDate, out));
		} catch (Exception e) {
			e.getMessage();
		}
		return timeSheet;
	}

	public void setDateSheetValue(String desc, String workdate, int emp_id) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date workDate = sdf.parse(workdate);
			long systime = workDate.getTime();
			dateSheet.setCreated(getCurrentTime());
			dateSheet.setEmpId(emp_id);
			dateSheet.setWorkDate(systime);
			dateSheet.setWorkDesc(desc);
		} catch (Exception e) {
			e.getMessage();
		}

	}

}
