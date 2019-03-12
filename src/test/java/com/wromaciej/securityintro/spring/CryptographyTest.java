package com.wromaciej.securityintro.spring;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wromaciej.securityintro.security.model.SimplePerson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CryptographyTest {
	
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
			FileOutputStream fileOut = new FileOutputStream(
					new File("simplePersonSealed.txt"));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(sealedPerson);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	@Test
	public void shouldSaveEncryptedAndRawObjectsAsJsonToFile()
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, IOException {
		// given
		Gson gsonFormat = new GsonBuilder().setPrettyPrinting().create();
		Gson gson = new Gson();

		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[32];
		secureRandom.nextBytes(key);
		SecretKey secretKey = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		SimplePerson person = new SimplePerson("Benny Lava", 20);
		SealedObject sealedPerson = new SealedObject(person, cipher);

		// when
		Writer writer = new FileWriter("simplePersonJSonRaw.json");
		gson.toJson(person, writer);
		writer.flush();
		writer.close();
		writer = new FileWriter("simplePersonJSonSealed.json");
		gson.toJson(sealedPerson, writer);
		writer.flush();
		writer.close();

		SimplePerson readedRawObject = gson
				.fromJson(new FileReader("simplePersonJSonRaw.json"), SimplePerson.class);
		SimplePerson readedSealedObject = gson
				.fromJson(new FileReader("simplePersonJSonRaw.json"), SimplePerson.class);

		// then
		assertThat(readedRawObject, is(person));

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

	@Test
	public void shouldLoadKeyFromKeyStoreAndUseItForCipher() throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableEntryException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, ClassNotFoundException, BadPaddingException {
		// given
		char[] ksPassword = "qwerty".toCharArray();
		String keyStoreType = "PKCS12";
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		FileInputStream ksFile = new FileInputStream("ks3.pkcs12");
		keyStore.load(ksFile, ksPassword);

		char[] secretKeyPassword1 = "qwerty2".toCharArray();
		String secretKeyAlias1 = "sec1";

		Key secretKey1 = keyStore.getKey(secretKeyAlias1, secretKeyPassword1);

		Gson gson = new Gson();

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey1);

		SimplePerson person = new SimplePerson("Benny Lava", 20);
		SealedObject sealedPerson = new SealedObject(person, cipher);

		Writer writer = new FileWriter("rawPerson.json");
		gson.toJson(person, writer);
		writer.flush();
		writer.close();
		writer = new FileWriter("sealedPerson.json");
		gson.toJson(sealedPerson, writer);
		writer.flush();
		writer.close();

		// when
		SimplePerson readedRawObject = gson.fromJson(new FileReader("rawPerson.json"),
				SimplePerson.class);
		SealedObject readedSealedObject = gson.fromJson(new FileReader("sealedPerson.json"),
				SealedObject.class);
		cipher.init(Cipher.DECRYPT_MODE, secretKey1);
		SimplePerson decryptedPerson = (SimplePerson) sealedPerson.getObject(cipher);
		
		
		
		// then
		assertThat(readedRawObject, is(person));
		assertThat(readedSealedObject, not(person));
		assertThat(decryptedPerson, is(person));

	}

}
