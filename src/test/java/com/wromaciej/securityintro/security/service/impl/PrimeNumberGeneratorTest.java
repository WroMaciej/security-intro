package com.wromaciej.securityintro.security.service.impl;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrimeNumberGeneratorTest {

	@Autowired
	PrimeNumberGenerator generator;

	@Test
	public void shouldGenerateJustPrimeNumbers() {
		// given
		// when
		List<BigInteger> primes = generator.getTwoRandomBigPrimes(16);
		// then
		assertThat(primes.stream().allMatch(number -> number.isProbablePrime(1)), is(true));
	}

	@Test
	public void shouldGenerateExactTwoNumbers() {
		// given
		// when
		List<BigInteger> primes = generator.getTwoRandomBigPrimes(16);
		// then
		assertThat(primes.size(), is(2));
	}

	@Test
	public void shouldGenerateRandomNumbers() {
		// given
		// when
		List<BigInteger> primes1 = generator.getTwoRandomBigPrimes(128);
		List<BigInteger> primes2 = generator.getTwoRandomBigPrimes(128);
		// then
		assertThat(primes1.containsAll(primes2), is(false));
	}

	@Test
	public void shouldGenerateRandomNumbersFor8Bits() {
		// given
		// when
		List<BigInteger> primes = generator.getTwoRandomBigPrimes(8);
		// then
		assertThat(primes.stream().allMatch(number -> number.isProbablePrime(1)), is(true));
	}
	
	@Test
	(expected = ArithmeticException.class)
	public void shouldThrowExceptionForLessThan8Bits() {
		// given
		// when
		List<BigInteger> primes = generator.getTwoRandomBigPrimes(7);
		// then
		//throw exception
	}
}
