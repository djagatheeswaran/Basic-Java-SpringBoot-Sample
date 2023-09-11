package com.jaga.constants;

public class SQLQueries {
	public static final String GET_USER_TOEKN = "From TransactionDTO where loginId = :LOGIN_ID order by insertTime desc limit 1";
	public static final String GET_USER = "From UserDTO where userId = :LOGIN_ID and password = :PASSWORD";
	public static final String GET_DEAN = "From DeanDTO where deanId = :LOGIN_ID and password = :PASSWORD";
	public static final String GET_USER_BOOKED_SLOTS = "From EventDTO where eventDate > now() and userId = :USER_ID";
	public static final String GET_DEAN_SLOTS = "From EventDTO where eventDate > now() and deanId = :DEAN_ID and userId is not null";
	public static final String GET_USER_AVAILABLE_SLOTS = "From EventDTO where eventDate > now() and userId = null";
}
