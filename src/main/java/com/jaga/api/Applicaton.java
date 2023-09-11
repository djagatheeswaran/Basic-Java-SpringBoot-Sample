package com.jaga.api;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jaga.entity.DeanDTO;
import com.jaga.entity.EventDTO;
import com.jaga.entity.TransactionDTO;
import com.jaga.service.DeanService;
import com.jaga.service.EventService;
import com.jaga.service.TransactionService;

@RestController
public class Applicaton {
	private static final Logger LOG = LoggerFactory.getLogger(Applicaton.class);

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private EventService eventService;
	
	@Autowired
	private DeanService deanService;
	
	@PostMapping("/login")
	public String login(@RequestParam Map<String, String> requestParams) {
		LOG.info("Login action initiated...");
		String userId = requestParams.get("user-id");
		String password = requestParams.get("password");
		String type = requestParams.get("type");
		LOG.info("Username: {} Password: {} type:{}", userId, password, type);

		// Validate user credentials		
		if(!(transactionService.isValidCredentials(userId,password,type))) {
			LOG.info("Invalid credentials entered");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		LOG.info("User credentials validated successfully.");
		String randomId = UUID.randomUUID().toString();
		TransactionDTO transaction = new TransactionDTO();
		transaction.setInsertTime(new Date());
		transaction.setLoginId(userId);
		transaction.setToken(randomId);
		transactionService.insertTransactions(transaction);
		LOG.info("UUID {} generated and inserted to Transaction table successfully.",randomId);
		transaction = transactionService.getTransaction(userId);
		LOG.info("Token successfully fetched from database for user_id={} token={}", userId, transaction.getToken());
		return randomId;
	}

	@GetMapping("/slots")
	public ResponseEntity<List<EventDTO>> showSlots(@RequestParam Map<String, String> requestParams,
			@RequestHeader Map<String,String> headers) {
		// Validate the given Bearer token with user-id
		String userId = requestParams.get("user-id");
		String authToken = headers.get("authorization");
		String userType = requestParams.get("type");
		if(!transactionService.isValidBearerToken(userId, authToken)) {
			LOG.error("Given user-id={} authToken={} are not valid",userId, authToken);
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		LOG.info("User={} Autht token is valid. Getting available slots for the user",userId);
		return new ResponseEntity<List<EventDTO>>(eventService.getBookedSession(userId, userType), HttpStatus.OK);
	}
	
	@GetMapping("/dean")
	public ResponseEntity<List<DeanDTO>> getDeans(@RequestParam Map<String, String> requestParams,
			@RequestHeader Map<String,String> headers) {
        return new ResponseEntity<List<DeanDTO>>(deanService.getAllDeans(),HttpStatus.OK);
	}
	
	
	@PutMapping("/bookslot")
	public ResponseEntity<EventDTO> bookSlot(@RequestParam Map<String, String> requestParams,
						   @RequestHeader Map<String,String> headers,
						   @RequestBody EventDTO event) {
		String userId = event.getUserId();		
		String authToken = headers.get("authorization");
		if(!transactionService.isValidBearerToken(userId, authToken)) {
			LOG.error("Given user-id={} authToken={} are not valid",userId, authToken);
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		LOG.info("User={} Auth token is valid. Proceeding with slot booking for the user");
		eventService.isValidBookSlotRequest(event);
		
		// Insert booking details to Event table
		return new ResponseEntity<EventDTO>(eventService.makeBooking(event),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public String homePage() {
		LOG.info("Started loading Homepage...");
		return "index";
	}
}
