package org.eclipse.rest;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
	
	private static final Map<String, UserInfo> USERS = new HashMap<>();
	static {
		USERS.put("user", new UserInfo("user", "api-user"));
		USERS.put("admin", new UserInfo("admin", "api-admin"));
	}
	
	public UserInfo authentificate(String userName, String password) {
		UserInfo u = USERS.get(userName);
		if (u == null) return null;
		if (!u.password().equals(password)) return null;
		return u;
	}
	
	public record UserInfo(String password, String role) {}
	
}
