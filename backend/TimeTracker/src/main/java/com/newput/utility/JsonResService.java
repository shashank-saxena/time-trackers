package com.newput.utility;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;

/**
 * 
 * @author Newput Description : Use to create and parse the json object
 */

@Service
public class JsonResService {

	private boolean success;
	private String rcode;
	private String error;
	private JSONObject data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
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
	 * 
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createEmployeeJson(Employee emp) {
		JSONObject obj = new JSONObject();
		obj.put("id", emp.getId());
		obj.put("firstName", emp.getFirstName());
		obj.put("lastName", emp.getLastName());
		obj.put("email", emp.getEmail());
		obj.put("dob", emp.getDob());
		obj.put("doj", emp.getDoj());
		obj.put("address", emp.getAddress());
		obj.put("contact", emp.getContact());
		obj.put("gender", emp.getGender());
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject responseSender() {
		JSONObject obj = new JSONObject();
		obj.put("response", getMap());
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject setDataValue(String str, String token) {
		JSONObject obj = new JSONObject();
		obj.put("msg", str);
		if (token != null && !token.equalsIgnoreCase("")) {
			obj.put("token", token);
		}
		setData(obj);
		return obj;
	}

	public HashMap<String, Object> getMap() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", isSuccess());
		map.put("data", getData());
		map.put("rcode", getRcode());
		map.put("error", getError());
		return map;
	}

	public void errorResponse(String response) {
		setDataValue(null,null);
		setError(response);
		setRcode("505");
		setSuccess(false);
	}

	public void successResponse() {
		setError(null);
		setRcode(null);
		setSuccess(true);
	}

}
