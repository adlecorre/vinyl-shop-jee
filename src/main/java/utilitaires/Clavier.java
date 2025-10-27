package utilitaires;

import java.util.Scanner;

public final class Clavier {
    private static final Scanner INSTANCE = new Scanner(System.in);

    private Clavier() {}

    public static Scanner getScanner() {
        return INSTANCE;
    }

    public static void fermer() {
        INSTANCE.close();
    }
    
    public static String lireLigne(String prompt) {
        if (prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }
        return INSTANCE.nextLine();
    }

    public static int lireInt(String prompt, int valeurParDefaut) {
        while (true) {
            if (prompt != null && !prompt.isEmpty()) System.out.print(prompt);
            String ligne = INSTANCE.nextLine().trim();
            if (ligne.equalsIgnoreCase("q")) return valeurParDefaut;
            try {
                return Integer.parseInt(ligne);
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide — veuillez saisir un entier (ou 'q' pour annuler).");
            }
        }
    }
}

