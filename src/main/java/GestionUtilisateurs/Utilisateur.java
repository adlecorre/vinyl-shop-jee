package GestionUtilisateurs;

import java.util.Date;
 
public class Utilisateur {
	private int id;
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private String email;
	private String adresse;
	private String numTel;
	private Role role;
	private String motDePasse;
	
	public Utilisateur() {
		
	}
	
	public Utilisateur(String nom, String prenom, String email, String mdp, Role role) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.role = role;
		this.motDePasse = mdp;
	}
	public Utilisateur(String nom, String prenom, Date dateNaissance, String email, String adresse, String numTel,
			Role role, String motDePasse) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.adresse = adresse;
		this.numTel = numTel;
		this.role = role;
		this.motDePasse = motDePasse;
	}
	
	
	
	public Utilisateur(int id, String nom, String prenom, Date dateNaissance, String email, String adresse,
			String numTel, Role role, String motDePasse) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.adresse = adresse;
		this.numTel = numTel;
		this.role = role;
		this.motDePasse = motDePasse;
	}



	public Utilisateur(int id, String nom, String prenom, String email, Role role) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.role = role;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getNumTel() {
		return numTel;
	}
	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance
				+ ", email=" + email + ", adresse=" + adresse + ", numTel=" + numTel + ", role=" + role + ", motPasse="
				+ motDePasse + "]";
	}

	
//connexion 
	public Boolean seConnecter(String email, String motPasse) {
			
		return (true);
		
	};
	
}

