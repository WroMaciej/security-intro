package com.wromaciej.securityintro.security.model;

import java.math.BigInteger;

public class AsymmetricKeys {
	
	private final BigInteger publicKey;
	
	private final BigInteger privateKey;
	
	private final BigInteger modulus;
	
	

	

	public AsymmetricKeys(BigInteger publicKey, BigInteger privateKey,
			BigInteger modulus) {
		super();
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
