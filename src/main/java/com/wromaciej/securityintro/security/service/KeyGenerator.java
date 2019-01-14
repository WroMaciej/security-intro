package com.wromaciej.securityintro.security.service;

import com.wromaciej.securityintro.security.model.AsymmetricKeys;

public interface KeyGenerator {
	
	AsymmetricKeys getAssymetricKeys(int keyBitLength);

}
