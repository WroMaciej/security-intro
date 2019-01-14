package com.wromaciej.securityintro.security.service;

public interface CipherService <T, E> {
	
	E toEncrypted(T decrypted);
	
	T toDecrypted(E encrypted);

}
