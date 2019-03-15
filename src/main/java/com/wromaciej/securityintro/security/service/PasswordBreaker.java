package com.wromaciej.securityintro.security.service;

import java.util.List;

public interface PasswordBreaker {
	
	String charsToBruteForce = "abcdefghijklmopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890";
	
	List<String> findAllPossibilities( String hash, int passLengthLimit ) throws Exception;

}
