package com.foucsr.ticketmanager.payload;

/**
 * Created by FocusR on 29-Sep-2019.
 */

public class UpdateUserRequest {
    
    private String name;

   
    private String username;
    
    private String full_name;

  
    private String email;

    
    private String password;
    
    
   
    private String userRoles;
    
    private String orgname;
    
    private Long id;
    
    private String forgetPasswordUrl;
    
    private String token;
   

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getForgetPasswordUrl() {
		return forgetPasswordUrl;
	}

	public void setForgetPasswordUrl(String forgetPasswordUrl) {
		this.forgetPasswordUrl = forgetPasswordUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String vendorID;
    
	 private String agentId;
    

    public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getVendorID() {
		return vendorID;
	}

	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}

	public String getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
}
