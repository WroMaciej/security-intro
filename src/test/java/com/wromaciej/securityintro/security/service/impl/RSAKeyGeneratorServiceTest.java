package com.wromaciej.securityintro.security.service.impl;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wromaciej.securityintro.security.model.RSAKeyDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RSAKeyGeneratorServiceTest {
	
	@Autowired
	RSAKeyGeneratorService keyGenerator;
	
	
	@Test
	public void shouldGenerateRandomKey() {
		//given
		//when
		RSAKeyDto key = keyGenerator.getRandomKey(64);
		//then
		System.out.println(key);
	}
	
	@Test(timeout = 5000L)
	public void shouldGenerate256BitKeyIn5seconds() {
		//given
		//when
		RSAKeyDto key = keyGenerator.getRandomKey(256);
		//then
		System.out.println(key);

	}
	

	
	

}
