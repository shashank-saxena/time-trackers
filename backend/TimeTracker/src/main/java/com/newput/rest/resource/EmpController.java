package com.newput.rest.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	 * @POST 
	 * Description : Use to add new user into the system and send the
	 *       validation email to the registered mail id
	 *       {@link VerificationMailSend}
	 */
	@Path("/register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject registerUser(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName,
			@FormParam("email") String email, @FormParam("dob") String dob, @FormParam("doj") String doj,
			@FormParam("address") String address, @FormParam("contact") String contact,
			@FormParam("gender") String gender, @FormParam("password") String password) {

		String token = emailSend.generateRandomString();
		reqParser.setEmployeeValue(firstName, lastName, email, dob, doj, address, contact, gender, password, token);
		empService.addUser(emp);
		emailSend.sendMail();
		
		return jsonResService.responseSender();
	}


	@Path("/verify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject mailVerification(@FormParam("email") String emailId, @FormParam("token") String token) {
		if (emailId != null && !emailId.equalsIgnoreCase("")) {
			if (token != null && !token.equalsIgnoreCase("")) {
				reqParser.setValidationValue(emailId, token);
				empService.mailVerify(emp);

			} else {
				jsonResService.setDataValue("token can not be blank");
				jsonResService.setError("null");
				jsonResService.setRcode("null");
				jsonResService.setSuccess(false);
			}
		} else {
			jsonResService.setDataValue("email id can not be blank");
			jsonResService.setError("null");
			jsonResService.setRcode("null");
			jsonResService.setSuccess(false);
		}
		return jsonResService.responseSender();
	}
	
	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)	
	public JSONObject login(@FormParam("email") String email, @FormParam("password") String password) {		
		reqParser.setSessionValue(email, password, "");
		loginService.createSession(emp);
	return jsonResService.responseSender();
	}
	
	@Path("/loginSession")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject loginSession(@FormParam("token") String token){
		reqParser.setSessionValue("", "", token);
		loginService.sessionManagement(session);		
		return jsonResService.responseSender();
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
