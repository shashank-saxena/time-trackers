package com.newput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;
import com.newput.mapper.EmployeeMapper;
import com.newput.utility.JsonResService;

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
	
	public void addUser(Employee employee) {
		int i = 0;
		boolean status = false;
		i = empMapper.insert(employee);

		if (i > 0) {
			status = true;
		}
	
		//jsonResService.setDataValue(@Value("${registarationStatus}"));
		jsonResService.setDataValue("you are successfully registered");
		jsonResService.setError("null");
		jsonResService.setRcode("null");
		jsonResService.setSuccess(status);
	}
	
	public void mailVerify(Employee employee) {
		int i = 0;
		boolean status = false;
		String result = "You are not registred with the specified mail id";
		System.out.println("in verify api");
	    i=empMapper.selectByEmailId(employee);

		if (i > 0) {
			status = true;
			employee.setStatus(status);
			System.out.println("in true status");
			empMapper.updateByEmail(employee);
			result = "Your mailid is successfully verified";
		}

		//jsonResService.setDataValue("${registarationStatus}");
		jsonResService.setDataValue(result);
		jsonResService.setError("null");
		jsonResService.setRcode("null");
		jsonResService.setSuccess(status);
	}
	
	public void mailVerify(Employee employee) {
		int i = 0;
		boolean status = false;
		String result = "You are not registred with the specified mail id";
		System.out.println("in verify api");
	    i=empMapper.selectByEmailId(employee);

		if (i > 0) {
			status = true;
			employee.setStatus(status);
			System.out.println("in true status");
			empMapper.updateByEmail(employee);
			result = "Your mailid is successfully verified";
		}

		//jsonResService.setDataValue("${registarationStatus}");
		jsonResService.setDataValue(result);
		jsonResService.setError("null");
		jsonResService.setRcode("null");
		jsonResService.setSuccess(status);
	}
}
