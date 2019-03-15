package com.wromaciej.securityintro.security.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.wromaciej.securityintro.security.service.PasswordBreaker;

public class PasswordBreakerJavaHashcodeTest {
	
	@Test(timeout=2000)
	public void shouldBreakSimplePasswordQuickly() throws Exception {
		//given
		char[] rawPassword = {'p','a','s','s'};
		int maxLength = 4;
		String passwordHash = String.valueOf(String.valueOf(rawPassword).hashCode());
		PasswordBreaker passwordBreaker = new PasswordBreakerJavaHashcode();
		//when
		List<String> guessed = passwordBreaker.findAllPossibilities(passwordHash, maxLength);
		//then
		assertThat(guessed, Matchers.hasItem(String.valueOf(rawPassword)));
	}
	
	@Test
	public void shouldNotBreakPasswordOverGivenLimit() {
		//given
		char[] rawPassword = {'p','a','s','s'};
		int maxLength = 3;
		String passwordHash = String.valueOf(String.valueOf(rawPassword).hashCode());
		PasswordBreaker passwordBreaker = new PasswordBreakerJavaHashcode();
		//when
		List<String> guessed = new ArrayList<>();
		boolean anyFound = true;
		try {
			guessed = passwordBreaker.findAllPossibilities(passwordHash, maxLength);
		} catch (Exception e) {
			anyFound = false;
		}
		//then
		assertFalse(anyFound);
	}

}
