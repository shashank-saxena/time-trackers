package com.newput.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.newput.domain.DateSheet;
import com.newput.domain.DateSheetExample;

public interface DateSheetMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */
	int countByExample(DateSheetExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */
	int deleteByExample(DateSheetExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */
	int insert(DateSheet record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */
	int insertSelective(DateSheet record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */List<DateSheet> selectByExample(DateSheetExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */
	DateSheet selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */int updateByExampleSelective(@Param("record") DateSheet record,@Param("example") DateSheetExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */int updateByExample(@Param("record") DateSheet record,@Param("example") DateSheetExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */
	int updateByPrimaryKeySelective(DateSheet record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table date_sheet
	 * @mbggenerated  Mon Oct 12 13:12:27 IST 2015
	 */
//	int updateByPrimaryKey(DateSheet record);
	List<DateSheet> updateByPrimaryKey(DateSheetExample example);
}