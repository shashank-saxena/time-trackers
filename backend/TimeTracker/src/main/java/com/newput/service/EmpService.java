package com.newput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;
import com.newput.mapper.EmployeeMapper;

/**
 * 
 * @author Newput
 *Description-Use to send control to the mapper classes
 */
@Service
public class EmpService {

	@Autowired
	EmployeeMapper empMapper;
	
	public void addUser(Employee employee){
		empMapper.insert(employee);		
	}
}
