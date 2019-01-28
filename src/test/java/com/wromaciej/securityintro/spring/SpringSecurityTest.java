package com.wromaciej.securityintro.spring;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.wromaciej.securityintro.security.model.SimplePerson;

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
		// then
		assertThat(hashedPassword,
				passwordEncoder.matches(passwordArray.toString(), hashedPassword), is(true));

	}

	@Test
	public void shouldGenerateRSAKeyPair() throws NoSuchAlgorithmException {
		// given
		KeyPairGenerator keyPairGenerator;

		keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(512);
		KeyPair rsaKeys = keyPairGenerator.generateKeyPair();

		RSAPublicKey rsaPublicKey;
		RSAPrivateKey rsaPrivateKey;

		rsaPublicKey = (RSAPublicKey) rsaKeys.getPublic();
		rsaPrivateKey = (RSAPrivateKey) rsaKeys.getPrivate();

		System.out.println(rsaPublicKey);
		System.out.println("private exponent: " + rsaPrivateKey.getPrivateExponent());

	}

	@Test
	public void shouldEncryptAndDecryptObject() throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			IOException, ClassNotFoundException, BadPaddingException {
		// given

		SimplePerson person = new SimplePerson("Benny Lava", 20);

		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[16];
		secureRandom.nextBytes(key);
		SecretKey secretKey = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		SealedObject sealedPerson;

		// when
		sealedPerson = new SealedObject(person, cipher);

		person.setName("changed");
		person.setAge(30);

		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		SimplePerson decryptedPerson = (SimplePerson) sealedPerson.getObject(cipher);
		
		try {
			FileOutputStream fileOut = new FileOutputStream(new File("simplePersonSealed.txt"));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(sealedPerson);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	@Test
	public void shouldSerializeObjectToFile() {
		SimplePerson person = new SimplePerson("Benny Lava", 20);

		try {
			FileOutputStream fileOut = new FileOutputStream(new File("simplePerson1.txt"));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(person);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

}
