package com.wromaciej.securityintro.security.service;

import java.math.BigInteger;

public interface DecryptionService <T, E> {
	
	public T toDecrypted (E encrypted, BigInteger publicKey, BigInteger privateKey )

}
