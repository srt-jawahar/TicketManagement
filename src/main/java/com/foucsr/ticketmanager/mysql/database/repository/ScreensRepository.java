package com.foucsr.ticketmanager.mysql.database.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.foucsr.ticketmanager.mysql.database.model.Screens;

@Repository
public interface ScreensRepository extends CrudRepository<Screens, Long> {
	   
	//Optional<PoAgents> findById(Long id);
	
    @Override
    Iterable<Screens> findAll();

   
}