package com.newput.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.newput.domain.Employee;
import com.newput.domain.EmployeeExample;
import com.newput.mapper.EmployeeMapper;
import com.newput.utility.EMailSender;
import com.newput.utility.ExcelTimeSheet;
import com.newput.utility.JsonResService;

public class CronService {

	@Autowired
	private EmployeeMapper empMapper;

	@Autowired
	private Employee emp;

	@Autowired
	private EMailSender sendEmail;

	@Autowired
	private ExcelTimeSheet excelTimeSheet;

	@Autowired
	private JsonResService jsonResService;

	@Autowired
	private EMailSender emailSend;

	public void dailyNotification() {

		List<Employee> list = new ArrayList<Employee>();
		EmployeeExample empExample = new EmployeeExample();
		empExample.createCriteria().andStatusEqualTo(true);
		list = empMapper.selectByExample(empExample);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				emp = list.get(i);
				sendEmail.notificationMail(emp);
			}
		}
	}

	public void emailSendJob() {

		Calendar cal = Calendar.getInstance();
		Long currntStamp = cal.getTimeInMillis();

		String year = new SimpleDateFormat("YYYY").format(currntStamp);
		String mnth = new SimpleDateFormat("MMMM").format(currntStamp);
		List<Employee> list = new ArrayList<Employee>();
		EmployeeExample empExample = new EmployeeExample();
		empExample.createCriteria().andStatusEqualTo(true);
		list = empMapper.selectByExample(empExample);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				emp = list.get(i);
				File file = excelTimeSheet.createExcelSheet(emp.getId(), mnth, year);
				if (jsonResService.isSuccess()) {
					emailSend.sendExcelSheet(excelTimeSheet.getEmpEmail(emp.getId()), file);
					file.delete();
				}
			}
		}
	}

}
