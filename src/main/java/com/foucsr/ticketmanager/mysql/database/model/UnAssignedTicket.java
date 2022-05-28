package com.foucsr.ticketmanager.mysql.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="UNASSIGNED_TICKET_TIME")
public class UnAssignedTicket 
{
	@Id
	@Column(name="TICKET_ID")
	@SequenceGenerator(name = "UNASSIGNED_TICKET_TIME_SEQ", sequenceName = "UNASSIGNED_TICKET_TIME_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNASSIGNED_TICKET_TIME_SEQ")
	private Long ticketId;
	
	@Column(name="UNASSIGNED_TIME")
	private String unassignedTime;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getUnassignedTime() {
		return unassignedTime;
	}

	public void setUnassignedTime(String unassignedTime) {
		this.unassignedTime = unassignedTime;
	}
	
	
	
}
