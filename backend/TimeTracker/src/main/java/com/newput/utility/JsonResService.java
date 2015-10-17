package com.newput.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;

/**
 * 
 * @author Newput Description : Use to create and parse the json object
 */

@Service
public class JsonResService {

	@Autowired
	private TTUtil util;

	private boolean success;
	private String rcode;
	private String error;
	// private JSONObject data;
	private ArrayList<JSONObject> data;

	public ArrayList<JSONObject> getData() {
		return data;
	}

	public void setData(ArrayList<JSONObject> data) {
		this.data = data;
	}

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

	// public JSONObject getData() {
	// return data;
	// }

	// public void setData(JSONObject data) {
	// this.data = data;
	// }

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
	public JSONObject createTimeSheetJson(HashMap<String, String> map) {
		JSONObject obj = new JSONObject();
		obj.put("workDate", map.get("workDate"));
		obj.put("in", map.get("in"));
		obj.put("out", map.get("out"));
		obj.put("lunchIn", map.get("lunchIn"));
		obj.put("lunchOut", map.get("lunchOut"));
		obj.put("nightIn", map.get("nightIn"));
		obj.put("nightOut", map.get("nightOut"));
		obj.put("workDesc", map.get("workDesc"));
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getTimeSheetJson(HashMap<String, Long> map, String totalHour, String workDesc, Long workDate) {
		JSONObject obj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date = sdf.format(workDate);
		obj.put("workDate", date);
		obj.put("in", util.timeHrs(map.get("in")));
		obj.put("out", util.timeHrs(map.get("out")));
		obj.put("lunchIn", util.timeHrs(map.get("lunchIn")));
		obj.put("lunchOut", util.timeHrs(map.get("lunchOut")));
		obj.put("nightIn", util.timeHrs(map.get("nightIn")));
		obj.put("nightOut", util.timeHrs(map.get("nightOut")));
		obj.put("totalHour", totalHour);
		obj.put("workDesc", workDesc);
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject responseSender() {
		JSONObject obj = new JSONObject();
		obj.put("response", getMap());
		return obj;
	}

	@SuppressWarnings("unchecked")
	public void setDataValue(String str, String token) {
		ArrayList<JSONObject> objArray = new ArrayList<JSONObject>();
		JSONObject obj = new JSONObject();
		obj.put("msg", str);
		if (token != null && !token.equalsIgnoreCase("")) {
			obj.put("token", token);
		}
		objArray.add(obj);
		setData(objArray);
		// return obj;
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
		setDataValue(null, null);
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
