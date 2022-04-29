package com.foucsr.ticketmanager.mysql.database.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.ticketmanager.mysql.database.model.Screens;
import com.foucsr.ticketmanager.mysql.database.service.MapValidationErrorService;
import com.foucsr.ticketmanager.mysql.database.service.ScreensService;

@RestController
@RequestMapping("/Screens/Service")
@CrossOrigin
public class ScreensController {

	@Autowired
	private ScreensService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Screens project, BindingResult result,
			Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		Screens project1 = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Screens>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable long projectId, Principal principal) {

		Optional<Screens> project = projectService.findProjectByIdentifier(projectId, principal.getName());

		return new ResponseEntity<Optional<Screens>>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Screens> getAllProjects(Principal principal) {
		return projectService.findAllProjects(principal.getName());
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable long projectId, Principal principal) {
		projectService.deleteProjectByIdentifier(projectId, principal.getName());

		return new ResponseEntity<String>("Project with ID: '" + projectId + "' was deleted", HttpStatus.OK);
	}
}
