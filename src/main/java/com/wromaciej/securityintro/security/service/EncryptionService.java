package com.wromaciej.securityintro.security.service;

import java.math.BigInteger;

public interface EncryptionService <T, E> {
	
	public E toEncrypted(T decrypted, BigInteger publicKey);

}
