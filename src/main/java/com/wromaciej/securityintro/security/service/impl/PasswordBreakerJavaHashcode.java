package com.wromaciej.securityintro.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wromaciej.securityintro.security.service.PasswordBreaker;

public class PasswordBreakerJavaHashcode implements PasswordBreaker {

	static final char firstChar = charsToBruteForce.charAt(0);
	static final char lastChar = charsToBruteForce.charAt(charsToBruteForce.length() - 1);

	private String getPassHash( String password ) {
		return String.valueOf(password.hashCode());
	}

	private char[] fullfillChars( char commonChar, int length ) {
		char array[] = new char[length];
		for (int i = 0; i < length; i++) {
			array[i] = commonChar;
		}
		return array;
	}

	private int charIndex( char ch ) {
		return this.charsToBruteForce.indexOf(ch);
	}

	private char charAt( int index ) {
		return this.charsToBruteForce.charAt(index);
	}

	private char nextChar( char currentChar ) {
		if (currentChar != lastChar) {
			return charAt(charIndex(currentChar) + 1);
		} else {
			return firstChar;
		}
	}

	private char[] nextPossibility( char[] currentArray ) throws Exception {

		char[] nextArray = currentArray;

		int testedCharIndex = 0;
		char occuredChar;

		while (testedCharIndex < currentArray.length) {
			occuredChar = currentArray[testedCharIndex];
			nextArray[testedCharIndex] = nextChar(occuredChar);
			if (occuredChar != lastChar) {
				break;
			} else {
				testedCharIndex++;
			}
		}

		if (testedCharIndex == currentArray.length) {
			throw new Exception("Current array is the last one possible.");
		}

		return nextArray;

	}

	private List<String> guessAllByBruteForce( String hash, int passLengthLimit ) throws Exception {
		char[] testedPass;
		int length;
		
		List<String> allCollisions = new ArrayList<>();

		for (length = 1; length <= passLengthLimit; length++) {
			testedPass = fullfillChars(charsToBruteForce.charAt(0), length);

			while (true) {
				String hashToTest = getPassHash(String.valueOf(testedPass));
				if (hash.equals(hashToTest)) {
					allCollisions.add(String.valueOf(testedPass));
				}
					try {
						testedPass = nextPossibility(testedPass);
					} catch (Exception e) {
						break;
					}
			}

		}
		
		if (allCollisions.size()==0) {
			throw new Exception("Cannot break password.");
		} else {
			return allCollisions;
		}
		
	}

	@Override
	public List<String> findAllPossibilities( String hash, int passLengthLimit ) throws Exception {
		return guessAllByBruteForce(hash, passLengthLimit);
	}

	@Override
	public Optional<String> findFirst( String hash, int passLengthLimit ) {
		char[] testedPass;
		int length;

		for (length = 1; length <= passLengthLimit; length++) {
			testedPass = fullfillChars(charsToBruteForce.charAt(0), length);

			while (true) {
				String hashToTest = getPassHash(String.valueOf(testedPass));
				if (hash.equals(hashToTest)) {
					return Optional.of(String.valueOf(testedPass));
				}
					try {
						testedPass = nextPossibility(testedPass);
					} catch (Exception e) {
						break;
					}
			}

		}
		
		return Optional.empty();
		
	}

}
