package com.newput.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.utility.ExcelTimeSheet;
import com.newput.utility.JsonResService;
import com.newput.utility.TTUtil;

@Service
public class SelectiveExcel {

	@Autowired
	private TTUtil util;

	@Autowired
	private ExcelTimeSheet excelSheet;

	@Autowired
	private JsonResService jsonRes;

	public void monthSheet(String monthName, int emp_id, String year) {
		if (emp_id > 0) {
			if (monthName != null && !monthName.equalsIgnoreCase("")) {
				HashMap<String, Long> mapValue = util.getMonthlyDate(monthName, year);
				excelSheet.getTimeSheetData(null, emp_id, mapValue.get("minDate"), mapValue.get("maxDate"),
						"monthSheet");

				jsonRes.successResponse();
			} else {
				jsonRes.errorResponse("Please specified month name");
			}
		} else {
			jsonRes.errorResponse("emp_id can not be null");
		}
	}

}
