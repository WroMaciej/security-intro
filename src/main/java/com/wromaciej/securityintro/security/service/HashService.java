package com.wromaciej.securityintro.security.service;

public interface HashService <T> {
	
	public String hash(T toHash);

}
