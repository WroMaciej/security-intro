package com.wromaciej.securityintro;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

public class JCEtest {

	@Test
	public void shouldHaveUnlimitedKey() throws NoSuchAlgorithmException {
		// given data in JDK
		// when
		int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
		// then
		assertThat(maxKeySize, greaterThan(1024));

	}



}
