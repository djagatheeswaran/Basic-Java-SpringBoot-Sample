package com.jaga.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="EVENTS")
@JsonPropertyOrder({ "eventId", "eventDate","userId", "deanId" })

public class EventDTO {
	@Id
	@Column(name="EVENT_ID")
	@JsonProperty
	String eventId;
	
	@Column(name="EVENT_DATE")		
	@JsonProperty
	Date eventDate;

	@Column(name="DEAN_ID")	
	@JsonProperty
	String deanId;
	
	@Column(name="USER_ID")	
	@JsonProperty
	String userId;

	@JsonGetter("eventId")
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	@JsonGetter("eventDate")
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	@JsonGetter("deanId")
	public String getDeanId() {
		return deanId;
	}

	public void setDeanId(String deanId) {
		this.deanId = deanId;
	}

	@JsonGetter("userId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	
}
