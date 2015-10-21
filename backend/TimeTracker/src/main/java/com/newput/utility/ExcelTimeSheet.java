package com.newput.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.DateSheet;
import com.newput.domain.DateSheetExample;
import com.newput.domain.Employee;
import com.newput.domain.TimeSheet;
import com.newput.domain.TimeSheetExample;
import com.newput.mapper.DateSheetMapper;
import com.newput.mapper.EmployeeMapper;
import com.newput.mapper.TimeSheetMapper;

@Service
public class ExcelTimeSheet {

	@Autowired
	private JsonResService jsonResService;

	@Autowired
	TimeSheetMapper timeSheetMapper;

	@Autowired
	DateSheetMapper dateSheetMapper;

	@Autowired
	TTUtil util;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	Employee employee;

	public File createExcelSheet(int emp_id, String monthName, String year) {
	
		File temp = null;
	    try {
			temp = File.createTempFile("tempfile", ".xls");
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Time_Sheet");

		HashMap<String, String> empMap = new HashMap<>();
		empMap.put("month", monthName);
		empMap.put("year", year);
		empMap.put("name", getEmpName(emp_id));

		sheet.setColumnWidth(8, 20000);
		createSheetStructure(sheet, workbook, empMap);
		getTimeSheetData(sheet, emp_id, util.getMonthlyDate(monthName, year).get("minDate"),
				util.getMonthlyDate(monthName, year).get("maxDate"), "excelExport", workbook);

		try {
			FileOutputStream outStream = new FileOutputStream(temp);
			workbook.write(outStream);
			outStream.close();
			jsonResService.successResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public void insertRow(DateSheet dateSheet, HSSFSheet sheet, HashMap<String, Long> map, String totalHours,
			Workbook workbook) {
		int rowCount = util.getExcelSheetDate(dateSheet.getWorkDate()) + 4;
		HSSFRow aRow = sheet.createRow(rowCount - 1);
		String formulaString = "C" + rowCount + "-B" + rowCount + "+E" + rowCount + "-D" + rowCount + "+G" + rowCount
				+ "-F" + rowCount;

		// create style for row date
		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setAlignment(CellStyle.ALIGN_CENTER);

		// create style for row cells
		CellStyle style = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		style.setDataFormat(createHelper.createDataFormat().getFormat("hh:mm"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);

		aRow.createCell(0).setCellValue(util.getExcelSheetDate(dateSheet.getWorkDate()));
		aRow.getCell(0).setCellStyle(dateStyle);
		aRow.createCell(1).setCellValue(util.timeHrs(map.get("in")));
		aRow.getCell(1).setCellStyle(style);
		if (util.timeHrs(map.get("lunchIn")).equals("") && util.timeHrs(map.get("lunchOut")).equals("")) {
			aRow.createCell(2).setCellValue(util.timeHrs(map.get("out")));
			aRow.getCell(2).setCellStyle(style);
		} else {
			aRow.createCell(2).setCellValue(util.timeHrs(map.get("lunchIn")));
			aRow.getCell(2).setCellStyle(style);
			aRow.createCell(3).setCellValue(util.timeHrs(map.get("lunchOut")));
			aRow.getCell(3).setCellStyle(style);
			aRow.createCell(4).setCellValue(util.timeHrs(map.get("out")));
			aRow.getCell(4).setCellStyle(style);
		}
		aRow.createCell(5).setCellValue(util.timeHrs(map.get("nightIn")));
		aRow.getCell(5).setCellStyle(style);
		aRow.createCell(6).setCellValue(util.timeHrs(map.get("nightOut")));
		aRow.getCell(6).setCellStyle(style);
		aRow.createCell(7).setCellFormula(formulaString);
		aRow.getCell(7).setCellStyle(style);
		aRow.createCell(8).setCellValue(dateSheet.getWorkDesc());
	}

	public void getTimeSheetData(HSSFSheet sheet, int emp_id, Long minDate, Long maxDate, String module,
			Workbook workbook) {
		HashMap<String, Long> map = new HashMap<String, Long>();
		ArrayList<JSONObject> jsonArray = new ArrayList<>();
		JSONObject obj = new JSONObject();
		map.put("in", 0L);
		map.put("out", 0L);
		map.put("lunchIn", 0L);
		map.put("lunchOut", 0L);
		map.put("nightIn", 0L);
		map.put("nightOut", 0L);
		map.put("totalHours", 0L);

		DateSheetExample exampleDate = new DateSheetExample();
		exampleDate.setOrderByClause("work_date");
		exampleDate.createCriteria().andEmpIdEqualTo(emp_id).andWorkDateBetween(minDate, maxDate);
		List<DateSheet> dateList = dateSheetMapper.selectByExample(exampleDate);

		if (dateList.size() > 0) {
			DateSheet dateSheetLocal = new DateSheet();
			for (int i = 0; i < dateList.size(); i++) {
				dateSheetLocal = dateList.get(i);
				TimeSheetExample exampleTime = new TimeSheetExample();
				exampleTime.createCriteria().andEmpIdEqualTo(emp_id).andWorkDateEqualTo(dateSheetLocal.getWorkDate());
				List<TimeSheet> timeList = timeSheetMapper.selectByExample(exampleTime);
				if (timeList.size() > 0) {
					TimeSheet timeSheetLocal = new TimeSheet();
					for (int j = 0; j < timeList.size(); j++) {
						timeSheetLocal = timeList.get(j);
						if (timeSheetLocal.getChunkId() == 1) {
							map.put("in", timeSheetLocal.getTimeIn());
							map.put("out", timeSheetLocal.getTimeOut());
						}
						if (timeSheetLocal.getChunkId() == 2) {
							map.put("lunchIn", timeSheetLocal.getTimeIn());
							map.put("lunchOut", timeSheetLocal.getTimeOut());
						}
						if (timeSheetLocal.getChunkId() == 3) {
							map.put("nightIn", timeSheetLocal.getTimeIn());
							map.put("nightOut", timeSheetLocal.getTimeOut());
						}
					}
				} else {
					jsonResService.errorResponse("time not found in time sheet table for emp id");
				}
				if (module.equalsIgnoreCase("excelExport")) {
					insertRow(dateSheetLocal, sheet, map, calculateTotalHours(map), workbook);
					map.clear();
				} else {
					obj = jsonResService.getTimeSheetJson(map, calculateTotalHours(map), dateSheetLocal.getWorkDesc(),
							dateSheetLocal.getWorkDate());
					jsonArray.add(obj);
				}
				jsonResService.setData(jsonArray);
			}
		} else {
			jsonResService.errorResponse("date not found in date sheet table for emp id");
		}
	}

	public void createSheetStructure(HSSFSheet sheet, HSSFWorkbook workbook, HashMap<String, String> empMap) {

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		//style.getDataFormat();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);

		HSSFRow aRow1 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
		aRow1.createCell(1).setCellValue("Name");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 6));
		aRow1.createCell(3).setCellValue(empMap.get("name"));
		HSSFRow aRow2 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
		aRow2.createCell(1).setCellValue("Month");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 6));
		aRow2.createCell(3).setCellValue(empMap.get("month"));
		HSSFRow aRow3 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
		aRow3.createCell(1).setCellValue("Year");
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 6));
		aRow3.createCell(3).setCellValue(empMap.get("year"));

		HSSFRow aRow4 = sheet.createRow(37);
		sheet.addMergedRegion(new CellRangeAddress(37, 37, 1, 6));
		aRow4.createCell(1).setCellValue("TOTAL HOURS");
		aRow4.createCell(7).setCellFormula("SUM(H6:H36)");
		aRow4.getCell(7).setCellStyle(style);

		// create header row
		HSSFRow header = sheet.createRow(4);
		header.createCell(0).setCellValue("Date");
		header.getCell(0).setCellStyle(style);
		header.createCell(1).setCellValue("IN");
		header.getCell(1).setCellStyle(style);
		header.createCell(2).setCellValue("OUT");
		header.getCell(2).setCellStyle(style);
		header.createCell(3).setCellValue("IN");
		header.getCell(3).setCellStyle(style);
		header.createCell(4).setCellValue("OUT");
		header.getCell(4).setCellStyle(style);
		header.createCell(5).setCellValue("IN");
		header.getCell(5).setCellStyle(style);
		header.createCell(6).setCellValue("OUT");
		header.getCell(6).setCellStyle(style);
		header.createCell(7).setCellValue("HRS.");
		header.getCell(7).setCellStyle(style);
		header.createCell(8).setCellValue("PROJECT");
		header.getCell(8).setCellStyle(style);

		// create style for row date
		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setAlignment(CellStyle.ALIGN_CENTER);

		// create style for row date
		CellStyle hourStyle = workbook.createCellStyle();
		hourStyle.setAlignment(CellStyle.ALIGN_RIGHT);

		for (int i = 5; i < 36; i++) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(i - 4);
			row.getCell(0).setCellStyle(dateStyle);
			row.createCell(7).setCellValue("0:00");
			row.getCell(7).setCellStyle(hourStyle);
		}
	}

	public String calculateTotalHours(HashMap<String, Long> map) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		TimeZone timezone = TimeZone.getTimeZone("UTC");
		formatter.setTimeZone(timezone);
		Long firstTime = 0L, secondTime = 0L, thirdTime = 0L;

		if (map.get("out") != null && map.get("in") != null) {
			firstTime = map.get("out") - map.get("in");
		}
		if (map.get("lunchIn") != null && map.get("lunchOut") != null) {
			secondTime = map.get("lunchOut") - map.get("lunchIn");
		}
		if (map.get("nightOut") != null && map.get("nightIn") != null) {
			thirdTime = map.get("nightOut") - map.get("nightIn");
		}

		Long totalHours = firstTime + thirdTime - secondTime;
		Date date = new Date(totalHours);
		String value = formatter.format(date);
		return value;
	}

	public String getEmpName(int empId) {
		employee = employeeMapper.selectByPrimaryKey(empId);		
		String fName = Character.toUpperCase(employee.getFirstName().charAt(0)) + employee.getFirstName().substring(1);
		String lName = Character.toUpperCase(employee.getLastName().charAt(0)) + employee.getLastName().substring(1);
		return fName + " " + lName;
	}
	
	public String getEmpEmail(int empId) {
		employee = employeeMapper.selectByPrimaryKey(empId);
		return employee.getEmail();
	}
}
