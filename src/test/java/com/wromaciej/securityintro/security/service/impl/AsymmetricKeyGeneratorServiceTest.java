package com.wromaciej.securityintro.security.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wromaciej.securityintro.security.model.AsymmetricKeysDto;
import com.wromaciej.securityintro.security.service.KeyGeneratorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsymmetricKeyGeneratorServiceTest {
	
	@Autowired
	KeyGeneratorService<AsymmetricKeysDto> keyGenerator;
	
	@Test
	public void shouldGenerateRandomKey() {
		//given
		//when
		AsymmetricKeysDto key = keyGenerator.getRandomKey(32);
		//then
		System.out.println(key);
	
		
		
	}

}
