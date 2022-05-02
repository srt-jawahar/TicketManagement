package com.foucsr.ticketmanager.mysql.database.controller;

import java.net.URI;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.foucsr.ticketmanager.mysql.database.repository.RoleRepository;
import com.foucsr.ticketmanager.mysql.database.repository.UserRepository;
import com.foucsr.ticketmanager.mysql.database.service.MapValidationErrorService;
import com.foucsr.ticketmanager.mysql.database.service.UserService;
import com.foucsr.ticketmanager.payload.ApiResponse;
import com.foucsr.ticketmanager.payload.DeleteUserRequest;
import com.foucsr.ticketmanager.payload.UpdateUserRequest;
import com.foucsr.ticketmanager.payload.UserProfile;
import com.foucsr.ticketmanager.payload.UserSummary;
import com.foucsr.ticketmanager.security.CurrentUser;
import com.foucsr.ticketmanager.security.UserPrincipal;

@RestController
@RequestMapping("/Users/Service")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;


	// private static final Logger logger =
	// LoggerFactory.getLogger(UserController.class);

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}
	

	@GetMapping("/users/{username}")
	public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

		 if('Y' != user.getIs_Active()) {
	        	throw new UsernameNotFoundException("User is not active : " + username);
	     }
		
		long pollCount = 0;// = pollRepository.countByCreatedBy(user.getId());
		long voteCount = 0;// voteRepository.countByUserId(user.getId());

		UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), pollCount,
				voteCount);

		return userProfile;
	}

	@GetMapping("/getListOfUsers")
	public List<User> getAllProjects(Principal principal) {
		List<User> listofUsers = userService.findAllProjects(principal.getName());
		return listofUsers;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/updateUserDetails")
	public ResponseEntity<?> updateUserDetails(@Valid @RequestBody UpdateUserRequest signUpRequest,
			BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		User user = userRepository.findById(signUpRequest.getId()).orElseThrow(
				() -> new ResourceNotFoundException("User Id is does not exist!", "id", signUpRequest.getId()));

		if (signUpRequest.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

		}
		
		user.setFull_name(signUpRequest.getFull_name());
		
		if (signUpRequest.getUserRoles() != null) {
		Role userRole = roleRepository.findByName(signUpRequest.getUserRoles())
				.orElseThrow(() -> new AppException("User Role not set."));
			Set rolesMap = new HashSet();
			Role setRoles = new Role();
			setRoles.setId((long) userRole.getId());
			setRoles.setName(userRole.getName());
			user.setRoles(rolesMap);

			Set rolesMap1 = new HashSet();
			rolesMap1.add(userRole);
			user.setRoles(rolesMap1);
		}
		if (signUpRequest.getName() != null) {
			user.setName(signUpRequest.getName());
		}
		
		/*if(signUpRequest.getUsername()!=null) {
			user.setUsername(signUpRequest.getUsername());
			}*/
		
		if(signUpRequest.getOrgname()!=null) {
			user.setOrgname(signUpRequest.getOrgname());
		}
		user.setEmail(signUpRequest.getEmail());
		User results = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(results.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User Updated successfully"));
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable long userId, Principal principal) {
		
		userService.deleteUserById(userId);
		return new ResponseEntity<String>("User with ID: '" + userId + "' was deleted", HttpStatus.OK);
	}
	
	@PostMapping("/deleteUser")
	public ResponseEntity<?> activeOrInactiveUser(@Valid @RequestBody DeleteUserRequest deleteRequest,
			Principal principal) {
		return userService.activeOrInactiveUser(deleteRequest);

	}
	
}
