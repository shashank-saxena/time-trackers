package com.newput.utility;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		try { // yyyy-mm-dd
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			emp.setEmail(email);

			Date userDob = sdf.parse(dob);
			Date userDoj = sdf.parse(doj);

			System.out.println("dob is : " + userDob);
			System.out.println("doj is : " + userDoj);

			emp.setDob(userDob);
			emp.setDoj(userDoj);
			emp.setAddress(address);
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

	public void setVerificationKey(boolean p_status, String pToken, String role) {
		long systime = new Date().getTime() / 1000;
		emp.setPasswordVerification(p_status);
		emp.setpExpireAt(systime + 86400);
		emp.setpToken(pToken);
		emp.setRole(role);
	}

	public void setSessionValue(HashMap<String, String> empValue) {
		try {
			session.setToken(empValue.get("token"));
			session.setEmpId(Integer.parseInt(empValue.get("emp_id")));
			session.setEmpName(empValue.get("emp_name"));

			// long systime = new Date().getTime()/1000;
			// session.setCreated(systime);
			// session.setExpiresWhen(systime + 3600);
			// session.setUpdated(systime);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void setValidationValue(String email, String token) {
		emp.setEmail(email);
		emp.setvToken(token);
		emp.setUpdated(getCurrentTime());
	}

	public void setTimeSheetValue(HashMap<String, String> empValue, int emp_id) {
		try {
			long systime = getCurrentTime();
			timeSheet.setCreated(systime);
			timeSheet.setEmpId(emp_id);
			timeSheet.setChunkId(Integer.parseInt(empValue.get("chunk_id")));
			timeSheet.setTimeIn(Long.parseLong(empValue.get("timeIn")));
			timeSheet.setTimeOut(Long.parseLong(empValue.get("timeout")));
			timeSheet.setWorkDate(Long.parseLong(empValue.get("workDate")));
			timeSheet.setUpdated(systime);

		} catch (Exception e) {
			e.getMessage();
		}

	}

	public void setDateSheetValue(HashMap<String, String> empValue, int emp_id) {
		try {
			long systime = new Date().getTime() / 1000;
			dateSheet.setCreated(systime);
			dateSheet.setEmpId(emp_id);
			dateSheet.setWorkDate(Long.parseLong(empValue.get("workDate")));
			dateSheet.setWorkDesc(empValue.get("workDesc"));
			dateSheet.setUpdated(systime);

		} catch (Exception e) {
			e.getMessage();
		}

	}
}
