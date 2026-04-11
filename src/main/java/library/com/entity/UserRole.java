package library.com.entity;

public enum UserRole {
	ROLE_USER("user"),
	ROLE_ADMIN("admin");
	
	private String role;

	public String getRole() {
		return role;
	}
	private UserRole(String role) {
		this.role = role;
	}
}
