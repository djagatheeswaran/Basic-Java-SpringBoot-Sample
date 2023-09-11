package com.jaga.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="DEAN")
@JsonPropertyOrder({ "deanId", "name","email", "mobile" })
public class DeanDTO {
	
	@Id
	@Column(name="DEAN_ID")
	@JsonProperty
	String deanId;
	
	@Column(name="PASSWORD")
	@JsonIgnore
	String password;

	@Column(name="NAME")
	@JsonProperty
	String name;
		
	@Column(name="EMAIL")
	@JsonProperty
	String email;
	
	@Column(name="MOBILE")
	@JsonProperty
	int mobile;

	@JsonGetter("deanId")
	public String getDeanId() {
		return deanId;
	}

	public void setDeanId(String deanId) {
		this.deanId = deanId;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonGetter("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonGetter("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonGetter("mobile")
	public int getMobile() {
		return mobile;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}
}
	