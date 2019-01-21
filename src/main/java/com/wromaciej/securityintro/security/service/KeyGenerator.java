package com.wromaciej.securityintro.security.service;

public interface KeyGenerator <T> {
	
	T getKey(int keyBitLength);

}
