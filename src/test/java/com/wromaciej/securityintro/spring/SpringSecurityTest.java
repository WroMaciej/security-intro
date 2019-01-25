package com.wromaciej.securityintro.spring;

import static org.junit.Assert.assertThat;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;

import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityTest {

	@Test
	public void shouldHashPassWithBCrypt() {
		// given
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		char[] passwordArray = { 'A', 'b', 'c', '1', '2', '3' };
		// when
		String hashedPassword = passwordEncoder.encode(passwordArray.toString());
		//then
		assertThat(hashedPassword,
				passwordEncoder.matches(passwordArray.toString(), hashedPassword), is(true));
		assertThat(hashedPassword,
				passwordEncoder.matches(passwordArray.toString(),
						"$2a$10$iJn.LdyIdFFAlb2j0NYczOQUjswYWZfFoWRHlwiZ6cwliuAgnM002"),
				is(true));
	}

	@Test
	public void shouldWorkWithRSA() {
		// given
		KeyPairGenerator keyPairGenerator;
		try {
			
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			KeyPair rsaKeys = keyPairGenerator.generateKeyPair();
			
			System.out.println(rsaKeys.getPrivate().getFormat());
			System.out.println(rsaKeys.getPublic());
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
