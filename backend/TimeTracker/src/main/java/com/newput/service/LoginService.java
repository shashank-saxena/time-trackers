package com.newput.service;

import java.util.List;

//import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;
import com.newput.domain.EmployeeExample;
import com.newput.domain.Session;
import com.newput.domain.SessionExample;
import com.newput.mapper.EmployeeMapper;
import com.newput.mapper.SessionMapper;
import com.newput.utility.JsonResService;
import com.newput.utility.TTUtil;

@Service
public class LoginService {

	@Autowired
	private SessionMapper sessionMapper;

	@Autowired
	private EmployeeMapper empMapper;

	@Autowired
	private JsonResService jsonResService;

	@Autowired
	private Session session;

	@Autowired
	private TTUtil util;

	public Long getCurrentTime() {
		return System.currentTimeMillis() / 1000;
	}

	public boolean createSession(Employee employee) {
		int i = 0;
		employee.setPassword(util.md5(employee.getPassword()));
		EmployeeExample example = new EmployeeExample();
		example.createCriteria().andEmailEqualTo(employee.getEmail()).andPasswordEqualTo(employee.getPassword());
		List<Employee> employeeList = empMapper.selectByExample(example);
		if (employeeList.isEmpty()) {
			jsonResService.errorResponse("invalid user");			
		} else {
			Employee emp = employeeList.get(0);
			SessionExample sessionExample = new SessionExample();
			sessionExample.createCriteria().andEmpIdEqualTo(emp.getId());
			List<Session> sessionList = sessionMapper.selectByExample(sessionExample);
			if (sessionList.isEmpty()) {
				if (emp.getStatus()) {
					session.setEmpId(emp.getId());
					session.setEmpName(emp.getFirstName());
					session.setToken(util.createSessionKey(getCurrentTime(), emp.getEmail()));
					session.setCreated(getCurrentTime());
					session.setExpiresWhen(getCurrentTime() + 3600);
					i = sessionMapper.insertSelective(session);
					if (i > 0) {
						jsonResService.setDataValue("Welcome User created : " + emp.getFirstName(), session.getToken());
						jsonResService.successResponse();
					} else {
						jsonResService.errorResponse("session token is not created");						
					}
				} else {
					jsonResService.errorResponse("email is not verified");					
				}
			} else {
				Session localSession = sessionList.get(0);
				localSession.setUpdated(getCurrentTime());
				localSession.setExpiresWhen(getCurrentTime() + 1800);
				localSession.setToken(util.createSessionKey(getCurrentTime(), emp.getEmail()));
				i = sessionMapper.updateByPrimaryKey(localSession);
				if (i > 0) {
					jsonResService.successResponse();
					jsonResService.setDataValue("Welcome User updated : " + emp.getFirstName(),
							localSession.getToken());
				} else {
					jsonResService.errorResponse("token is not update");					
				}
			}
		}
		return true;
	}

	public boolean loginSessionFilter(String token) {
		int i = 0;
		SessionExample sessionExample = new SessionExample();
		sessionExample.createCriteria().andTokenEqualTo(token);
		List<Session> sessionList = sessionMapper.selectByExample(sessionExample);
		if (sessionList.isEmpty()) {
			jsonResService.errorResponse("token not found");
			return false;
		} else {
			Session localSession = sessionList.get(0);
			Long expireTime = localSession.getExpiresWhen();
			Long currentTime = getCurrentTime();
			if (expireTime > currentTime) {
				localSession.setUpdated(getCurrentTime());
				localSession.setExpiresWhen(getCurrentTime() + 1800);
				i = sessionMapper.updateByPrimaryKey(localSession);
				if (i > 0) {
					jsonResService.successResponse();
					jsonResService.setDataValue("Welcome User through token: " + localSession.getEmpName(),
							localSession.getToken());
					return true;
				} else {
					jsonResService.errorResponse("token is not update");
					return false;
				}
			} else {
				jsonResService.errorResponse("token is expired please login again");
				return false;
			}
		}
	}

	public void signOut(int emp_id) {
		int i = 0;
		SessionExample sessionExample = new SessionExample();
		sessionExample.createCriteria().andEmpIdEqualTo(emp_id);
		List<Session> sessionList = sessionMapper.selectByExample(sessionExample);
		if (sessionList.isEmpty()) {
			jsonResService.errorResponse("employee not found");
		} else {
			Session localSession = sessionList.get(0);
			localSession.setExpiresWhen(getCurrentTime());
			i = sessionMapper.updateByExample(localSession, sessionExample);					
			if (i > 0) {
				jsonResService.successResponse();
				jsonResService.setDataValue("User succefully signout : " + localSession.getEmpName(), "");				
			} else {
				jsonResService.errorResponse("user can not sign out");				
			}
		}
	}
}
