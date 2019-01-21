package com.wromaciej.securityintro.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wromaciej.securityintro.security.model.AsymmetricKeysDto;
import com.wromaciej.securityintro.security.model.CipherDto;

@Service
public final class RSACipherService<T, E> implements CipherService<T, E> {
	

	
	private final KeyGenerator<AsymmetricKeysDto> keyGenerator;


	
	


	public RSACipherService(@Autowired KeyGenerator<AsymmetricKeysDto> keyGenerator) {
		super();
		this.keyGenerator = keyGenerator;

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
