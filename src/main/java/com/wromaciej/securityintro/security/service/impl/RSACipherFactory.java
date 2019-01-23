package com.wromaciej.securityintro.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wromaciej.securityintro.security.model.AsymmetricKeysDto;
import com.wromaciej.securityintro.security.model.RSAKeyDto;
import com.wromaciej.securityintro.security.service.KeyGeneratorService;

@Service
public class RSACipherFactory {

	private final KeyGeneratorService<RSAKeyDto> keyGeneratorService;

	public RSACipherFactory(
			@Autowired KeyGeneratorService<RSAKeyDto> keyGeneratorService) {
		super();
		this.keyGeneratorService = keyGeneratorService;
	}

	public RSAKeyDto createRSACipher( String cipherName, int keyBitLength ) {

		RSAKeyDto rsaKeyDto = keyGeneratorService.getRandomKey(keyBitLength);

		return new RSAKeyDto(cipherName, rsaKeyDto.getPublicKey(),
				rsaKeyDto.getPrivateKey(), rsaKeyDto.getModulus());

	}

}
