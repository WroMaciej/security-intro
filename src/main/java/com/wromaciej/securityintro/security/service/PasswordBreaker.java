package com.wromaciej.securityintro.security.service;

import java.util.List;
import java.util.Optional;

public interface PasswordBreaker {
	
	String charsToBruteForce = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	List<String> findAllPossibilities( String hash, int passLengthLimit ) throws Exception;
	
	Optional<String> findFirst( String hash, int passLengthLimit );

}
