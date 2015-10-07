package com.newput.rest.resource;

//import java.math.BigDecimal;
//import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.newput.domain.Employee;
import com.newput.service.EmpService;
import com.newput.service.VerificationMailSend;
import com.newput.service.ReqResParser;

/**
 * Description : Use as a controller class to pass control on the services
 * 
 * @author Newput
 * 
 */
@Controller
@Path("/employee")
public class EmpController {

	@Autowired
	private ReqResParser reqResParse;

	@Autowired
	private EmpService empService;

	@Autowired
	private Employee emp;

	@Autowired
	private VerificationMailSend emailSend;

	/**
	 * Method - GET Description - Use for creation and parsing of a json
	 * 
	 * @return json object
	 *         e.g.{"id":null,"firstName":"user","lastName":"tracker","email":
	 *         "user@newput.com","dob":1444219564605,"doj":1444219564605,
	 *         "address":"indore","contact":"1234567890","gender":"m","password"
	 *         :"xyz","status":true,"passwordVerification":true,"role":
	 *         "employee","created":10092015,"updated":10102015,"timeZone":5.5}
	 * @throws ParseException
	 */
	@Path("/get")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getIt() throws ParseException {
		JSONObject obj = reqResParse.createRegistrationJson();
		reqResParse.setRegistrationJson(obj);
		return emp;
	}

	/**
	 * @POST
	 * Description-Use to add new user into the system and send the validation email to the registered mail id
	 * {@link VerificationMailSend}
	 */
	@Path("/add")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public void addUser() {
		empService.addUser(emp);
		emailSend.sendMail();
	}

	public void registration() {
	}

	public void forgotPwd() {
	}

	public void email() {
	}

	public void excelExport() {
	}

	public void monthlyExcel() {
	}

	public void editDetail() {
	}

	public void signOut() {
	}

	public void emailValidation() {
	}
}
