package org.eclipse.services;

public class InscriptionService {
	
	public boolean verificationNomPrenom(String valeur) {
		if(valeur.equals(null) || valeur.length() < 2) return false;
		if(valeur.charAt(0) < 'A' || valeur.charAt(0) > 'Z') return false;
		return true;
	}
	
	public boolean verificationEmail(String email) {
		if(email.equals(null) || email.length() < 5) return false;
		if(!email.contains("@")) return false;
		return true;
	}

}
