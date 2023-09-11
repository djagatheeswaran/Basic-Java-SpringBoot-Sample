package com.jaga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.jaga.constants.SQLQueries;
import com.jaga.entity.DeanDTO;
import com.jaga.entity.TransactionDTO;
import com.jaga.entity.UserDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TransactionsDAO {
	private static final Logger LOG = LoggerFactory.getLogger(TransactionsDAO.class);

	// @Autowired
	// private SessionFactory sessionFactory;
	// Getting Spring boot hibernate no transaction
	// is in progress. Hence not using SessionFactory

	@PersistenceContext
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional
	public boolean insertTransaction(TransactionDTO transactions) {
		getSession().persist(transactions);
		return true;
	}
	
	@Transactional
	public TransactionDTO getTransaction(String loginId) {
		Query<TransactionDTO> query = getSession().createQuery(SQLQueries.GET_USER_TOEKN, TransactionDTO.class);
		query.setParameter("LOGIN_ID", loginId);
		List<TransactionDTO> transactions = query.list();
		if(transactions.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return query.list().get(0);
	}
	
	@Transactional
	public boolean isValidUserCredentials(String userId, String password) {
		Query<UserDTO> query = getSession().createQuery(SQLQueries.GET_USER, UserDTO.class);
		query.setParameter("LOGIN_ID", userId);
		query.setParameter("PASSWORD", password);
		final List<UserDTO> respList = query.list();
		if(respList == null) {
			return false;
		} else if(respList.size() == 1) {
			return true;
		} else {
			LOG.error("Error while validating User credentials");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Transactional
	public boolean isValidDeanCredentials(String deanId, String password) {
		Query<DeanDTO> query = getSession().createQuery(SQLQueries.GET_DEAN, DeanDTO.class);
		query.setParameter("LOGIN_ID", deanId);
		query.setParameter("PASSWORD", password);
		final List<DeanDTO> respList = query.list();
		if(respList == null) {
			return false;
		} else if(respList.size() == 1) {
			return true;
		} else {
			LOG.error("Error while validating Dean credentials");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}
}
