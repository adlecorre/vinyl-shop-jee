package GestionUtilisateurs;

public enum Role {
	CLIENT, ADMIN ;
	
	public static Role fromString(String value) {
        if (value == null) return CLIENT;
        switch (value.toUpperCase()) {
            case "ADMIN": return ADMIN;
            default: return CLIENT;
        }
    }
}
