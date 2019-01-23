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
		System.out.println("modulus: " + modulus + " phi: " + phiFunction);

		BigInteger publicKey;
		do {
			publicKey = generateRandomOddNumber(BigInteger.ONE, modulus);
		} while (!publicKey.gcd(phiFunction).equals(BigInteger.ONE));

		System.out.println("pk calculated");
		return publicKey;

	}

	private BigInteger generatePrivateKey( BigInteger publicKey, BigInteger phiFunction ) {
		System.out.println("pk: " + publicKey + " phi: " + phiFunction);

		BigInteger privateKey = BigInteger.ZERO;
		BigInteger u = BigInteger.ONE;
		BigInteger w = publicKey;
		BigInteger z = phiFunction;

		BigInteger multiplification;

		BigInteger temp;

		do {
			if (w.compareTo(z) < 0) {
				temp = new BigInteger(privateKey.toString());
				privateKey = new BigInteger(u.toString());
				u = new BigInteger(temp.toString());

				temp = new BigInteger(w.toString());
				w = new BigInteger(z.toString());
				z = new BigInteger(temp.toString());
			}
			multiplification = w.divide(z);

			u = u.subtract(multiplification.multiply(privateKey));

			w = w.subtract(multiplification.multiply(z));

		} while (!w.equals(BigInteger.ZERO));
		
		if (!z.equals(BigInteger.ONE)) {
			throw new ArithmeticException(
					"Wrong public key or phi function to private key determine.");
		}
		
		if (privateKey.compareTo(BigInteger.ZERO) < 0) {
			privateKey = privateKey.add(phiFunction);
		}
		

		System.out.println("privKey calculated");
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
