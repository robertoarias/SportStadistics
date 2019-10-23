package com.sports.frontend.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class UserInfoLDap extends org.springframework.security.core.userdetails.User {
	
	private static final long serialVersionUID = -6411988532329234916L;
	private String userId;
	private String userName;
	private String roleName;
	private String userMail;
	private String pathToPic;
	private String roleId;
	private String action;
	private String password;
	
	public UserInfoLDap(String username, String password, String pathToPic,
			Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.pathToPic = pathToPic;
    }

	
	public UserInfoLDap(String username, String password, String userId, String userName, String roleName,
			String userMail, String pathToPic, String roleId, String action, //String password,
			Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.pathToPic = pathToPic;
    }
	
	
	@Override
	public String toString() {
		String res ="";
		res +="UserId= "+userId;
		res +=" - UserName= "+userName;
		res +=" - roleName= "+roleName;
		res +=" - userMail= "+userMail;
		res +=" - pathToPic= "+pathToPic;
		return res;
	}

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserMail() {
		return userMail;
	}


	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}


	public String getPathToPic() {
		return pathToPic;
	}


	public void setPathToPic(String pathToPic) {
		this.pathToPic = pathToPic;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
