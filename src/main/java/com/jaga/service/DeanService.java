package com.jaga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaga.dao.DeanDAO;
import com.jaga.entity.DeanDTO;

@Component
public class DeanService {
	@Autowired
	private DeanDAO deanDAO;
	
	public List<DeanDTO> getAllDeans(){
		return deanDAO.getAllDeans();
	}
}
