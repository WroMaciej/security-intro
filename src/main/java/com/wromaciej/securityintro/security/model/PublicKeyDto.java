package com.wromaciej.securityintro.security.model;

import java.math.BigInteger;

public class PublicKeyDto {
	
	private final BigInteger publicKey;
	
	private final BigInteger modulus;

	public PublicKeyDto(BigInteger publicKey, BigInteger modulus) {
		super();
		this.publicKey = publicKey;
		this.modulus = modulus;
	}
	
	public PublicKeyDto(AsymmetricKeysDto asymmetricKeysDto) {
		super();
		this.publicKey = asymmetricKeysDto.getPublicKey();
		this.modulus = asymmetricKeysDto.getModulus();
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public BigInteger getModulus() {
		return modulus;
	}
	
	
	
	

}
