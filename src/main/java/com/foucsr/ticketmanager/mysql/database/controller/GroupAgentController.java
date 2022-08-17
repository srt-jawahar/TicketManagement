package com.foucsr.ticketmanager.mysql.database.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.ticketmanager.mysql.database.model.BusinessFunctions;
import com.foucsr.ticketmanager.mysql.database.model.GroupAgents;
import com.foucsr.ticketmanager.mysql.database.model.UnAssignedTicket;
import com.foucsr.ticketmanager.mysql.database.repository.GroupAgentRepository;
import com.foucsr.ticketmanager.mysql.database.service.GroupAgentService;

@RestController
@RequestMapping("groupAgents")
public class GroupAgentController 
{
	@Autowired
	private GroupAgentService groupAgentService;
	
	@DeleteMapping("delete/{groupAgentId}")
	public ResponseEntity<?> deleteGroupAgents(@PathVariable Long groupAgentId)
	{	
		ResponseEntity<?> delgroup = groupAgentService.deleteGroupAgents(groupAgentId);
		return delgroup;
		//return ResponseEntity.status(HttpStatus.OK).body(groupAgentService.deleteGroupAgents(groupAgentId));
	}
	
	@DeleteMapping("removeAll")
	public ResponseEntity<String> deleteAllGroupAgents()
	{
		return ResponseEntity.status(HttpStatus.OK).body(groupAgentService.deleteAllGroupAgents());
	}
	
	@GetMapping("get/{groupAgentId}")
	public ResponseEntity<?> getGroupAgents(@PathVariable Long groupAgentId)
	{
		ResponseEntity<?> getgroup = groupAgentService.getGroupAgents(groupAgentId);
		return getgroup;
	}
	
	@GetMapping("getName/{groupAgentName}")
	public ResponseEntity<GroupAgents> getGroupAgentsbyName(@PathVariable String groupAgentName)
	{
		return ResponseEntity.status(HttpStatus.OK).body(groupAgentService.getGroupAgentByName(groupAgentName));
		
	}
	
	
	// CREATE OR UPDATE
	@PostMapping("createorupdate")
	public ResponseEntity<?> CreateorUpdateGroupAgents(@RequestBody GroupAgents groupAgents,HttpServletRequest http)
	{
		//return ResponseEntity.status(HttpStatus.OK).body(groupAgentService.CreateorUpdateGroupAgents(groupAgents));
		ResponseEntity<?> createorupdategroupAgents = groupAgentService.CreateorUpdateGroupAgents(groupAgents, http);
		return createorupdategroupAgents;
	}
	
	//GET 
	@GetMapping("getGroups")
	public ResponseEntity<?> getGroupAgents()
	{
		ResponseEntity<?> getGroups = groupAgentService.getGroupAgents();
		return getGroups;	
	}
	
	
	@GetMapping("getListofBusinessFunctions")
	public List<BusinessFunctions> getBusinessFunctions()
	{
		List<BusinessFunctions> businessfunct = new ArrayList<>();
		
		List<String> listbusinessfunct = new ArrayList<>();
		
		Long businessId = (long) 1;
		
		listbusinessfunct.add("Finance");
		listbusinessfunct.add("Legal");
		listbusinessfunct.add("Marketing");
		
		for(String businessfun : listbusinessfunct)
		{
			BusinessFunctions busines = new BusinessFunctions();
			
			busines.setBusinessId(businessId);
			busines.setBusinessFunctions(businessfun);
			
			businessfunct.add(busines);
			businessId++;
			
		}
		return businessfunct;
	}
	
	@PostMapping("savebusiness")
	public ResponseEntity<?> createorupdateBussFunct(@RequestBody BusinessFunctions businessfunct,HttpServletRequest http)
	{
		ResponseEntity<?> createorupdateBussFunctions = groupAgentService.createorUpdateBusinessFunctions(businessfunct, http);
		return createorupdateBussFunctions;
	}
	
	@GetMapping("/getLOVList")
	public ResponseEntity<?> getTicketingLOVList(Principal principal)
	{
		ResponseEntity<?> message = groupAgentService.getGroupsLOVList();
		return message;
	}
	
	@GetMapping("getFunctions")
	public ResponseEntity<?> getBusinessFunctions1()
	{
		getGroupAgents();
		ResponseEntity<?> getFunctions = groupAgentService.getBusinessFunctions();
		return getFunctions;	
	}
	
	@GetMapping("getListofUnassignedTicket")
	public ResponseEntity<?> getUnassignedTicket()
	{
		ResponseEntity<?> unassignedTicket = groupAgentService.getUnAssignedTicket();
		return unassignedTicket;
	}
	
	@GetMapping("test")
	public String testing()
	{
		return "Testing App";
	}
	
}
