package com.wromaciej.securityintro.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wromaciej.securityintro.security.model.AsymmetricKeysDto;
import com.wromaciej.securityintro.security.model.CipherDto;
import com.wromaciej.securityintro.security.service.CipherService;
import com.wromaciej.securityintro.security.service.KeyGeneratorService;

@Service
public final class RSACipherService<T, E> implements CipherService<T, E> {

	private final KeyGeneratorService<AsymmetricKeysDto> keyGeneratorService;

	public RSACipherService(
			@Autowired KeyGeneratorService<AsymmetricKeysDto> keyGenerator) {
		super();
		this.keyGeneratorService = keyGenerator;
	}
	

	@Override
	public E toEncrypted( CipherDto cipherDto, T decrypted ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T toDecrypted( CipherDto cipherDto, E encrypted ) {
		// TODO Auto-generated method stub
		return null;
	}

}
