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
import com.newput.service.JsonResService;

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
	private JsonResService jsonResService;

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
	 *         e.g.{"response":{"data":{"lastName":"kulmi","address":"indore",
	 *         "role":"employee","gender":"m","created":10092015,"pwd_verify":
	 *         true,"time_zone":5.5,"firstName":"rahul","password":"xyz","dob":
	 *         1444283837170,"contact":"9907614646","updated":10102015,"email":
	 *         "rahul@newput.com","doj":1444283837170,"status":true},"success":
	 *         "true","rcode":"null","error":"null"}}
	 * @throws ParseException
	 */
	@Path("/get")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getIt() throws ParseException {
		JSONObject obj = jsonResService.createRegistrationJson();
		jsonResService.setSuccess("true");
		jsonResService.setData(obj);
		jsonResService.setRcode("null");
		jsonResService.setError("null");
		jsonResService.setRegistrationJson(obj);
		jsonResService.responseSender();
		System.out.println("created json response ::" + jsonResService.responseSender());
		return jsonResService.responseSender();
	}

	/**
	 * @POST Description-Use to add new user into the system and send the
	 *       validation email to the registered mail id
	 *       {@link VerificationMailSend}
	 */
	@Path("/add")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public void addUser() {
		String token = emailSend.generateRandomString();
		System.out.println("token value : " + token);
		emp.setvToken(token);
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
