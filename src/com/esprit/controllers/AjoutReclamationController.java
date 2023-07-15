package com.esprit.controllers;

import com.esprit.entities.Email;
import com.esprit.entities.EtatReclamation;
import com.esprit.entities.MailException;
import com.esprit.entities.Reclamation;
import com.esprit.services.ServiceOffre;
import com.esprit.services.ServiceReclamation;
import com.esprit.services.ServiceUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

public class AjoutReclamationController implements Initializable {

    @FXML
    private TextArea tf_reclamation;

    @FXML
    private Text rclname;

    @FXML
    private Text recid;

    @FXML
    private Text rclstat;

    @FXML
    private Text recstat;

    @FXML
    private ListView<Reclamation> lv1;

    @FXML
    private Button b1;
    
    private ServiceReclamation sr;
    
    private ServiceUser su;
    
    private ServiceOffre so;

    private int id_user;
    
    private int id_offre;
    
    public AjoutReclamationController() {
        sr = new ServiceReclamation();
        su = new ServiceUser();
        so = new ServiceOffre();
    }
    
    @FXML
    void selectViewOption(MouseEvent event) {
        Reclamation selectedOption = lv1.getSelectionModel().getSelectedItem();

        if (selectedOption != null) {
            tf_reclamation.setText(selectedOption.getReclamation());

            tf_reclamation.setEditable(false);

            b1.setText("Retour");
            b1.setOnAction(e -> clearButton());

            rclname.setText(String.valueOf(selectedOption.getId_reclamation()));
            rclstat.setText(String.valueOf(selectedOption.getEtat()));

            recid.setVisible(true);
            recstat.setVisible(true);
        }
    }

    @FXML
    private void ajouterReclamation(ActionEvent event) throws IOException {
        String message = tf_reclamation.getText().trim(); // Trim leading/trailing whitespace
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Commentaire ne peut pas être vide !");
            return; // Stop execution if comment is empty
        }
        
        Reclamation reclamation = new Reclamation(0, message, id_user, EtatReclamation.En_cours, id_offre);

        try {
            sr.ajouter(reclamation);
            JOptionPane.showMessageDialog(null, "Reclamation ajoutée!");

            List<Reclamation> reclamations = sr.afficher();
            JOptionPane.showMessageDialog(null, "Envoie du mail en cours");
            Reclamation rec = reclamations.get(reclamations.size() - 1);
            
            int id = rec.getId_reclamation();
            String mail = su.getUserByID(rec.getId_user()).getMail();

            Email.sendMail(mail, "REF " + id,
                    Email.envoiReclamationMessage(su.getUserByID(rec.getId_user()).getNom()));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la validation!");
        } catch (MessagingException | MailException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'envoie du mail!");
        }

        lv1.getSelectionModel().clearSelection();
        tf_reclamation.setText("");
    }

    private void clearButton() {
        b1.setText("Envoyer");
        b1.setOnAction(e -> {
            try {
                ajouterReclamation(e);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la validation!");
            }
        });

        lv1.getSelectionModel().clearSelection();

        tf_reclamation.setText("");
        tf_reclamation.setEditable(true);

        rclname.setText(null);
        recid.setVisible(false);

        rclstat.setText(null);
        recstat.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillViewOptions();
    }

    private void fillViewOptions() {
        try {
            List<Reclamation> reclamations = sr.getReclamationEnCours(id_user, id_offre);

            for (Reclamation rec : reclamations) {
                String output = so.chercherOffreByID(rec.getId_offre()).getTitre() + "      " + rec.getEtat();

                rec.setOutput(output);
            }

            ObservableList<Reclamation> data = FXCollections.observableArrayList();
            data.addAll(reclamations);

            lv1.setItems(data);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la validation!");
        }
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }
}
