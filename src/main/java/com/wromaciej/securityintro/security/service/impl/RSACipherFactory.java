package com.wromaciej.securityintro.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wromaciej.securityintro.security.model.AsymmetricKeysDto;
import com.wromaciej.securityintro.security.model.RSACipherDto;
import com.wromaciej.securityintro.security.service.KeyGeneratorService;

@Service
public class RSACipherFactory {

	private final KeyGeneratorService<AsymmetricKeysDto> keyGeneratorService;

	public RSACipherFactory(
			@Autowired KeyGeneratorService<AsymmetricKeysDto> keyGeneratorService) {
		super();
		this.keyGeneratorService = keyGeneratorService;
	}

	public RSACipherDto createRSACipher( String cipherName, int keyBitLength ) {

		AsymmetricKeysDto asymmetricKeysDto = keyGeneratorService.getRandomKey(keyBitLength);

		return new RSACipherDto(cipherName, asymmetricKeysDto.getPublicKey(),
				asymmetricKeysDto.getPrivateKey(), asymmetricKeysDto.getModulus());

	}

}
