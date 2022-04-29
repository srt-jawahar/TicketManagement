package com.foucsr.ticketmanager.mysql.database.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS_SCREENS_MAP")
public class UsersScreensMap {
	@Id
	@SequenceGenerator(name = "USERS_SCREENS_MAP_SEQ", sequenceName = "USERS_SCREENS_MAP_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SCREENS_MAP_SEQ")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "USER_ID")
	private String user_id;
	
	@Column(name = "SCREEN_ID")
	private String screen_id;
	
	@Column(name = "IS_SHOW")
	private String is_show;

	public UsersScreensMap() {
		
	}

	public UsersScreensMap(Long id, String user_id, String screen_id, String is_show) {
		this.id = id;
		this.user_id = user_id;
		this.screen_id = screen_id;
		this.is_show = is_show;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(String screen_id) {
		this.screen_id = screen_id;
	}

	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
}