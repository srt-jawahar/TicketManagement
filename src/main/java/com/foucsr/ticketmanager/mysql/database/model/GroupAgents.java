package com.foucsr.ticketmanager.mysql.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Required;

@Entity
@Table(name="GROUP_AGENTS")
public class GroupAgents 
{

	@Id
	@Column(name="GROUPAGENT_ID")
	@SequenceGenerator(name = "GROUPAGENT_SEQ", sequenceName = "GROUPAGENT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUPAGENT_SEQ")
	private Long groupAgentId;
	
	@Column(name="GROUPAGENT_NAME")
	@Size(min=3,max=10)
	@NotBlank
	private String groupAgentName;
	
	@Column(name="GROUPAGENT_DESCRIPTION")
	private String groupAgentDescription;
	
	@Column(name="BUSINESS_FUNCTION")
	@NotBlank
	private String businessFunction;
	
	@Column(name="TICKET_ASSIGNS")
	private String ticketAssignment;
	
	@Column(name="UNASSIGNED_TICKET_TIME")
	private String unassignedTicketTime;
	
	// GENERATE SETTERS AND GETTERS 
	
	public Long getGroupAgentId() {
		return groupAgentId;
	}

	public void setGroupAgentId(Long groupAgentId) {
		this.groupAgentId = groupAgentId;
	}

	public String getGroupAgentName() {
		return groupAgentName;
	}

	public void setGroupAgentName(String groupAgentName) {
		this.groupAgentName = groupAgentName;
	}

	public String getGroupAgentDescription() {
		return groupAgentDescription;
	}

	public void setGroupAgentDescription(String groupAgentDescription) {
		this.groupAgentDescription = groupAgentDescription;
	}

	public String getBusinessFunction() {
		return businessFunction;
	}

	public void setBusinessFunction(String businessFunction) {
		this.businessFunction = businessFunction;
	}

	public String getTicketAssignment() {
		return ticketAssignment;
	}

	public void setTicketAssignment(String ticketAssignment) {
		this.ticketAssignment = ticketAssignment;
	}
	

	public String getUnassignedTicketTime() {
		return unassignedTicketTime;
	}

	public void setUnassignedTicketTime(String unassignedTicketTime) {
		this.unassignedTicketTime = unassignedTicketTime;
	}

	public GroupAgents(Long groupAgentId, @Size(min = 3, max = 10) @NotBlank String groupAgentName,
			String groupAgentDescription, @NotBlank String businessFunction, String ticketAssignment,
			String unassignedTicketTime) {
		super();
		this.groupAgentId = groupAgentId;
		this.groupAgentName = groupAgentName;
		this.groupAgentDescription = groupAgentDescription;
		this.businessFunction = businessFunction;
		this.ticketAssignment = ticketAssignment;
		this.unassignedTicketTime = unassignedTicketTime;
	}

	public GroupAgents() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
