package com.foucsr.ticketmanager.mysql.database.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.foucsr.ticketmanager.exception.AppException;
import com.foucsr.ticketmanager.exception.ResourceNotFoundException;
import com.foucsr.ticketmanager.mysql.database.model.Role;
import com.foucsr.ticketmanager.mysql.database.model.User;
import com.foucsr.ticketmanager.mysql.database.repository.EmailDetailsRepository;
import com.foucsr.ticketmanager.mysql.database.repository.RoleRepository;
import com.foucsr.ticketmanager.mysql.database.repository.UserRepository;
import com.foucsr.ticketmanager.mysql.database.service.MapValidationErrorService;
import com.foucsr.ticketmanager.payload.ApiResponse;
import com.foucsr.ticketmanager.payload.JwtAuthenticationResponse;
import com.foucsr.ticketmanager.payload.LoginRequest;
import com.foucsr.ticketmanager.payload.SignUpRequest;
import com.foucsr.ticketmanager.payload.UpdateUserRequest;
import com.foucsr.ticketmanager.security.JwtTokenProvider;
import com.foucsr.ticketmanager.util.AppConstants;
import com.foucsr.ticketmanager.util.EmailHtmlLoader;
import com.foucsr.ticketmanager.util.EmailSubject;
import com.foucsr.ticketmanager.util.SCAUtil;
import com.foucsr.ticketmanager.util.SendMail;

