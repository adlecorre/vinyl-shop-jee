package utilitaires;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
	private static Connection connexion;
	
	public static Connection getConnection() {
		if(connexion == null) {
			final String URL = "jdbc:mysql://localhost:3306/bd_vinyle";
			final String USER = "app";
			final String PSW = "app";
			try { 
				connexion = DriverManager.getConnection(URL,USER,PSW);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connexion;
	}
	
	 public static void fermerConnexion() {
	        try {
	            if (connexion != null && !connexion.isClosed()) {
	                connexion.close();
	                System.out.println("🔒 Connexion fermée.");
	            }
	        } catch (SQLException e) {
	            System.err.println("⚠️ Erreur lors de la fermeture de la connexion : " + e.getMessage());
	        }
	    }

}
