package com.foucsr.ticketmanager.mysql.database.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.ticketmanager.mysql.database.model.EmailDetails;
import com.foucsr.ticketmanager.mysql.database.service.EmailDetailsService;
import com.foucsr.ticketmanager.mysql.database.service.MapValidationErrorService;

@RestController
@RequestMapping("/EmailDetails/Service")
@CrossOrigin
public class EmailDetailsController {

	@Autowired
	private EmailDetailsService emailDetailsService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/CreateOrUpdate")
	public ResponseEntity<?> createOrUpdateEmailDetails(@Valid @RequestBody EmailDetails emailDetails,
			BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		EmailDetails emailDetailsRes = emailDetailsService.saveOrUpdateEmailDetails(emailDetails);

		return new ResponseEntity<EmailDetails>(emailDetailsRes, HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public EmailDetails getAll(Principal principal) {
		return emailDetailsService.findEmailDetails(principal.getName());
	}

}
