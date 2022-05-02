package com.foucsr.ticketmanager.mysql.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foucsr.ticketmanager.exception.AppException;
import com.foucsr.ticketmanager.exception.ResourceNotFoundException;
import com.foucsr.ticketmanager.mysql.database.model.EmailDetails;
import com.foucsr.ticketmanager.mysql.database.repository.EmailDetailsRepository;
import com.foucsr.ticketmanager.util.EncryptionUtil;

@Service
public class EmailDetailsService {

	@Autowired
	private EmailDetailsRepository emailDetailsRepository;
	
	private EncryptionUtil encryptionUtil;

	public EmailDetails findEmailDetails(String username) {
		
		EmailDetails emailDetailsList = emailDetailsRepository.findOneEmailDetails().orElseThrow(
				() -> new ResourceNotFoundException("SMTP details  does not exist!", "", ""));
		
		try {
			
		encryptionUtil = EncryptionUtil.getInstance();
			
		encryptionUtil.decryptEmailDetails(emailDetailsList);
		
		} catch (Exception e) {
			throw new AppException("Unable to get Email details");
		}
		
		return emailDetailsList;
	}

	public EmailDetails saveOrUpdateEmailDetails(EmailDetails emailDetails) {

		try {
			
			encryptionUtil = EncryptionUtil.getInstance();
			
			encryptionUtil.encryptEmailDetails(emailDetails);
			
			emailDetailsRepository.save(emailDetails);
			
			encryptionUtil.decryptEmailDetails(emailDetails);
			
			return emailDetails;

		} catch (Exception e) {
			throw new AppException("Unable to update Email details");
		}

	}

}
