package com.jaga.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.jaga.dao.EventDAO;
import com.jaga.entity.EventDTO;

@Component
public class EventService {
	private static final Logger LOG = LoggerFactory.getLogger(EventService.class);
	
	@Autowired
	private EventDAO eventDAO;
	
	public boolean isValidBookSlotRequest(EventDTO event) {
		if(!isValidEventDay(event.getEventDate())) {
			LOG.error("Given date={} is not a valid day to book slot.",event.getEventDate());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return true;
	}

	public EventDTO makeBooking(EventDTO event){
		return eventDAO.makeBooking(event);
	}
		
	public List<EventDTO> getBookedSession(String userId, String userType){
		return eventDAO.getBookedSession(userId, userType);
	}
	
	
	private boolean isValidEventDay(Date eventDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(eventDate);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY 
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			return true;
		}
		return false;
	}
	
}	

