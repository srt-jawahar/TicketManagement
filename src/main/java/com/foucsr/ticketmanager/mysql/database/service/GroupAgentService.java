package com.foucsr.ticketmanager.mysql.database.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.foucsr.ticketmanager.exception.IdNotFoundException;
import com.foucsr.ticketmanager.mysql.database.model.BusinessFunctions;
import com.foucsr.ticketmanager.mysql.database.model.GroupAgents;
import com.foucsr.ticketmanager.mysql.database.model.TicketingLOV;
import com.foucsr.ticketmanager.mysql.database.model.UnAssignedTicket;
import com.foucsr.ticketmanager.mysql.database.repository.BusinessFunctionRepository;
import com.foucsr.ticketmanager.mysql.database.repository.GroupAgentRepository;
import com.foucsr.ticketmanager.payload.ApiResponse;
import com.foucsr.ticketmanager.util.SCAUtil;

@Service
public class GroupAgentService {
	@Autowired
	private GroupAgentRepository groupAgentRepository;

	@Autowired
	private BusinessFunctionRepository businessfunctrepository;

	// GET BY ID
	public ResponseEntity<GroupAgents> getGroupAgents(Long groupAgentId) throws IdNotFoundException {
		GroupAgents getGroups = groupAgentRepository.findById(groupAgentId)
				.orElseThrow(() -> new IdNotFoundException("Given Id not Found"));
		
		return new ResponseEntity(getGroups,HttpStatus.OK);
	}

	// GET BY NAME

	public GroupAgents getGroupAgentByName(String groupAgentName) {
		return groupAgentRepository.findGroupAgentsByName(groupAgentName);
	}

	// DELETE BY ID
	public ResponseEntity<GroupAgents> deleteGroupAgents(Long groupAgentId) throws IdNotFoundException {
		GroupAgents delGroup = groupAgentRepository.findById(groupAgentId)
				.orElseThrow(() -> new IdNotFoundException("Given Id not Found"));
		groupAgentRepository.delete(delGroup);
		return new ResponseEntity(delGroup, HttpStatus.OK);
	}

	// DELETE ALL
	public String deleteAllGroupAgents() {
		// GroupAgents delGroup =
		// groupAgentRepository.findById(groupAgentId).orElseThrow(() -> new
		// IdNotFoundException("Given Id not Found"));
		groupAgentRepository.deleteAll();
		return "All entries was deleted successfully...!!!";
	}

	Logger log = LoggerFactory.getLogger(GroupAgentService.class);
	SCAUtil sca = new SCAUtil();

	public ResponseEntity<?> CreateorUpdateGroupAgents(GroupAgents groupAgents, HttpServletRequest http) {

//		if (!"Y".equalsIgnoreCase(groupAgents.getTicketAssignment())
//				&& !"N".equalsIgnoreCase(groupAgents.getTicketAssignment())) {
//
//			return new ResponseEntity(new ApiResponse(false, "Ticket should Y/N type"), HttpStatus.NOT_ACCEPTABLE);
//		}

		try {
			Long groupAgentId = groupAgents.getGroupAgentId();

			if (groupAgents.getGroupAgentId() != null) {
				GroupAgents isgroupAgents = groupAgentRepository.findGroupAgentsById(groupAgentId);

				if (isgroupAgents == null) {
					return new ResponseEntity(new ApiResponse(false, "No Groups Found"),
							HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
				}
			}

			groupAgentRepository.save(groupAgents);

		}

		catch (Exception e) {
			SCAUtil sca = new SCAUtil();

			log.error("*****!!! UNABLE TO CREATE !!!*******" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to save GroupAgents" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(groupAgents, HttpStatus.CREATED);
	}

	// GET ALL
	public ResponseEntity<?> getGroupAgents() {

		List<GroupAgents> groups;

		try {
			groups = groupAgentRepository.findAll();

			if (groups == null) {
				groups = new ArrayList<GroupAgents>();
			}
		} catch (Exception e) {
			SCAUtil sca = new SCAUtil();
			log.error("***!!! not able to fetch Groups!!! ***");

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Facing issue to get GroupAgents!" + msg),
					HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity(groups, HttpStatus.OK);
	}

	// CREATE BUSINESS FUNCTIONS --

	public ResponseEntity<?> createorUpdateBusinessFunctions(BusinessFunctions businessfunct, HttpServletRequest http) {

		try {
			Long businessId = businessfunct.getBusinessId();

			if (businessfunct.getBusinessId() != null) {
				BusinessFunctions isbusinessfunctions = businessfunctrepository.findBusinessFunctionsById(businessId);

				if (isbusinessfunctions == null) {
					return new ResponseEntity(new ApiResponse(false, "No Groups Found"),
							HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
				}
			}

			businessfunctrepository.save(businessfunct);
		} catch (Exception e) {
			log.error("***!!! not able to fetch Groups!!! ***");

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Business functions not saved" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(businessfunct, HttpStatus.OK);

	}

	public ResponseEntity<?> getBusinessFunctions() {

		List<BusinessFunctions> functions;

		try {
			functions = businessfunctrepository.findAll();

			if (functions == null) {
				functions = new ArrayList<BusinessFunctions>();
			}
		} catch (Exception e) {
			log.error("***!!! not able to fetch Business Functions!!! ***");

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Facing issue to get Business Functions!" + msg),
					HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity(functions, HttpStatus.OK);
	}

	// UNASSIGNED TICKETS --

	public ResponseEntity<?> getUnAssignedTicket() {
		List<UnAssignedTicket> unassignticket = new ArrayList<>();

		List<String> ticketunassigns = new ArrayList<>();

		ticketunassigns.add("15 minutes");
		ticketunassigns.add("30 minutes");
		ticketunassigns.add("60 minutes");
		ticketunassigns.add("120 minutes");

		Long ticketId = (long) 101;

		for (String timers : ticketunassigns) {
			UnAssignedTicket unassignticketsconst = new UnAssignedTicket();
			unassignticketsconst.setTicketId(ticketId);
			unassignticketsconst.setUnassignedTime(timers);

			unassignticket.add(unassignticketsconst);
			ticketId++;
		}

		return new ResponseEntity(unassignticket, HttpStatus.OK);
	}
	
	public ResponseEntity<?> getGroupsLOVList()
	{
		TicketingLOV ticketingLOV = new TicketingLOV();
		SCAUtil sca = new SCAUtil();
		
		try
		{
			ticketingLOV.setTickettimings(getTicketTimings());
			ticketingLOV.setBusinessfunct(getBusinessFunction());
		}catch(Exception e)
		{
			String msg = sca.getErrorMessage(e);
			return new ResponseEntity(new ApiResponse(false, "Unable to get LOV"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(ticketingLOV, HttpStatus.OK);
	}
	
	private List<String> getTicketTimings()
	{
		List<String> ticketTime = new ArrayList<>();
		ticketTime.add("15 minutes");
		ticketTime.add("30 minutes");
		ticketTime.add("1 hour");
		ticketTime.add("3 hours");
		return ticketTime;
	}
	
	private List<String> getBusinessFunction()
	{
		List<String> businessfunct = new ArrayList<>();
		businessfunct.add("Finance");
		businessfunct.add("Marketing");
		businessfunct.add("Legal");
		return businessfunct;
	}
}
