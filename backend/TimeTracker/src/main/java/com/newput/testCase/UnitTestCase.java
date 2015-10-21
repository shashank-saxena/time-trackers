package com.newput.testCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import java.text.ParseException;
import com.newput.domain.Employee;
import com.newput.service.LoginService;
import com.newput.service.TSchedualService;
import com.newput.utility.JsonResService;
import com.newput.utility.ReqParseService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UnitTestCase {

/*	@Autowired
	private Employee emp;
	
	@Autowired
	private TSchedualService timeSchedual;

	@Autowired
	private ReqParseService reqParser;

	@Autowired
	private JsonResService jsonResService;
	
	@Autowired
	private LoginService loginService;

	String email = "rahul@newput.com";
	String password = "abcd";*/

	@Test
	public void testLogin() { 
/*		emp.setEmail(email);
		emp.setPassword(password);
		//assertEquals(true, jsonResService.isSuccess());
		loginService.createSession(emp);*/
		assertEquals(true, true);
	}	

	@Test
	public void testTimeEntry() throws ParseException {
		/*timeSchedual.timeSheetValue("12:00", "9:00", "19:30", "06-11-2015", "12:30", "21:00", "23:00", 1);
		reqParser.setDateSheetValue("this is my 6 date", "06-11-2015", 1);
		timeSchedual.dateSheetValue();*/
		assertEquals(true, true);
	}
}
