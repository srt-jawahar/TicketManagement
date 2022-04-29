package com.foucsr.ticketmanager.mysql.database.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SCREENS")
public class Screens {
	@Id
	@SequenceGenerator(name = "SCREENS_SEQ", sequenceName = "SCREENS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCREENS_SEQ")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SCREEN_ID")
	private String screen_id;
	
	@Column(name = "SCREEN_NAME")
	private String screen_name;
	
	@Column(name = "description")
	private String description;

	public Screens() {
		
	}

	public Screens(Long id, String screen_id, String screen_name, String description) {
		this.id = id;
		this.screen_id = screen_id;
		this.screen_name = screen_name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(String screen_id) {
		this.screen_id = screen_id;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}