package com.hari.xxx.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;

import org.springframework.stereotype.Component;
@Component
public class RSAKeyGenerationUtil {

	private PublicKey publickey;
	private PrivateKey privateKey;
	private static final Logger logger = LoggerFactory.getLogger(RSAKeyGenerationUtil.class);

	@PostConstruct
	public void generateKeys() {
		KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			KeyPair pair = generator.generateKeyPair();
			this.publickey = pair.getPublic();
			this.privateKey = pair.getPrivate();
			logger.info("Created the RSA Key pair during Startup");
		} catch (NoSuchAlgorithmException e) {
			logger.info("RSA Key pair Generation Error : {}", e.getMessage());
		}
	}

	public String getPublicKey() {
		return new String(Base64.encodeBase64(this.publickey.getEncoded()));
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public String decrypt(String encryptedPassword) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
			byte[] cipherText = cipher.doFinal(Base64.decodeBase64(encryptedPassword));
			return new String(cipherText, "UTF-8");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return "";
	}

}
