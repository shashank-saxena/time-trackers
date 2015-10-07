package com.newput.domain;

import java.io.Serializable;

public class Session implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column session.id
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column session.emp_id
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	private Integer empId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column session.emp_name
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	private String empName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column session.token
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	private String token;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column session.expires_when
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	private Long expiresWhen;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column session.created
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	private Long created;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column session.updated
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	private Long updated;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table session
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column session.id
	 * @return  the value of session.id
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column session.id
	 * @param id  the value for session.id
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column session.emp_id
	 * @return  the value of session.emp_id
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column session.emp_id
	 * @param empId  the value for session.emp_id
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column session.emp_name
	 * @return  the value of session.emp_name
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column session.emp_name
	 * @param empName  the value for session.emp_name
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column session.token
	 * @return  the value of session.token
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public String getToken() {
		return token;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column session.token
	 * @param token  the value for session.token
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column session.expires_when
	 * @return  the value of session.expires_when
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public Long getExpiresWhen() {
		return expiresWhen;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column session.expires_when
	 * @param expiresWhen  the value for session.expires_when
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public void setExpiresWhen(Long expiresWhen) {
		this.expiresWhen = expiresWhen;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column session.created
	 * @return  the value of session.created
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public Long getCreated() {
		return created;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column session.created
	 * @param created  the value for session.created
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public void setCreated(Long created) {
		this.created = created;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column session.updated
	 * @return  the value of session.updated
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public Long getUpdated() {
		return updated;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column session.updated
	 * @param updated  the value for session.updated
	 * @mbggenerated  Wed Oct 07 14:48:54 IST 2015
	 */
	public void setUpdated(Long updated) {
		this.updated = updated;
	}
}