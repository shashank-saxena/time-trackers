package com.newput.utility;

import java.io.File;
import java.io.FileOutputStream;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.domain.DateSheet;
import com.newput.domain.DateSheetExample;
import com.newput.domain.TimeSheet;
import com.newput.domain.TimeSheetExample;
import com.newput.mapper.DateSheetMapper;
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

	public void createExcelSheet(int emp_id, String monthName, String year) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Time_Sheet");

		sheet.setColumnWidth(8, 20000);
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(7, 3000);
		createSheetStructure(sheet, workbook);
		getTimeSheetData(sheet, emp_id, util.getMonthlyDate(monthName, year).get("minDate"),
				util.getMonthlyDate(monthName, year).get("maxDate"), "excelExport");

		rowCount = 5;

		try {
			FileOutputStream outStream = new FileOutputStream(new File("H:/timeSheet.xls"));
			workbook.write(outStream);
			outStream.close();
			jsonResService.setDataValue("timeSheet.xls file written successfully on disk.", "");
			jsonResService.successResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createSheetStructure(HSSFSheet sheet, HSSFWorkbook workbook) {

		HSSFRow aRow1 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
		aRow1.createCell(1).setCellValue("Name");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 6));
		aRow1.createCell(3).setCellValue("emp name");
		HSSFRow aRow2 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
		aRow2.createCell(1).setCellValue("Month");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 6));
		aRow2.createCell(3).setCellValue("emp month");
		HSSFRow aRow3 = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
		aRow3.createCell(1).setCellValue("Year");
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 6));
		aRow3.createCell(3).setCellValue("emp year");

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

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
		header.createCell(7).setCellValue("Total Hours");
		header.getCell(7).setCellStyle(style);
		header.createCell(8).setCellValue("Work Desc");
		header.getCell(8).setCellStyle(style);
	}

	public void getTimeSheetData(HSSFSheet sheet, int emp_id, Long minDate, Long maxDate, String module) {
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
					insertRow(dateSheetLocal, sheet, map, calculateTotalHours(map));
				} else {
					obj = jsonResService.getTimeSheetJson(map, calculateTotalHours(map), dateSheetLocal.getWorkDesc(),
							dateSheetLocal.getWorkDate());
					jsonArray.add(obj);
				}

			}
			jsonResService.setData(jsonArray);
		} else {
			jsonResService.errorResponse("date not found in date sheet table for emp id");
		}
	}

	int rowCount = 5;

	public void insertRow(DateSheet dateSheet, HSSFSheet sheet, HashMap<String, Long> map, String totalHours) {
		HSSFRow aRow = sheet.createRow(rowCount++);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = formatter.format(dateSheet.getWorkDate());
		// String formulaString = "C" + rowCount + "-B" + rowCount + "+E" +
		// rowCount + "-D" + rowCount + "+G" + rowCount
		// + "-F" + rowCount + "";
		aRow.createCell(0).setCellValue(dateString);
		aRow.createCell(1).setCellValue(util.timeHrs(map.get("in")));
		aRow.createCell(2).setCellValue(util.timeHrs(map.get("lunchIn")));
		aRow.createCell(3).setCellValue(util.timeHrs(map.get("lunchOut")));
		aRow.createCell(4).setCellValue(util.timeHrs(map.get("out")));
		aRow.createCell(5).setCellValue(util.timeHrs(map.get("nightIn")));
		aRow.createCell(6).setCellValue(util.timeHrs(map.get("nightOut")));
		aRow.createCell(7).setCellValue(totalHours);
		// aRow.createCell(7).setCellFormula(formulaString);
		aRow.createCell(8).setCellValue(dateSheet.getWorkDesc());
	}

	public String calculateTotalHours(HashMap<String, Long> map) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		TimeZone timezone = TimeZone.getTimeZone("UTC");
		formatter.setTimeZone(timezone);

		Long longIn = map.get("in");
		Long longOut = map.get("out");
		Long longLunchIn = map.get("lunchIn");
		Long longLunchOut = map.get("lunchOut");
		Long longNightIn = map.get("nightIn");
		Long longNightOut = map.get("nightOut");

		Long totalHours = (longLunchIn - longIn) + (longOut - longLunchOut) + (longNightOut - longNightIn);
		Date date = new Date(totalHours);
		String value = formatter.format(date);
		return value;
	}

}
