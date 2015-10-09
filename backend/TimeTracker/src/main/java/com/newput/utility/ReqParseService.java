package com.newput.utility;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

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

	public HashMap<String, String> reqParser(String req) {
		HashMap<String, String> request = new HashMap<>();
		StringTokenizer st = new StringTokenizer(req, "&");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			int i = token.indexOf("=");
			String key = token.substring(0, i);
			String value = token.substring(i + 1);
			request.put(key, value);
		}
		return request;
	}

	public void setEmployeeValue(HashMap<String, String> empValue, String token) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

		try {
			
			long systime = new Date().getTime()/1000;
			Boolean status =false;
			emp.setAddress(empValue.get("address"));
			emp.setContact(empValue.get("contact"));
			emp.setCreated(systime);
			String dob = empValue.get("dob");
			Date userDob = sdf.parse(dob);
			emp.setDob(userDob);
			String doj = empValue.get("doj");
			Date userDoj = sdf.parse(doj);
			emp.setDoj(userDoj);
			emp.setEmail(empValue.get("email"));
			emp.setFirstName(empValue.get("firstName"));
			emp.setGender(empValue.get("gender"));
			emp.setLastName(empValue.get("lastName"));
			String getPassword = util.md5(empValue.get("password"));
			emp.setPassword(getPassword);// add encryption
			emp.setvToken(token);			
			emp.setUpdated(systime);	
			emp.setRole("guest");
			emp.setTimeZone(new BigDecimal("5.5"));
			emp.setStatus(status);
			emp.setPasswordVerification(status);
			
		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	public void setVerificationKey(boolean p_status,String pToken,String role){
		long systime = new Date().getTime()/1000;
		emp.setPasswordVerification(p_status);
		emp.setpExpireAt(systime+86400);
		emp.setpToken(pToken);
		emp.setRole(role);
	}

	public void setSessionValue(HashMap<String, String> empValue, int emp_id, String emp_name, String token) {
		try {
			long systime = new Date().getTime()/1000;
			session.setCreated(systime);
			session.setEmpId(emp_id);
			session.setEmpName(emp_name);
			session.setExpiresWhen(systime + 3600);
			session.setToken(token);
			session.setUpdated(systime);

		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void setTimeSheetValue(HashMap<String, String> empValue, int emp_id) {
		try {
			long systime = new Date().getTime()/1000;
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
			long systime = new Date().getTime()/1000;
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
