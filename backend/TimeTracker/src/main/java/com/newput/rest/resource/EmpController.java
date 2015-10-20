package com.newput.rest.resource;

import java.io.File;

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
import com.newput.utility.EMailSender;

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
	private EMailSender emailSend;

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
	 *       {@link EMailSender}
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
	public JSONObject forgotPwd(@FormParam("email") String email) {

		if (email != null && !email.equalsIgnoreCase("") && util.mailFormat(email)) {
			String ptoken = util.generateRandomString();
			empService.resetPassword(email, ptoken, "password");
			if (jsonResService.isSuccess()) {
				emailSend.sendMail("password");
			}
		} else {
			jsonResService.errorResponse("Mail id can not be null and in proper format");
		}
		return jsonResService.responseSender();
	}

	@Path("/excelExport")
	@POST
	//@Produces("application/vnd.ms-excel")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject excelExport(@FormParam("empId") String emp_id, @FormParam("month") String monthName,
			@FormParam("year") String year) {
		if (emp_id != null && !emp_id.equalsIgnoreCase("")) {
			if (monthName != null && !monthName.equalsIgnoreCase("")) {
				excelTimeSheet.createExcelSheet(Integer.parseInt(emp_id), monthName, year);
			} else {
				jsonResService.errorResponse("Please provide the month to select data");
			}
		} else {
			jsonResService.errorResponse("Please provide employee id to select data");
		}
		return jsonResService.responseSender();		
	}

	@Path("/pwdVerify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject passwordVerification(@FormParam("empId") int empId, @FormParam("pToken") String pToken,
			@FormParam("newPassword") String newPwd) {
		if (empId > 0) {
			if (pToken != null && !pToken.equalsIgnoreCase("")) {
				if (newPwd != null && !newPwd.equalsIgnoreCase("")) {
					newPwd = util.md5(newPwd);
					reqParser.setPValidationValue(empId, pToken, newPwd);
					empService.pwdVerify(emp);
				} else {
					jsonResService.errorResponse("Password can not be blank");
				}
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
	public JSONObject monthlyExcel(@FormParam("month") String monthName, @FormParam("empId") String emp_id,
			@FormParam("year") String year) {
		if (emp_id != null && !emp_id.equalsIgnoreCase("")) {
			if (monthName != null && !monthName.equalsIgnoreCase("")) {
				excel.monthSheet(monthName, Integer.parseInt(emp_id), year);
			} else {
				jsonResService.errorResponse("Please provide the month to select data");
			}
		} else {
			jsonResService.errorResponse("Please provide employee id to select data");
		}

		return jsonResService.responseSender();
	}

	@Path("/mailExcelSheet")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject mailExcelSheet(@FormParam("empId") String emp_id, @FormParam("month") String monthName,
			@FormParam("year") String year) {
		if (emp_id != null && !emp_id.equalsIgnoreCase("")) {
			if (monthName != null && !monthName.equalsIgnoreCase("")) {
				File file = excelTimeSheet.createExcelSheet(Integer.parseInt(emp_id), monthName, year);
				if (jsonResService.isSuccess()) {
					emailSend.sendExcelSheet(excelTimeSheet.getEmpEmail(Integer.parseInt(emp_id)), file);
					file.delete();
				}
			} else {
				jsonResService.errorResponse("Please provide the month to select data");
			}
		} else {
			jsonResService.errorResponse("Please provide employee id to select data");
		}
		return jsonResService.responseSender();
	}

	@Path("/resend")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject resendMail(@FormParam("email") String email, @FormParam("flag") String flag) {
		if (email != null && !email.equalsIgnoreCase("") && util.mailFormat(email)) {
			if (flag != null && !flag.equalsIgnoreCase("")) {
				String token = util.generateRandomString();
				empService.resetPassword(email, token,flag);
				if (jsonResService.isSuccess()) {
					emailSend.sendMail(flag);
				}
			} else {
				jsonResService.errorResponse("Flag is must");
			}
		} else {
			jsonResService.errorResponse("Email id not valid");
		}
		return jsonResService.responseSender();
	}

	@Path("/signOut")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject signOut(@FormParam("empId") String emp_id) {
		loginService.signOut(Integer.parseInt(emp_id));
		return jsonResService.responseSender();
	}

}
