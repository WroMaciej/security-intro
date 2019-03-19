package com.wromaciej.securityintro.security.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.wromaciej.securityintro.security.service.PasswordBreaker;

public class PasswordBreakerJavaHashcodeTest {

	@Test(timeout = 4000)
	public void shouldBreakSimplePasswordQuickly() throws Exception {
		// given
		char[] rawPassword = { 'p', 'a', 's', 's' };
		int maxLength = 4;
		String passwordHash = String.valueOf(String.valueOf(rawPassword).hashCode());
		PasswordBreaker passwordBreaker = new PasswordBreakerJavaHashcode();
		// when
		List<String> guessed = passwordBreaker.findAllPossibilities(passwordHash, maxLength);
		// then
		assertThat(guessed, Matchers.hasItem(String.valueOf(rawPassword)));
	}

	@Test(timeout = 4000)
	public void shouldFindFirstMatch() throws Exception {
		// given
		char[] rawPassword = { 'p', 'a', 's', 's' };
		int maxLength = 4;
		String passwordHash = String.valueOf(String.valueOf(rawPassword).hashCode());
		PasswordBreaker passwordBreaker = new PasswordBreakerJavaHashcode();
		// when
		Optional<String> guessed = passwordBreaker.findFirst(passwordHash, maxLength);
		List<String> allPossibilities = passwordBreaker.findAllPossibilities(passwordHash, maxLength);
		// then
		assertThat(guessed.get(), Matchers.is(String.valueOf(rawPassword)));
		assertThat(allPossibilities, Matchers.hasItem(String.valueOf(rawPassword)));
		assertThat(allPossibilities.size(), Matchers.greaterThan(1));
	}

	@Test
	public void shouldNotBreakPasswordOverGivenLimit() {
		// given
		char[] rawPassword = { 'p', 'a', 's', 's' };
		int maxLength = 3;
		String passwordHash = String.valueOf(String.valueOf(rawPassword).hashCode());
		PasswordBreaker passwordBreaker = new PasswordBreakerJavaHashcode();
		// when
		List<String> guessed = new ArrayList<>();
		boolean anyFound = true;
		try {
			guessed = passwordBreaker.findAllPossibilities(passwordHash, maxLength);
		} catch (Exception e) {
			anyFound = false;
		}
		// then
		assertFalse(anyFound);
	}

	@Test(timeout = 3000)
	public void shouldBreakPassWithDigitsOnly() throws Exception {
		// given
		char[] rawPassword = { '1', '2', '3' };
		int maxLength = 4;
		String passwordHash = String.valueOf(String.valueOf(rawPassword).hashCode());
		PasswordBreaker passwordBreaker = new PasswordBreakerJavaHashcode();
		// when
		List<String> guessed = passwordBreaker.findAllPossibilities(passwordHash, maxLength);
		// then
		assertThat(guessed, Matchers.hasItem(String.valueOf(rawPassword)));
	}



}
