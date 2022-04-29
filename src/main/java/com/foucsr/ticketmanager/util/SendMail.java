package com.foucsr.ticketmanager.util;

import java.io.File;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import com.foucsr.ticketmanager.exception.AppException;

public class SendMail {

	Logger logger = LoggerFactory.getLogger(SendMail.class);
	
	public SendMail() {
		// TODO Auto-generated constructor stub
	}

	@Async
	public void sendMail(EmailSubject emailObject) {
		
		try {
			
		logger.info("***************** Email Object values *********************" + emailObject.toString());
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
	    MimeMessage mail = mailSender.createMimeMessage();
		
	    mailSender.setJavaMailProperties(emailObject.getProps());
	    
//	    mailSender.setUsername(emailObject.getUsername());
//	    mailSender.setPassword(emailObject.getPassword());
	    
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
 
        // to mails
        InternetAddress[] mailAddress_TO = new InternetAddress[emailObject.getEmailTo().size()];

		for (int i = 0; i < emailObject.getEmailTo().size(); i++) {
			mailAddress_TO[i] = new InternetAddress(emailObject.getEmailTo().get(i));
		}
		
		 helper.setTo(mailAddress_TO);
   	
		 // cc mails
		if (emailObject.getEmailCC() != null) {
			InternetAddress[] mailAddress_CC = new InternetAddress[emailObject.getEmailCC().size()];

			for (int i = 0; i < emailObject.getEmailCC().size(); i++) {
				mailAddress_CC[i] = new InternetAddress(emailObject.getEmailCC().get(i));
			}

			helper.setCc(mailAddress_CC);
		}

		// from mail
       helper.setFrom(emailObject.getEmailFrom());
       
       
       // subject
       helper.setSubject(emailObject.getEmailSubject());
       helper.setText(emailObject.getEmailText(), emailObject.isHTML());
       
       logger.info("***************** Email content added and ready to send *********************");
       
       // attachments
       
       if(emailObject.getFiles() != null) {
    	   
    	   logger.info("***************** Email Entered in file attachment *********************");
    	   
    	   for(File file : emailObject.getFiles()) {
    		   
//    		   logger.info("***************** Email file name *********************");
//    		   logger.info(file.getName());
    		   helper.addAttachment(file.getName(), file);
//    		   logger.info("***************** Email file attached *********************");
    	   }
       }
       
       
       
       mailSender.send(mail);
       
       
       logger.info("***************** Email Sent *********************");
       
		} catch(Exception ex) {
			logger.error("***************** Email failure *********************\n" + ex);
//			logger.info("mailerror" + ex.toString());
//			logger.info("message" + ex.getMessage());
//			logger.info("cause" + ex.getCause());
			throw new AppException("Unable to send mail");
		}

	}
}
