package com.jaga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jaga.constants.SQLQueries;
import com.jaga.entity.EventDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EventDAO {
	private static final Logger LOG = LoggerFactory.getLogger(EventDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional
	public List<EventDTO> getAvailableSlotsForUser(String userId) {
		Query<EventDTO> query = getSession().createQuery(SQLQueries.GET_USER_AVAILABLE_SLOTS, EventDTO.class);
		return query.list();
	}

	@Transactional
	public List<EventDTO> getBookedSlotsForDean(String deanId) {
		Query<EventDTO> query = getSession().createQuery(SQLQueries.GET_DEAN_SLOTS, EventDTO.class);
		query.setParameter("DEAN_ID", deanId);
		return query.list();
	}

	@Transactional
	public List<EventDTO> getBookedSession(String userId, String userType) {
		if(userType.equals("dean")) {
			return getBookedSlotsForDean(userId);
		} else {
			return getAvailableSlotsForUser(userId);
		}
	}

	@Transactional
	public EventDTO makeBooking(EventDTO event) {
		EventDTO dto =getSession().merge(event);
		LOG.info("Slot successfully booked for user={}", event.getUserId());
		return dto;
	}

}
