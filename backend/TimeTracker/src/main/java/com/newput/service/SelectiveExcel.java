package com.newput.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newput.utility.TTUtil;

@Service
public class SelectiveExcel {

	@Autowired
	private TTUtil util;
	
	public void monthSheet(String monthName) {
		HashMap<String , Long> mapValue = util.getMonthlyDate(monthName);
		
	}

}
