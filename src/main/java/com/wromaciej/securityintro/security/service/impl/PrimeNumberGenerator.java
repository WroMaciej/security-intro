package com.wromaciej.securityintro.security.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class PrimeNumberGenerator {
	
	private static final int MINIMUM_BITS = 8;
	private static final int BITS_DIFFERENCE = 3;
	
	public List<BigInteger> getTwoRandomBigPrimes( int maxBits ) {
		if (maxBits < MINIMUM_BITS) {
			throw new ArithmeticException("At least " + MINIMUM_BITS + " bits expected.");
		}
		List<BigInteger> primes = new ArrayList<>();
		BigInteger upperPrime = getDifferentRandomPrime(null, maxBits - BITS_DIFFERENCE, maxBits);
		BigInteger lowerPrime = getDifferentRandomPrime(upperPrime, maxBits - 2*BITS_DIFFERENCE, maxBits - BITS_DIFFERENCE);
		primes.add(upperPrime);
		primes.add(lowerPrime);

		return primes;
	}


	private BigInteger getDifferentRandomPrime( BigInteger differentThan, int minBits, int maxBits ) {
		BigInteger maxNumber = maxValueForGivenBits(maxBits);
		BigInteger minNumber = maxValueForGivenBits(minBits);
		BigInteger range = maxNumber.subtract(minNumber);
		
		BigInteger randomNumber;
		Random random = new Random();
		BigDecimal randomToAdd;
		BigDecimal randomDouble;
		
		do {
			randomDouble = new BigDecimal(random.nextDouble());
			randomToAdd = new BigDecimal(range).multiply(randomDouble);
			randomNumber = minNumber.add(randomToAdd.toBigInteger());
			
		} while (randomNumber.equals(differentThan) || !randomNumber.isProbablePrime(1));

		return randomNumber;
	}

	
	public boolean isPrime( BigInteger number ) {
		return number.isProbablePrime(1);
	}

	private BigInteger maxValueForGivenBits( int bits ) {
		BigInteger maxNumber = BigInteger.ONE;
		for (int bit = 1; bit <= bits; bit++) {
			maxNumber = maxNumber.multiply(BigInteger.valueOf(2L));
		}
		return maxNumber.subtract(BigInteger.ONE);
	}

}
