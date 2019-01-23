package com.wromaciej.securityintro.security.service.impl;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wromaciej.securityintro.security.model.AsymmetricKeysDto;
import com.wromaciej.securityintro.security.model.RSAKeyDto;
import com.wromaciej.securityintro.security.service.KeyGeneratorService;

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
	
	@Test(timeout = 10000L)
	public void shouldGenerate32BitKeyIn10Seconds() {
		//given
		//when
		RSAKeyDto key = keyGenerator.getRandomKey(32);
		//then
		System.out.println(key);
	}
	

	
	

}
