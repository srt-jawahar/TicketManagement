package com.foucsr.ticketmanager.mysql.database.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foucsr.ticketmanager.exception.AppException;
import com.foucsr.ticketmanager.mysql.database.model.User;
import com.foucsr.ticketmanager.mysql.database.repository.UserRepository;
import com.foucsr.ticketmanager.payload.ApiResponse;
import com.foucsr.ticketmanager.payload.DeleteUserRequest;
import com.foucsr.ticketmanager.util.AppConstants;
import com.foucsr.ticketmanager.util.SCAUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository usersRepository;
	
	Logger logger = LoggerFactory.getLogger(UserRepository.class);

	public List<User> findAllProjects(String username) {
		return usersRepository.findListOfUsers();
	}
	
	public void deleteUserById(long id) {

		User user = usersRepository.findById(id).orElseThrow(() -> new AppException("User does not exist."));
		user.setIs_Active('N');
		usersRepository.save(user);
	}
	
	public Optional findUserByResetToken(String resetToken) {
		return usersRepository.findByResetToken(resetToken);
	}
	

	public ResponseEntity<?> activeOrInactiveUser(DeleteUserRequest deleteRequest) {

		SCAUtil prUtil = new SCAUtil();
		
		Long user_id = deleteRequest.getId();
		char activeOrInactive = deleteRequest.getIs_Active();
		
		try {

			User user = usersRepository.findUserByIdToActiveOrInactive(user_id);
			
			if(user == null) {
				
				return new ResponseEntity(new ApiResponse(false, "User does not exist" ),
						HttpStatus.BAD_REQUEST);
			}
			
			user.setIs_Active(activeOrInactive);
			
			usersRepository.save(user);
			
		} catch (Exception e) {

			logger.error("***************** Unable to Active/Inactive the user!  *********************\n" + e);

			String msg = prUtil.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to Active/Inactive the user!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, AppConstants.Success_Message), HttpStatus.OK);

	}

}
