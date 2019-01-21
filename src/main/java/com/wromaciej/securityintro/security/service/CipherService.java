package com.wromaciej.securityintro.security.service;

import com.wromaciej.securityintro.security.model.CipherDto;

public interface CipherService <T, E> {
	
	E toEncrypted(CipherDto cipherDto, T decrypted);
	
	T toDecrypted(CipherDto cipherDto, E encrypted);

}
