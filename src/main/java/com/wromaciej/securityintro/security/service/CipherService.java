package com.wromaciej.securityintro.security.service;

import com.wromaciej.securityintro.security.model.KeyDto;

public interface CipherService <T, E> {
	
	E toEncrypted(KeyDto keyDto, T decrypted);
	
	T toDecrypted(KeyDto keyDto, E encrypted);

}
