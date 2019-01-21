package com.wromaciej.securityintro.security.service;

public interface KeyGeneratorService <T> {
	
	T getRandomKey(int keyBitLength);

}
