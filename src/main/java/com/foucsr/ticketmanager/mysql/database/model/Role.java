package com.foucsr.ticketmanager.mysql.database.model;

import org.hibernate.annotations.NaturalId;
import javax.persistence.*;

/**
 * Created by FocusR on 01/08/17.
 */
@Entity
@Table(name = "USER_ROLES")
public class Role {
	 @Id
	 @SequenceGenerator(name = "USER_ROLES_SEQ", sequenceName = "USER_ROLES_SEQ", allocationSize = 1)
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ROLES_SEQ")
    private Long id;

    @Column(length = 60)
    private String name;
    
    @Column(name="DESCRIPTION")
	private String description;

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

}
