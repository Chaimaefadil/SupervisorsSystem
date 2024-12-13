package com.example.supervisorssystem.Controller;

import com.example.supervisorssystem.Model.Membre;
import com.example.supervisorssystem.DAO.MembreDaoImpl;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javafx.stage.FileChooser;

import java.io.File;

import com.example.supervisorssystem.DAO.MembreDaoImpl;

public class MembreFormController {
    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telephoneField;

    @FXML
    private Button insertButton;
    @FXML
    private Button importCSVButton;
    @FXML
    private TextArea textArea;
    private final Set<Membre> membres = new HashSet<>();

    // Instance de DAO pour les interactions avec la base de données
    private MembreDaoImpl membreDao = new MembreDaoImpl();

    @FXML
    void handleInsertButton() {
        // Récupération des données des champs
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String telephone = telephoneField.getText().trim();

        // Validation des données
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        if (!email.contains("@")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir un email valide.");
            return;
        }

        // Création de l'objet Membre
        String id = java.util.UUID.randomUUID().toString();
        Membre membre = new Membre();
        membre.setid(id);
        membre.setNom(nom);
        membre.setPrenom(prenom);
        membre.setEmail(email);
        membre.setTelephone(Long.parseLong(telephone));

        // Insertion du membre dans la base de données
        try {
            membreDao.inserer(membre);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le membre a été ajouté avec succès !");
            clearFields(); // Réinitialiser les champs après l'ajout
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout du membre.");
        }
    }

    // Méthode utilitaire pour afficher une boîte de dialogue
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode utilitaire pour vider les champs
    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        telephoneField.clear();
    }

    @FXML
    public void handleImportCSV() {
        // Ouvrir un sélecteur de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("member_sample", "*.csv"));
        File fichier = fileChooser.showOpenDialog(null);

        if (fichier != null) {
            try {
                // Charger les membres à partir du fichier CSV
                Set<Membre> membresImportes = chargerListeMembre(fichier.getAbsolutePath());

                // Ajouter les membres à la liste existante et mettre à jour l'affichage
                for (Membre membre : membresImportes) {
                    if (membres.add(membre)) {
                        textArea.appendText(membre + "\n");
                    }
                }
            } catch (IOException e) {
                textArea.setText("Erreur lors de l'importation du fichier : " + e.getMessage());
            }
        }
    }
    public Set<Membre> chargerListeMembre(String nomFichier) throws IOException {
        Set<Membre> membres = new HashSet<>(); // Utilisation d'un Set pour éliminer automatiquement les doublons

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;

            // Lire chaque ligne du fichier CSV
            while ((ligne = br.readLine()) != null) {
                String[] donnees = ligne.split(","); // Supposons que le CSV est délimité par des virgules

                // Assurez-vous que la ligne contient le bon nombre de colonnes
                if (donnees.length == 5) {
                    Membre membre = new Membre(
                            donnees[0], // Identifiant
                            donnees[1], // Nom
                            donnees[2], // Prénom
                            donnees[3], // Email
                            Long.parseLong(donnees[4]) // Téléphone
                    );
                    membres.add(membre); // Ajout au Set (élimine les doublons)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return membres;
    }

}
