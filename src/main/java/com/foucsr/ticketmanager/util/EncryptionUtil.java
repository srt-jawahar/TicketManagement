package com.foucsr.ticketmanager.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.foucsr.ticketmanager.mysql.database.model.EmailDetails;

public class EncryptionUtil {


	private String unicode_format = "UTF8";

	public String desede_encryption_scheme = "DESede";

	private String myEncryptionKey = "securesecuresecuresecure";

	private KeySpec ks;

	private SecretKeyFactory skf;

	private Cipher cipher;

	byte[] arrayBytes;

	private String myEncryptionScheme;

	SecretKey key;

	private static EncryptionUtil encryptionUtil;

	public EncryptionUtil() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException {

		myEncryptionScheme = desede_encryption_scheme;
		arrayBytes = myEncryptionKey.getBytes(unicode_format);
		ks = new DESedeKeySpec(arrayBytes);
		skf = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = skf.generateSecret(ks);
	}

	public static EncryptionUtil getInstance() throws UnsupportedEncodingException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {

		if (encryptionUtil == null) {
			encryptionUtil = new EncryptionUtil();
		}
		return encryptionUtil;
	}

	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(unicode_format);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

	public void encryptEmailDetails(EmailDetails emailDetails) {

		emailDetails.setSmtp_host(encryptionUtil.encrypt(emailDetails.getSmtp_host()));
		emailDetails.setUsername(encryptionUtil.encrypt(emailDetails.getUsername()));
		emailDetails.setPassword(encryptionUtil.encrypt(emailDetails.getPassword()));

	}

	public void decryptEmailDetails(EmailDetails emailDetails) {

		emailDetails.setSmtp_host(encryptionUtil.decrypt(emailDetails.getSmtp_host()));
		emailDetails.setUsername(encryptionUtil.decrypt(emailDetails.getUsername()));
		emailDetails.setPassword(encryptionUtil.decrypt(emailDetails.getPassword()));

	}
}
