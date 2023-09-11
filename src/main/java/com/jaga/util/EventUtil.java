package com.jaga.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaga.dao.DeanDAO;
import com.jaga.entity.DeanDTO;
import com.jaga.entity.EventDTO;

@Component
public class EventUtil {
	@Autowired
	private DeanDAO deanDAO;

	public List<EventDTO> getDefaultAvailableSlots() {
		List<EventDTO> slots = new ArrayList<>();
		List<String> deanNames = deanDAO.getAllDeans()
				.stream().map(DeanDTO::getDeanId).collect(Collectors.toList());

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().length(startDate.isLeapYear()));
		for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
			if (date.getDayOfWeek() == DayOfWeek.THURSDAY || date.getDayOfWeek() == DayOfWeek.FRIDAY) {
				for (String name : deanNames) {
					EventDTO event = new EventDTO();
					event.setDeanId(name);
					Date eventDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
					event.setEventDate(eventDate);
					slots.add(event);
				}
			}
		}
		return slots;
	}

	public LocalDate convertToLocalDate(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static boolean isSameDate(Date first, Date second) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(first);
		cal2.setTime(second);
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	}
}
