package com.foucsr.ticketmanager.mysql.database.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.foucsr.ticketmanager.model.audit.DateAudit;

/**
 * Created by FocusR.
 */

@Entity
@Table(name = "USER_LOGIN_DETAILS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User  extends DateAudit{
    @Id
    @SequenceGenerator(name = "USER_LOGIN_DETAILS_SEQ", sequenceName = "USER_LOGIN_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_LOGIN_DETAILS_SEQ")
    private Long id;

    @NotBlank
    @Size(max = 250)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String username;
    
    @Column(name="FULL_NAME")
	private String full_name;
    
    @Column(name="IS_SUPER_USER")
   	private String is_super_user;
    
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(max = 500)
    private String password;
    
    private String orgname;
    
    private String vendorID;
    
    private String agentId;
    
    private char supplier_flag;
    private char buyer_flag;
    
    private char is_active;
    
    @Column(name = "reset_token" , length = 2000)
	private String resetToken;
    
	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getVendorID() {
		return vendorID;
	}

	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}

	//@ManyToMany(fetch = FetchType.LAZY)
	@ManyToMany(cascade =CascadeType.ALL)
	//@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "ROLES_MAP_BY_USERS",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }      

    
    public User(String name, String username, String email, String password ,
    		        String orgname,String vendorID,String agentId, char is_active) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.orgname = orgname;
        this.vendorID = vendorID;
		this.agentId=agentId;
		this.is_active = is_active;
    }


   
	public User(Long id, @NotBlank @Size(max = 40) String name, @NotBlank @Size(max = 15) String username,
			@NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password, String orgname,
			String vendorID, String agentId, char supplier_flag, char buyer_flag, Set<Role> roles , char is_active) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.orgname = orgname;
		this.vendorID = vendorID;
		this.agentId = agentId;
		this.supplier_flag = supplier_flag;
		this.buyer_flag = buyer_flag;
		this.roles = roles;
		this.is_active = is_active;
	}

	public char getSupplier_flag() {
		return supplier_flag;
	}

	public void setSupplier_flag(char supplier_flag) {
		this.supplier_flag = supplier_flag;
	}

	public char getBuyer_flag() {
		return buyer_flag;
	}

	public void setBuyer_flag(char buyer_flag) {
		this.buyer_flag = buyer_flag;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public char getIs_Active() {
		return is_active;
	}

	public void setIs_Active(char is_active) {
		this.is_active = is_active;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", orgname=" + orgname + ", vendorID=" + vendorID + ", agentId=" + agentId
				+ ", supplier_flag=" + supplier_flag + ", buyer_flag=" + buyer_flag
				+ ", roles=" + roles + "]";
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getIs_super_user() {
		return is_super_user;
	}

	public void setIs_super_user(String is_super_user) {
		this.is_super_user = is_super_user;
	}

	
}