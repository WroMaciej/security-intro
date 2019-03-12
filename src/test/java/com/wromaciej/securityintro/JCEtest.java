package com.wromaciej.securityintro;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class JCEtest {
	
	@Test
	public void shouldHaveUnlimitedKey() throws NoSuchAlgorithmException {
		
		int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
		System.out.println("Max Key Size for AES : " + maxKeySize);
	}

}