/**
 * Created by FocusR.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

//	@Autowired
//	private POAgentsOracleRepository poAgentsOracleRepositoryOracle;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	EmailDetailsRepository emailDetailsRepository;

	@Autowired
	EmailHtmlLoader emailHtmlLoader;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/forgetPassword")
	public ResponseEntity<?> processForgotPasswordForm(@Valid @RequestBody UpdateUserRequest forgetPassRequest,
			BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

		if (errorMap != null)
			return errorMap;

		User user = userRepository.findByUsernameOrEmail(forgetPassRequest.getEmail(), forgetPassRequest.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User  does not exist!", "username or email",
						forgetPassRequest.getEmail()));

		String jwt = tokenProvider.generateTokenForgetPassword(user);

		user.setResetToken(jwt);

		// Save token to database
		User results = userRepository.save(user);

		sendMail(forgetPassRequest, jwt, user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(results.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User Updated successfully"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/updateNewPassword")
	public ResponseEntity<?> updateNewPassword(@Valid @RequestBody UpdateUserRequest signUpRequest,
			BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		User user = userRepository.findByResetToken(signUpRequest.getToken()).orElseThrow(
				() -> new ResourceNotFoundException("User token does not exist!", "token", signUpRequest.getToken()));

		if (signUpRequest.getPassword() != null) {

			String jwt = signUpRequest.getToken();

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

				/*
				 * Long userId = tokenProvider.getUserIdFromJWT(jwt);
				 * 
				 * UserDetails userDetails = customUserDetailsService.loadUserById(userId);
				 * UsernamePasswordAuthenticationToken authentication = new
				 * UsernamePasswordAuthenticationToken(userDetails, null,
				 * userDetails.getAuthorities()); authentication.setDetails(new
				 * WebAuthenticationDetailsSource().buildDetails(request));
				 * 
				 * SecurityContextHolder.getContext().setAuthentication(authentication);
				 */

				user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
				user.setResetToken(null);

				User results = userRepository.save(user);

				URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
						.buildAndExpand(results.getUsername()).toUri();

				return ResponseEntity.created(location)
						.body(new ApiResponse(true, "User password updated successfully"));
			}

		}

		return new ResponseEntity(new ApiResponse(false, "unable to update password"), HttpStatus.BAD_REQUEST);

	}

	private void sendMail(UpdateUserRequest forgetPassRequest, String jwt, User user) {

//		User admin = userRepository.findAdmin()
//				.orElseThrow(() -> new ResourceNotFoundException("User  does not exist!", "", ""));

		String appUrl = forgetPassRequest.getForgetPasswordUrl();

		String emailFrom = new String();
		List<String> emailTo = new ArrayList<String>();
		List<String> emailCC = new ArrayList<String>();
		String subject = AppConstants.forgetPasswordSubject;
		String text = emailHtmlLoader.getText(AppConstants.forgetPasswordTemplate, appUrl + "/resetPwd/" + jwt);

		emailTo.add(user.getEmail());

		EmailSubject emailSubject = null;
		try {
			emailSubject = EmailSubject.getInstance(emailDetailsRepository);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeySpecException e) {

			throw new AppException("Unable to get Email details");
		}

		String fromEmail = emailSubject.getUsername();
		emailFrom = fromEmail;

		emailSubject.init(emailFrom, emailTo, emailCC, null, subject, text);
		emailSubject.setHTML(true);

		SendMail sm = new SendMail();

		sm.sendMail(emailSubject);
	}

	public void createAdmin() {

		Optional<User> admin = null;

		try {
			admin = userRepository.findFirstAdmin();
		} catch (Exception ex) {
			logger.error("***************** Unable to find admin ********************* " + ex);
		}

		if (admin != null && !admin.isPresent()) {

			User user = new User("admin", "sysadmin", "sysadmin@gmail.com", "welcome", "", "", "", 'Y');

			user.setSupplier_flag('N');
			user.setBuyer_flag('N');

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByName("ROLE_ADMIN")
					.orElseThrow(() -> new AppException("User Role not set."));

			user.setRoles(Collections.singleton(userRole));

			try {
				userRepository.save(user);
			} catch (Exception ex) {
				logger.error("***************** Unable to create admin ********************* " + ex);
			}

		}

	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		logger.info("***************** User signin *********************\n" + loginRequest.getUsernameOrEmail());

//		testConnection();
//		
		createAdmin();

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = "";

		jwt = tokenProvider.generateToken(authentication);

		User user = null;

		Long userId = getUserIdFromJWT(jwt);

		Optional<User> opt = userRepository.findUser(userId);

		if (!opt.isPresent()) {

			return new ResponseEntity(new ApiResponse(false, "User is not found !"), HttpStatus.BAD_REQUEST);
		}

		user = opt.get();

		String name = user.getName() == null ? "" : user.getName();
		String full_name = user.getFull_name() == null ? "" : user.getFull_name();

		SCAUtil scaUtil = new SCAUtil();

		if ("Y".equals(loginRequest.getIsSuperUser())) {

			if (!"Y".equals(user.getIs_super_user())) {

				return new ResponseEntity(new ApiResponse(false, "User is not a Super User!"), HttpStatus.BAD_REQUEST);
			}

			jwt = tokenProvider.generateCoOrdinatorToken(authentication, loginRequest.getVendorID());
		}

		JwtAuthenticationResponse resp = new JwtAuthenticationResponse(jwt);
		resp.setName(name);
		resp.setFull_name(full_name);

		return ResponseEntity.ok(resp);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {

		URI location = null;

		try {

			ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
			if (errorMap != null)
				return errorMap;
			String getAgentOrBuyerName = signUpRequest.getName() != null ? signUpRequest.getName() : "";

			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
						HttpStatus.BAD_REQUEST);
			}

//		java.util.Date date = poAgentsOracleRepositoryOracle.findDateFromServer();

			if (!"ROLE_ADMIN".equals(signUpRequest.getUserRoles())
					&& !"ROLE_SUPERUSER".equals(signUpRequest.getUserRoles())
					&& !"ROLE_FINANCE".equals(signUpRequest.getUserRoles())) {

				if (signUpRequest.getAgentId() == null && signUpRequest.getVendorID() == null) {
					return new ResponseEntity(new ApiResponse(false, "User must be a supplier or a buyer!"),
							HttpStatus.BAD_REQUEST);
				}

				if (!(("ROLE_BUYER".equals(signUpRequest.getUserRoles()) && signUpRequest.getAgentId() != null)
						|| ("ROLE_REQUESTOR".equals(signUpRequest.getUserRoles()) && signUpRequest.getAgentId() != null)
						|| ("ROLE_SUPPLIER".equals(signUpRequest.getUserRoles())
								&& signUpRequest.getVendorID() != null))) {
					return new ResponseEntity(new ApiResponse(false, "Role must have a proper supplier or a buyer id!"),
							HttpStatus.BAD_REQUEST);
				}

			}

			// Creating user's account
			User user = new User(getAgentOrBuyerName, signUpRequest.getUsername(), signUpRequest.getEmail(),
					signUpRequest.getPassword(), signUpRequest.getOrgname(), signUpRequest.getVendorID(),
					signUpRequest.getAgentId(), 'Y');

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			user.setFull_name(signUpRequest.getFull_name());

			if (signUpRequest.getVendorID() != null) {
				user.setSupplier_flag('Y');
				user.setBuyer_flag('N');
			}

			if (signUpRequest.getAgentId() != null) {
				user.setSupplier_flag('N');
				user.setBuyer_flag('Y');
			}
			Role userRole = roleRepository.findByName(signUpRequest.getUserRoles())
					.orElseThrow(() -> new AppException("User Role not set."));

			user.setRoles(Collections.singleton(userRole));

			user.setIs_super_user("N");

			if ("ROLE_SUPERUSER".equals(signUpRequest.getUserRoles())) {

				user.setIs_super_user("Y");

			}

			User results = userRepository.save(user);

			location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
					.buildAndExpand(results.getUsername()).toUri();

		} catch (Exception ex) {

			logger.error("***************** Unable to register user!  *********************\n" + ex);

			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}

			return new ResponseEntity(new ApiResponse(false, " Unable to register user!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}

	private Long getUserIdFromJWT(String jwt) {

		Long userId = tokenProvider.getUserIdFromJWT(jwt);

		return userId;
	}

}
