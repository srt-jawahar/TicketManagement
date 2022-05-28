package com.foucsr.ticketmanager.mysql.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="GROUPBUSINESS_FUNCTIONS")
public class BusinessFunctions 
{
	@Id
	@Column(name="BUSINESS_ID")
	@SequenceGenerator(name = "BUSINESS_FUNCTIONS_SEQ", sequenceName = "BUSINESS_FUNCTIONS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUSINESS_FUNCTIONS_SEQ")
	private Long businessId;
	
	@Column(name="BUSINESS_ROLE")
	@NotBlank
	private String businessFunctions;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getBusinessFunctions() {
		return businessFunctions;
	}

	public void setBusinessFunctions(String businessFunctions) {
		this.businessFunctions = businessFunctions;
	}
	
	
}
