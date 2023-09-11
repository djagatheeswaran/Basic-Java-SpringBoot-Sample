package com.jaga.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jaga.entity.DeanDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class DeanDAO {
	private static final Logger LOG = LoggerFactory.getLogger(DeanDAO.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	@Transactional
	public List<DeanDTO> getAllDeans(){
		LOG.info("Getting all Dean Users from database");
	    return getSession().createQuery("SELECT a FROM DeanDTO a", DeanDTO.class).getResultList();      
	}

}
