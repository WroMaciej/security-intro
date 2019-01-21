package com.wromaciej.securityintro.security.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PrimeNumberGenerator {

	public BigInteger getMaximalPrime( int maxBits ) {
		BigInteger maxNumber = maxValueForGivenBits(maxBits);
		
		while (!isPrime(maxNumber)) {
			maxNumber.subtract(BigInteger.valueOf(2L));
		}
		
		return maxNumber;
	}
	
	
	
	public List<BigInteger> getTwoRandomBigPrimes( int maxBits){
		if (maxBits < 4) {
			throw new ArithmeticException("At least 4 bits expected.");
		}
		
		List<BigInteger> primes = new ArrayList<>();
		
		BigInteger upperPrime = getMaximalPrime(maxBits);
		BigInteger lowerPrime = upperPrime;
		int actualBitsForLowerPrime = maxBits - 1;
		
		while(lowerPrime.equals(upperPrime)) {
			lowerPrime = getMaximalPrime(actualBitsForLowerPrime);
			actualBitsForLowerPrime --;
		}
		primes.add(upperPrime);
		primes.add(lowerPrime);
		
		return primes;
	}
	
	public boolean isPrime(BigInteger number){
		return number.isProbablePrime(1);
	}

	public BigInteger maxValueForGivenBits( int bits ) {
		BigInteger maxNumber = BigInteger.ONE;
		for (int bit = 1; bit <= bits; bit++) {
			maxNumber = maxNumber.multiply(BigInteger.valueOf(2L));
		}
		return maxNumber.subtract(BigInteger.ONE);
	}

}
