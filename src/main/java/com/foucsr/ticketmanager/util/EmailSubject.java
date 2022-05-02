package com.foucsr.ticketmanager.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Component;

import com.foucsr.ticketmanager.exception.ResourceNotFoundException;
import com.foucsr.ticketmanager.mysql.database.model.EmailDetails;
import com.foucsr.ticketmanager.mysql.database.repository.EmailDetailsRepository;

@Component
public class EmailSubject {

	private static EmailSubject emailTextObject;

	private static Properties props;

	private String emailSubject;

	private String emailText;

	private String emailFrom;

	private List<String> emailTo;

	private List<String> emailCC;

	private List<File> files;
	
	private static String username;
	
	private static String password;

	private static EncryptionUtil encryptionUtil;
	
	private boolean isHTML;
		
//	private static long userId;


	public EmailSubject() {
		
	}

	public static EmailSubject getInstance(EmailDetailsRepository emailDetailsRepository) throws InvalidKeyException, UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {

//		if (emailTextObject == null) {

			emailTextObject = new EmailSubject();

			EmailDetails emailDetails = emailDetailsRepository.findOneEmailDetails().orElseThrow(
					() -> new ResourceNotFoundException("SMTP details  does not exist!", "", ""));

			encryptionUtil = EncryptionUtil.getInstance();

			encryptionUtil.decryptEmailDetails(emailDetails);

			Properties prop = System.getProperties();
//			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.auth", "false");
			prop.put("mail.smtp.port", emailDetails.getPort());
			prop.put("mail.smtp.host", emailDetails.getSmtp_host());
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.debug", "true");
			
			// for ATA
			prop.put("mail.smtp.ssl.trust", emailDetails.getSmtp_host());
			
			emailTextObject.props = prop;
			
			emailTextObject.username = emailDetails.getUsername();
			emailTextObject.password = emailDetails.getPassword();

//		}

		return emailTextObject;
	}

	public void init(String emailFrom, List<String> emailTo, List<String> emailCC, List<File> files,
			String emailSubject, String emailText) {

		this.emailFrom = emailFrom;
		this.emailCC = emailCC;
		this.emailTo = emailTo;
		this.files = files;
		this.emailSubject = emailSubject;
		this.emailText = emailText;
	}
	
	public static EmailSubject getEmailTextObject() {
		return emailTextObject;
	}

	public static void setEmailTextObject(EmailSubject emailTextObject) {
		EmailSubject.emailTextObject = emailTextObject;
	}

	public static Properties getProps() {
		return props;
	}

	public static void setProps(Properties props) {
		EmailSubject.props = props;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public List<String> getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(List<String> emailTo) {
		this.emailTo = emailTo;
	}

	public List<String> getEmailCC() {
		return emailCC;
	}

	public void setEmailCC(List<String> emailCC) {
		this.emailCC = emailCC;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFds(List<File> files) {
		this.files = files;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isHTML() {
		return isHTML;
	}

	public void setHTML(boolean isHTML) {
		this.isHTML = isHTML;
	}
	
//	public long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(long userId) {
//		this.userId = userId;
//	}
	

//	@Override
//	public String toString() {
//		return "EmailSubject [emailSubject=" + emailSubject + ", emailText=" + emailText + ", emailFrom=" + emailFrom
//				+ ", emailTo=" + emailTo + ", emailCC=" + emailCC + ", fds=" + files + ", isHTML=" + isHTML + "]";
//	}
	
	@Override
	public String toString() {
		return "EmailSubject [emailSubject=" + emailSubject + ", emailText=" + emailText + ", emailFrom=" + emailFrom
				+ ", emailTo=" + emailTo + ", emailCC=" + emailCC + "]";
	}
	
	

}
