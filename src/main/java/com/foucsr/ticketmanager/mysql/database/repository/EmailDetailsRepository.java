package com.foucsr.ticketmanager.mysql.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.foucsr.ticketmanager.mysql.database.model.EmailDetails;

/**
 * Created by FocusR on 29-Sep-2019.
 */
@Repository
public interface EmailDetailsRepository extends JpaRepository<EmailDetails, Long> {
	
	@Override
    List<EmailDetails> findAll();
	
	 @Query(value = "select * from EMAIL_DETAILS", nativeQuery = true)
	 Optional<EmailDetails> findOneEmailDetails();
 

}
