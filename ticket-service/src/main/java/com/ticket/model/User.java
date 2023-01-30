package com.ticket.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ticket.model.enums.Sex;
import com.ticket.model.enums.UserType;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	private String name;
	private String surname;
	private String password;
	private String email;
	@Enumerated(EnumType.STRING)
	private Sex sex;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderlist", referencedColumnName = "id", nullable = true)
	private List<Order> orderList;
	@Enumerated(EnumType.STRING)
	private UserType type;
	private String telNo;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "roles", referencedColumnName = "id", nullable = true)
	private List<Role> roles;

	public User() {
		super();
	}

	public User(Sex sex, String name, String surname, String password, String email, UserType type, String telNo) {
		super();
		this.sex = sex;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.email = email;
		this.type = type;
		this.telNo = telNo;
	}

	public User(String name, String password, String email, List<Role> roles) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
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

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
