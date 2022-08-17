package com.foucsr.ticketmanager.mysql.database.model;

import java.util.List;

public class TicketingLOV 
{
	public List<String> getTickettimings() {
		return tickettimings;
	}

	public void setTickettimings(List<String> tickettimings) {
		this.tickettimings = tickettimings;
	}

	private List<String> tickettimings;
	
	private List<String> businessfunct;

	public List<String> getBusinessfunct() {
		return businessfunct;
	}

	public void setBusinessfunct(List<String> businessfunct) {
		this.businessfunct = businessfunct;
	}
	
	
}
