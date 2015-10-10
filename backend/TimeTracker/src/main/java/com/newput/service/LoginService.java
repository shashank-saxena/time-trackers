package com.newput.service;

//import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.Employee;
import com.newput.domain.Session;
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

	public Long getCurrentTime(){
		return System.currentTimeMillis()/1000;
	}
	
	public void createSession(Employee employee) {		
		boolean status = false;
		int i = 0;
		employee.setPassword(util.md5(employee.getPassword()));
		Employee emp = empMapper.selectByEmailPassword(employee);
		if(emp == null){
			jsonResService.setDataValue("invalid user");			
		}else{
			Session localSession = sessionMapper.selectByEmpPrimaryKey(emp.getId());
			if(localSession == null){
				if(emp.getStatus()){
					session.setEmpId(emp.getId());
					session.setEmpName(emp.getFirstName());
					session.setToken(util.createSessionKey(getCurrentTime(), emp.getEmail()));
					session.setCreated(getCurrentTime());
					session.setExpiresWhen(getCurrentTime()+1800);
					i = sessionMapper.insert(session);
					if (i > 0) {
						status = true;
						jsonResService.setDataValue("Welcome User created : "+emp.getFirstName());
					}else{
						jsonResService.setDataValue("session token is not created");
					}										
				}else{
					jsonResService.setDataValue("email is not verified");	
				}
			}else{
				localSession.setUpdated(getCurrentTime());
				localSession.setExpiresWhen(getCurrentTime()+60);
				localSession.setToken(util.createSessionKey(getCurrentTime(), emp.getEmail()));
				i = sessionMapper.updateByPrimaryKey(localSession);
				if (i > 0) {
					status = true;
					jsonResService.setDataValue("Welcome User updated : "+emp.getFirstName());
				}else{
					jsonResService.setDataValue("token is not update");
				}				
			}						
		}
		jsonResService.setSuccess(status);
		if(status){
			jsonResService.setRcode("null");
			jsonResService.setError("null");			
		}else{
			jsonResService.setRcode("505");
			jsonResService.setError("invalid response");			
		}		
	}

	public void sessionManagement(Session session) {
		boolean status = false;
		int i = 0;
		Session localSession = sessionMapper.selectByTokenKey(session.getToken());
		if(localSession == null){
			jsonResService.setDataValue("token not found");
		}else{
			Long expireTime = localSession.getExpiresWhen();
			Long currentTime = getCurrentTime();
			if(expireTime>currentTime){
				localSession.setUpdated(getCurrentTime());
				localSession.setExpiresWhen(getCurrentTime()+60);
				i = sessionMapper.updateByPrimaryKey(localSession);	
				if (i > 0) {
					status = true;
					jsonResService.setDataValue("Welcome User through token: "+localSession.getEmpName());
				}else{
					jsonResService.setDataValue("token is not update");
				}
			}else{
				jsonResService.setDataValue("token is expired please login again");		
			}
		}
		jsonResService.setSuccess(status);
		if(status){
			jsonResService.setRcode("null");
			jsonResService.setError("null");			
		}else{
			jsonResService.setRcode("505");
			jsonResService.setError("invalid response");			
		}
	}
}
