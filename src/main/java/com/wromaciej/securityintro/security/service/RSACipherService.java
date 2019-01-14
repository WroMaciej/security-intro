package com.wromaciej.securityintro.security.service;

import java.math.BigInteger;
import java.security.KeyPair;

import org.springframework.stereotype.Service;

import com.wromaciej.securityintro.security.model.AsymmetricKeys;

@Service
public final class RSACipherService<T, E> implements CipherService<T, E> {
	
	private final int keyBitLength;
	
	private KeyGenerator keyGenerator;

	private final BigInteger publicKey;
	private final BigInteger privateKey;
	private final BigInteger modulus;
	
	


	public RSACipherService(int keyBitLength) {
		super();
		this.keyBitLength = keyBitLength;
		AsymmetricKeys asymmetricKeys = keyGenerator.getAssymetricKeys(keyBitLength);
		publicKey = asymmetricKeys.getPublicKey();
		privateKey = asymmetricKeys.getPrivateKey();
		modulus = asymmetricKeys.getModulus();
	}

	@Override
	public E toEncrypted( T decrypted ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T toDecrypted( E encrypted ) {
		// TODO Auto-generated method stub
		return null;
	}

}
