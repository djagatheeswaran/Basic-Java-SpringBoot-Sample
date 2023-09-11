package com.jaga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.jaga.dao.TransactionsDAO;
import com.jaga.entity.TransactionDTO;

@Component
public class TransactionService {
	@Autowired
	private TransactionsDAO transactionsDAO;

	public boolean insertTransactions(TransactionDTO transaction) {
		return transactionsDAO.insertTransaction(transaction);
	}

	public TransactionDTO getTransaction(String loginId) {
		return transactionsDAO.getTransaction(loginId);
	}

	public boolean isValidCredentials(String loginId, String password, String type) {
		if (type.equalsIgnoreCase("USER")) {
			return transactionsDAO.isValidUserCredentials(loginId, password);
		} else if (type.equalsIgnoreCase("DEAN")) {
			return transactionsDAO.isValidDeanCredentials(loginId, password);
		}
		return false;
	}
	
	public boolean isValidBearerToken(String loginId, String authHeader) {
		String token = authHeader;
		TransactionDTO dto = transactionsDAO.getTransaction(loginId);
		if(dto == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		if (authHeader.startsWith("Bearer ")){
		     token = authHeader.substring(7);
		}
		if(dto.getToken().equals(token)) {
			return true;
		} else {
			return false;
		}
	}

}
