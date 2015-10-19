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
import com.newput.service.EmpService;
import com.newput.service.LoginService;
import com.newput.service.SelectiveExcel;
import com.newput.service.TSchedualService;
import com.newput.utility.ExcelTimeSheet;
import com.newput.utility.JsonResService;
import com.newput.utility.ReqParseService;
import com.newput.utility.TTUtil;
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
	private SelectiveExcel excel;

	@Autowired
	private TSchedualService timeSchedual;

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
	private LoginService loginService;

	@Autowired
	private TTUtil util;

	@Autowired
	private ExcelTimeSheet excelTimeSheet;

	/**
	 * @POST Description : Use to add new user into the system and send the
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

		String token = util.generateRandomString();
		reqParser.setEmployeeValue(firstName, lastName, email, dob, doj, address, contact, gender, password, token);
		empService.addUser(emp);
		if (jsonResService.isSuccess()) {
			emailSend.sendMail("registration");
		}
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
				jsonResService.errorResponse("token can not be blank");
			}
		} else {
			jsonResService.errorResponse("Mail id can not be null");
		}
		return jsonResService.responseSender();
	}

	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject login(@FormParam("email") String email, @FormParam("password") String password) {
		if (email != null && !email.equalsIgnoreCase("") && util.mailFormat(email)) {
			if (password != null && !password.equalsIgnoreCase("")) {
				reqParser.setSessionValue(email, password, "");
				loginService.createSession(emp);
			} else {
				jsonResService.errorResponse("password can not be blank");
			}
		} else {
			jsonResService.errorResponse("Mail id can not be null and in proper format");
		}
		return jsonResService.responseSender();
	}

	@Path("/timeEntry")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject timeEntry(@FormParam("lunchIn") String lunchIn, @FormParam("in") String in,
			@FormParam("out") String out, @FormParam("workDate") String workdate,
			@FormParam("lunchOut") String lunchOut, @FormParam("nightIn") String nightIn,
			@FormParam("nightOut") String nightOut, @FormParam("workDesc") String workDesc,
			@FormParam("empId") String emp_id) {
		int id = Integer.parseInt(emp_id);
		if (workdate != null && !workdate.equalsIgnoreCase("")) {
			if (emp_id != null && !emp_id.equalsIgnoreCase("")) {

				timeSchedual.timeSheetValue(lunchIn, in, out, workdate, lunchOut, nightIn, nightOut, id);
				if (workDesc != null && !workDesc.equalsIgnoreCase("")) {
					reqParser.setDateSheetValue(workDesc, workdate, id);
					timeSchedual.dateSheetValue();
				}
				timeSchedual.clearMap();
			} else {
				jsonResService.errorResponse("emp_id can not be null");
			}
		} else {
			jsonResService.errorResponse("Date can not be null");
		}

		return jsonResService.responseSender();
	}

	@Path("/forgotPwd")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject forgotPwd(@FormParam("email") String email, @FormParam("newPassword") String newPassword) {

		if (email != null && !email.equalsIgnoreCase("") && util.mailFormat(email)) {
			if (newPassword != null && !newPassword.equalsIgnoreCase("")) {
				String ptoken = util.generateRandomString();
				newPassword = util.md5(newPassword);
				empService.resetPassword(email, newPassword, ptoken);
				if (jsonResService.isSuccess()) {
					emailSend.sendMail("resetPassword");
				}
			} else {
				jsonResService.errorResponse("password can not be blank");
			}
		} else {
			jsonResService.errorResponse("Mail id can not be null and in proper format");
		}
		return jsonResService.responseSender();
	}

	@Path("/excelExport")
	@POST
	// @Produces("application/vnd.ms-excel")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject excelExport(@FormParam("empId") String emp_id, @FormParam("month") String monthName,
			@FormParam("year") String year) {
		excelTimeSheet.createExcelSheet(Integer.parseInt(emp_id), monthName, year);
		return jsonResService.responseSender();
	}

	@Path("/pwdVerify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject passwordVerification(@FormParam("email") String emailId, @FormParam("pToken") String pToken) {
		if (emailId != null && !emailId.equalsIgnoreCase("")) {
			if (pToken != null && !pToken.equalsIgnoreCase("")) {
				reqParser.setPValidationValue(emailId, pToken);
				empService.pwdVerify(emp);
			} else {
				jsonResService.errorResponse("token can not be blank");
			}
		} else {
			jsonResService.errorResponse("Mail id can not be null");
		}
		return jsonResService.responseSender();
	}

	@Path("/monthlyExcel")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject monthlyExcel(@FormParam("month") String monthName, @FormParam("empId") int emp_id,
			@FormParam("year") String year) {
		if (monthName != null && !monthName.equalsIgnoreCase("")) {
			// timeSchedual.toCheck();
			excel.monthSheet(monthName, emp_id, year);
		} else {
			jsonResService.errorResponse("Please provide the month to select data");
		}

		return jsonResService.responseSender();
	}

	public void email() {
	}

	public void excelExport() {
	}

	public void editDetail() {
	}

	public void emailValidation() {
	}

	@Path("/signOut")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject signOut(@FormParam("empId") String emp_id) {
		loginService.signOut(Integer.parseInt(emp_id));
		return jsonResService.responseSender();
	}

}
