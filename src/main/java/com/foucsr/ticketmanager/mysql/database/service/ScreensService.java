package com.foucsr.ticketmanager.mysql.database.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foucsr.ticketmanager.mysql.database.model.Screens;
import com.foucsr.ticketmanager.mysql.database.repository.ScreensRepository;

@Service
public class ScreensService {

	@Autowired
	private ScreensRepository screensRepository;

	public Screens saveOrUpdateProject(Screens screens, String username) {

		try {

		} catch (Exception e) {
			// throw new ProjectIdException("Project ID
			// '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
		}
		return screensRepository.save(screens);

	}

	public Optional<Screens> findProjectByIdentifier(Long id, String username) {

		// Only want to return the project if the user looking for it is the owner

		Optional<Screens> screens = screensRepository.findById(id);

		return screens;
	}

	public Iterable<Screens> findAllProjects(String username) {
		return screensRepository.findAll();
	}

	public void deleteProjectByIdentifier(long id, String username) {

		screensRepository.deleteById(id);
	}

}
