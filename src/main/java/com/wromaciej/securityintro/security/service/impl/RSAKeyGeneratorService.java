package com.wromaciej.securityintro.security.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wromaciej.securityintro.security.model.RSAKeyDto;
import com.wromaciej.securityintro.security.service.KeyGeneratorService;

@Service
public class RSAKeyGeneratorService implements KeyGeneratorService<RSAKeyDto> {

	private final PrimeNumberGenerator primeNumberGenerator;

	public RSAKeyGeneratorService(@Autowired PrimeNumberGenerator primeNumberGenerator) {
		super();
		this.primeNumberGenerator = primeNumberGenerator;
	}

	@Override
	public RSAKeyDto getRandomKey( int keyBitLength ) {
		List<BigInteger> primes = new ArrayList<>();

		try {
			primes = primeNumberGenerator.getTwoRandomBigPrimes(keyBitLength / 2);
		} catch (ArithmeticException e) {
			throw new SecurityException("Too short key.");
		}

		BigInteger prime1 = primes.get(0);
		BigInteger prime2 = primes.get(1);

		BigInteger phiFunction = prime1.subtract(BigInteger.ONE)
				.multiply(prime2.subtract(BigInteger.ONE));
		BigInteger modulus = prime1.multiply(prime2);

		BigInteger publicKey = generatePublicKey(modulus, phiFunction);

		BigInteger privateKey = generatePrivateKey(publicKey, phiFunction);

		return new RSAKeyDto("RSA", publicKey, privateKey, modulus);
	}

	private BigInteger generatePublicKey( BigInteger modulus, BigInteger phiFunction ) {

		BigInteger publicKey;
		do {
			publicKey = generateRandomOddNumber(BigInteger.ONE, modulus);
		} while (!publicKey.gcd(phiFunction).equals(BigInteger.ONE));

		return publicKey;
	}

	private BigInteger generatePrivateKey( BigInteger publicKey, BigInteger phiFunction ) {

		BigInteger privateKey = BigInteger.ZERO;
		BigInteger parameter1 = BigInteger.ONE;
		BigInteger parameter2 = publicKey;
		BigInteger parameter3 = phiFunction;

		BigInteger multiplification;

		BigInteger temporaryNumber;

		do {
			if (parameter2.compareTo(parameter3) < 0) {
				temporaryNumber = new BigInteger(privateKey.toString());
				privateKey = new BigInteger(parameter1.toString());
				parameter1 = new BigInteger(temporaryNumber.toString());

				temporaryNumber = new BigInteger(parameter2.toString());
				parameter2 = new BigInteger(parameter3.toString());
				parameter3 = new BigInteger(temporaryNumber.toString());
			}
			multiplification = parameter2.divide(parameter3);
			parameter1 = parameter1.subtract(multiplification.multiply(privateKey));
			parameter2 = parameter2.subtract(multiplification.multiply(parameter3));

		} while (!parameter2.equals(BigInteger.ZERO));

		if (!parameter3.equals(BigInteger.ONE)) {
			throw new ArithmeticException(
					"Wrong public key or phi function to private key determine.");
		}

		if (privateKey.compareTo(BigInteger.ZERO) < 0) {
			privateKey = privateKey.add(phiFunction);
		}

		return privateKey;

	}

	private BigInteger generateRandomOddNumber( BigInteger moreThan, BigInteger lessThan ) {
		Random random = new Random();
		BigInteger randomNumber;

		BigInteger range = lessThan.subtract(moreThan).subtract(BigInteger.ONE)
				.subtract(BigInteger.valueOf(2L));
		BigInteger minimum = moreThan.add(BigInteger.ONE);

		BigDecimal randomDouble;
		BigDecimal randomToAdd;

		do {
			randomDouble = new BigDecimal(random.nextDouble());
			randomToAdd = new BigDecimal(range).multiply(randomDouble);

			randomNumber = minimum.add(randomToAdd.toBigInteger());

		} while (randomNumber.mod(BigInteger.valueOf(2L)).equals(BigInteger.valueOf(0L)));
		

		
		return randomNumber;
	}

}
