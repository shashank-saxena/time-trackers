package com.newput.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;

/**
 * 
 * @author Newput
 * Description : Use to create and parse the json object
 */

@Service
public class JsonResService {

	@Autowired
	private Employee emp;
	
	private String success;
	private String rcode;
	private String error;
	private JSONObject data;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	/**
	 * Description : Create a Json to send into the response of UI
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createRegistrationJson() {
		JSONObject obj = new JSONObject();
		obj.put("firstName", "rahul");
		obj.put("lastName", "kulmi");
		obj.put("email", "deepak@newput.com");
		obj.put("dob", new Date());
		obj.put("doj", new Date());
		obj.put("address", "indore");
		obj.put("contact", "9907614646");
		obj.put("gender", "m");
		obj.put("password", "xyz");
		obj.put("status", true);
		obj.put("pwd_verify", true);
		obj.put("role", "employee");
		obj.put("created", new Long(10092015));
		obj.put("updated", new Long(10102015));
		obj.put("time_zone", new BigDecimal(5.5));
		return obj;
	}

	

	/**
	 * Description : To parse the Json object and set into the employee bean
	 * @param obj_new
	 * @throws ParseException
	 */
	public void setRegistrationJson(JSONObject obj_new) throws ParseException {
		// JSONParser jsonParser = new JSONParser();
		// JSONObject obj_new = (JSONObject)
		// jsonParser.parse(obj.toJSONString()) ;
		emp.setFirstName(obj_new.get("firstName").toString());
		emp.setLastName(obj_new.get("lastName").toString());
		emp.setEmail(obj_new.get("email").toString());
		emp.setDob((Date) obj_new.get("dob"));
		emp.setDoj((Date) obj_new.get("doj"));
		emp.setAddress(obj_new.get("address").toString());
		emp.setContact(obj_new.get("contact").toString());
		emp.setGender(obj_new.get("gender").toString());
		emp.setPassword(obj_new.get("password").toString());
		emp.setStatus((Boolean) obj_new.get("status"));
		emp.setPasswordVerification((Boolean) obj_new.get("pwd_verify"));
		emp.setRole(obj_new.get("role").toString());
		emp.setCreated((Long) obj_new.get("created"));
		emp.setUpdated((Long) obj_new.get("updated"));
		emp.setTimeZone((BigDecimal) obj_new.get("time_zone"));
	}
	

	@SuppressWarnings("unchecked")
	public JSONObject responseSender(){
		JSONObject obj = new JSONObject();
		obj.put("response", getMap());
		return obj;
	}
	
	public HashMap<String, Object> getMap(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", getSuccess());
		map.put("data", getData());
		map.put("rcode", getRcode());
		map.put("error", getError());
		return map;
	}

	
}
