package com.jaga.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventDTOList {
	@JsonProperty
	List<EventDTO> eventsList;
	
	@JsonGetter("events")
	public List<EventDTO> getEventsList(){
		if(eventsList == null) {
			return new ArrayList<EventDTO>();
		}
		return eventsList;
	}

	public void setEventsList(List<EventDTO> eventsList) {
		this.eventsList = eventsList;
	}
	

}
