package com.newput.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;
import com.newput.domain.EmployeeExample;
import com.newput.mapper.EmployeeMapper;
import com.newput.utility.JsonResService;
import com.newput.utility.ReqParseService;
import com.newput.utility.TTUtil;

/**
 * 
 * @author Newput Description : Use to send control to the mapper classes
 */
@Service
public class EmpService {

	@Autowired
	EmployeeMapper empMapper;

	@Autowired
	private JsonResService jsonResService;

	@Autowired
	private ReqParseService reqres;

	@Autowired
	private TTUtil util;

	@Autowired
	private Employee emp;
	ArrayList<JSONObject> objArray = new ArrayList<JSONObject>();

	public void addUser(Employee employee) {
		int i = 0;
		if (employee.getEmail() != null && !employee.getEmail().equalsIgnoreCase("")
				&& util.mailFormat(employee.getEmail())) {
			if (employee.getPassword() != null && !employee.getPassword().equalsIgnoreCase("")) {
				if (employee.getContact() != null && !employee.getContact().equalsIgnoreCase("")) {
					if (employee.getFirstName() != null && !employee.getFirstName().equalsIgnoreCase("")) {
						if (employee.getLastName() != null && !employee.getLastName().equalsIgnoreCase("")) {
							if (employee.getDob() != null) {
								if (employee.getDoj() != null) {
									if (employee.getGender() != null && !employee.getGender().equalsIgnoreCase("")) {
										i = empMapper.insertSelective(employee);
										if (i > 0) {
											jsonResService.successResponse();
											objArray.add(jsonResService.createEmployeeJson(employee));
											jsonResService.setData(objArray);
										} else {
											jsonResService.errorResponse("invalid response");
										}
									} else {
										jsonResService.errorResponse("Please fill your gender");
									}
								} else {
									jsonResService.errorResponse("please fill your date of joining");
								}
							} else {
								jsonResService.errorResponse("please fill your dob");
							}
						} else {
							jsonResService.errorResponse("Last name can not be null");
						}
					} else {
						jsonResService.errorResponse("First name can not be null");
					}
				} else {
					jsonResService.errorResponse("Contact no should be valid");
				}
			} else {
				jsonResService.errorResponse("password can not be null");
			}
		} else {
			jsonResService.errorResponse("Mail id should be valid");
		}
		objArray.clear();
	}

	public void mailVerify(Employee employee) {
		EmployeeExample example = new EmployeeExample();
		example.createCriteria().andEmailEqualTo(employee.getEmail()).andVTokenEqualTo(employee.getvToken());
		List<Employee> employeeList = empMapper.selectByExample(example);
		if (employeeList.size() > 0) {
			employee = employeeList.get(0);
			example.createCriteria().andEmailEqualTo(employee.getEmail());
			employee.setStatus(true);
			employee.setUpdated(new Date().getTime() / 1000);
			employee.setRole("employee");
			empMapper.updateByExampleSelective(employee, example);
			objArray.add(jsonResService.createEmployeeJson(employee));
			jsonResService.setData(objArray);
			jsonResService.successResponse();
		} else {
			jsonResService.errorResponse("your id or token is not correct");
		}
		objArray.clear();
	}

	public void resetPassword(String email, String pToken) {

		EmployeeExample example = new EmployeeExample();
		example.createCriteria().andEmailEqualTo(email);

		List<Employee> empl = empMapper.selectByExample(example);
		if (empl.size() > 0) {
			Employee emply = new Employee();
			emply = empl.get(0);
			emply.setpToken(pToken);
			emply.setpExpireAt(reqres.getCurrentTime() + 30);
			emply.setUpdated(reqres.getCurrentTime());
			int i = empMapper.updateByExampleSelective(emply, example);
			emp.setId(emply.getId());
			emp.setEmail(email);
			emp.setpToken(pToken);
			if (i > 0) {
				jsonResService.successResponse();
				objArray.add(jsonResService.createEmployeeJson(emply));
				jsonResService.setData(objArray);
			} else {
				jsonResService.errorResponse("invalid response");
			}
		} else {
			jsonResService.errorResponse("user not exist");
		}
		objArray.clear();
	}

	public void pwdVerify(Employee employee) {
		EmployeeExample example = new EmployeeExample();
		example.createCriteria().andIdEqualTo(employee.getId()).andPTokenEqualTo(employee.getpToken());
		List<Employee> employeeList = empMapper.selectByExample(example);
		if (employeeList.size() > 0) {
			employee = employeeList.get(0);
			if (employee.getpExpireAt() >= reqres.getCurrentTime()) {
				example.createCriteria().andIdEqualTo(emp.getId());
				employee.setPassword(emp.getPassword());
				employee.setUpdated(new Date().getTime() / 1000);
				int i = empMapper.updateByExampleSelective(employee, example);
				if (i > 0) {
					objArray.add(jsonResService.createEmployeeJson(employee));
					jsonResService.setData(objArray);
					jsonResService.successResponse();
				} else {
					jsonResService.errorResponse("Please try again");
				}

			} else {
				jsonResService.errorResponse("your password is expire please reset your password again.");
			}
		} else {
			jsonResService.errorResponse("your id or token is not correct");
		}
		objArray.clear();
	}
}
