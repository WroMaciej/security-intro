package com.wromaciej.securityintro.security.service;

import java.io.ObjectInputStream.GetField;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wromaciej.securityintro.security.model.AsymmetricKeysDto;

@Service
public class AsymmetricKeyGeneratorService implements KeyGeneratorService<AsymmetricKeysDto> {
	
	private final PrimeNumberGenerator primeNumberGenerator;
	
	

	public AsymmetricKeyGeneratorService(@Autowired PrimeNumberGenerator primeNumberGenerator) {
		super();
		this.primeNumberGenerator = primeNumberGenerator;
	}



	@Override
	public AsymmetricKeysDto getRandomKey( int keyBitLength ) {
		List<BigInteger> primes = primeNumberGenerator.getTwoRandomBigPrimes(keyBitLength);
		
		BigInteger prime1 = primes.get(0);
		BigInteger prime2 = primes.get(1);
		
		BigInteger phiFunction = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));
		BigInteger modulus = prime1.multiply(prime2);
		
		BigInteger publicKey = getRandomCoprimeNumber(1, modulus);
		BigInteger privateKey;
		
	}
	
	private BigInteger getRandomCoprimeNumber(BigInteger moreThan, BigInteger lessThan ) {
		return 0;
	}

}
