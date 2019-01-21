package com.wromaciej.securityintro.security.model;

import java.math.BigInteger;

public class RSACipherDto extends CipherDto {

	private final BigInteger publicKey;
	private final BigInteger privateKey;
	private final BigInteger modulus;
	


	public RSACipherDto(String name, BigInteger publicKey,
			BigInteger privateKey, BigInteger modulus) {
		super(name);
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.modulus = modulus;
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}

	public BigInteger getModulus() {
		return modulus;
	}


	
	
	
	

}
