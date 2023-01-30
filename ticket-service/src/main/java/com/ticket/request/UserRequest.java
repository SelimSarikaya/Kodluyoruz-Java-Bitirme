package com.ticket.request;

import com.ticket.model.enums.Sex;
import com.ticket.model.enums.UserType;

public class UserRequest {

	private String name;
	private String surname;
	private String password;
	private String email;
	private Sex sex;
	private UserType type;
	private String telNo;

	public UserRequest() {
		super();
	}

	public UserRequest(String name, String surname, String password, String email, Sex sex, UserType userType, String telNo) {
		super();
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.email = email;
		this.sex = sex;
		this.type = userType;
		this.telNo = telNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	
}
