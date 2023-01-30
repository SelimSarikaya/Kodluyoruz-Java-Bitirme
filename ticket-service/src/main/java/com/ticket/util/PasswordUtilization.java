package com.ticket.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordUtilization {
	
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private PasswordUtilization() {
	}
	
	public static BCryptPasswordEncoder encoder() {
		return encoder;
	}

	public static String preparePasswordHash(String password) {
	    return encoder.encode(password);
	}

	public static boolean matchPassword(String password, String hash) {
	    return encoder.matches(password, hash);
	}

}
