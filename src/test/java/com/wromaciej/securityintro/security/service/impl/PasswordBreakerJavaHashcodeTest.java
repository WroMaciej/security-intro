package com.wromaciej.securityintro.security.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.function.Predicate;

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
		// then
		assertThat(guessed.get(), Matchers.is(String.valueOf(rawPassword)));
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

	static class MutableInt implements Observer {
		int value;

		public MutableInt(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "MutableInt [value=" + value + "]";
		}

		@Override
		public void update( Observable o, Object arg ) {
			int oldValue = value;
			int newValue = (int) arg;
			value = newValue;
		}

	}

	static class MutablesChanger extends Observable {
		public void changeAllValues( int commonValue ) {
			setChanged();
			notifyObservers(commonValue);
		}
	}

	@Test
	public void shouldChangeAllObservers() {
		// given
		MutableInt mutableInt1 = new MutableInt(1);
		MutableInt mutableInt2 = new MutableInt(2);
		MutableInt mutableInt3 = new MutableInt(3);
		List<MutableInt> list = new ArrayList<>();
		list.add(mutableInt1);
		list.add(mutableInt2);
		list.add(mutableInt3);
		MutablesChanger mutablesChanger = new MutablesChanger();
		list.forEach(mutableInt -> mutablesChanger.addObserver(mutableInt));
		// when
		final Integer commonValue = Integer.valueOf(99);
		mutablesChanger.changeAllValues(commonValue);
		// then
		Predicate<MutableInt> hasCommonValue = 
				mutable -> mutable.value == commonValue.intValue();
		assertTrue(list.stream().allMatch(hasCommonValue));
	}

}