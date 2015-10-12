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

	public void createSession(Employee employee) {
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
					session.setExpiresWhen(getCurrentTime() + 1800);
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
				localSession.setExpiresWhen(getCurrentTime() + 60);
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
	}

	public void sessionManagement(Session session) {
		int i = 0;
		SessionExample sessionExample = new SessionExample();
		sessionExample.createCriteria().andTokenEqualTo(session.getToken());
		List<Session> sessionList = sessionMapper.selectByExample(sessionExample);
		if (sessionList.isEmpty()) {
			jsonResService.errorResponse("token not found");
		} else {
			Session localSession = sessionList.get(0);
			Long expireTime = localSession.getExpiresWhen();
			Long currentTime = getCurrentTime();
			if (expireTime > currentTime) {
				localSession.setUpdated(getCurrentTime());
				localSession.setExpiresWhen(getCurrentTime() + 60);
				i = sessionMapper.updateByPrimaryKey(localSession);
				if (i > 0) {
					jsonResService.successResponse();
					jsonResService.setDataValue("Welcome User through token: " + localSession.getEmpName(),
							localSession.getToken());
				} else {
					jsonResService.errorResponse("token is not update");
				}
			} else {
				jsonResService.errorResponse("token is expired please login again");
			}
		}
	}
}
