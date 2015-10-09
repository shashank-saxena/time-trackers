package com.newput.rest.resource;

import java.util.Date;
import java.util.HashMap;

<<<<<<< HEAD

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
=======
//import javax.ws.rs.FormParam;
>>>>>>> e44a126... Verify mail and update flag


//import java.math.BigDecimal;
//import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//
import com.newput.domain.Employee;
import com.newput.domain.Session;
import com.newput.service.EmpService;
import com.newput.service.LoginService;
import com.newput.utility.JsonResService;
import com.newput.utility.ReqParseService;
import com.newput.utility.VerificationMailSend;

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
	
	@Autowired
	private ReqParseService reqParser;
	
	@Autowired
	private Session session;
	
	@Autowired
	private LoginService loginService;

	/**
	 * Method - GET 
	 * Description : Use for creation and parsing of a json
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
		jsonResService.setSuccess(true);
		jsonResService.setData(obj);
		jsonResService.setRcode("null");
		jsonResService.setError("null");
		jsonResService.setRegistrationJson(obj);
		jsonResService.responseSender();
		//System.out.println("created json response ::" + jsonResService.responseSender());
		return jsonResService.responseSender();
	}

	/**
	 * @POST 
	 * Description : Use to add new user into the system and send the
	 *       validation email to the registered mail id
	 *       {@link VerificationMailSend}
	 */
	@Path("/register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
<<<<<<< HEAD
	public JSONObject registerUser(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName, 
			@FormParam("email") String email, @FormParam("dob") String dob, @FormParam("doj") String doj, 
			@FormParam("address") String address, @FormParam("contact") String contact, 
			@FormParam("gender") String gender, @FormParam("password") String password) {		

		String token = emailSend.generateRandomString();		
		reqParser.setEmployeeValue(firstName, lastName, email, dob, doj, address, contact, gender, password, token);
		System.out.println("here now");
//		String empl ="address=indore&contact=1234567890&dob=03-03-2015&doj=03-03-2015&email=rahul@newput.com&firstName=deepti&gender=F&lastName=modi&password=check2";
//		HashMap<String, String> mapValue=reqParser.reqParser(empl);
//		reqParser.setEmployeeValue(mapValue, token);
		
		empService.addUser(emp);
		emailSend.sendMail();		

=======
	public JSONObject registerUser() {
		
		String empl ="address=indore&contact=1234567890&dob=03-03-2015&doj=03-03-2015&email=rahul.kulmi@gmail.com&firstName=deepti&gender=F&lastName=modi&password=check2";
		
		String token = emailSend.generateRandomString();
		HashMap<String, String> mapValue=reqParser.reqParser(empl);
		reqParser.setEmployeeValue(mapValue, token);
		empService.addUser(emp);
		//emailSend.sendMail();
		
>>>>>>> e44a126... Verify mail and update flag
		return jsonResService.responseSender();
	}

	@Path("/verify")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject mailVerification() {
//		@FormParam("token") String token,@FormParam("emailId") String mailId
		String empl ="token=UD1N3AMP&email=rahul.kulmi@gmail.com";
		
		HashMap<String, String> mapValue=reqParser.reqParser(empl);
		reqParser.setEmployeeValue(mapValue, mapValue.get("token"));
		empService.mailVerify(emp);
		return jsonResService.responseSender();
	}
	
	/*
	 * @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, 
			@FormParam("email") String email, @FormParam("dob") Date dob, @FormParam("doj") Date doj, 
			@FormParam("address") String address, @FormParam("contact") String contact, 
			@FormParam("gender") String gender, @FormParam("password") String password
	*/
	
	@Path("/login")
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public JSONObject login() {		
//		System.out.println("email vlaue is : "+email);
//		System.out.println("password valus is : "+pwd);
//		System.out.println("address value is : "+address);
//	String empl ="email=rahul@newput.com&password=check2";		
	//HashMap<String, String> mapValue=reqParser.reqParser(empl);
		//System.out.println("map vlaue 11 : "+mapValue);
		//reqParser.setEmployeeValue(mapValue, "");
//emp.setEmail("varsha@newput.com");
//emp.setPassword("check");
//loginService.createSession(emp);
		
//		String token = emailSend.generateRandomString();
//		
//		reqParser.setEmployeeValue(firstName, lastName, email, dob, doj, address, contact, gender, password, token);
		
		jsonResService.setDataValue("token is not update");
		jsonResService.setError("invalid response");
		jsonResService.setRcode("505");
		jsonResService.setSuccess(true);
	return jsonResService.responseSender();
	}
	
//	@Path("/loginSession")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public JSONObject loginSession(){
////		String empl ="token=62EC4DFF8394CD3130582A55A278E452";		
////		HashMap<String, String> mapValue=reqParser.reqParser(empl);
////		System.out.println("map vlaue 22 : "+mapValue);
////		reqParser.setSessionValue(mapValue);
////		//session.setToken("59A232E135230F93EE46CA41B9D7B960");
////		loginService.sessionManagement(session);
////		jsonResService.setDataValue("token is not update");
////		jsonResService.setError("invalid response");
////		jsonResService.setRcode("505");
////		jsonResService.setSuccess(true);
////		return jsonResService.responseSender();
//	}
	

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
