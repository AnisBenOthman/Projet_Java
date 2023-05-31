/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controller;

import com.esprit.entities.*;
import com.esprit.services.ServiceCompetence;
import com.esprit.services.ServiceDomaine;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class AjoutCandidatController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfmotdepasse;
    @FXML
    private TextField tfmotdepasse2;
    @FXML
    private TextField tftelephone;
    @FXML
    private TextField tfgithub;
    @FXML
    private TextArea tadescription;
    @FXML
    private ComboBox<Experience> cbexperience;
    @FXML
    private ComboBox<Diplome> cbdiplome;
    @FXML
     private ListView<String> listcompetence; 
    @FXML
    private Button envoye;
    
    private Refresh refreshEvent;
   
    public void setRefreshEvent(Refresh refreshListener) {
    this.refreshEvent = refreshListener;
}
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceCompetence sc = new ServiceCompetence();
        cbdiplome.setItems(FXCollections.observableArrayList(Diplome.values()));
        cbexperience.setItems(FXCollections.observableArrayList(Experience.values()));
        listcompetence.setItems(FXCollections.observableArrayList(sc.affichercompetencebynom()));
        listcompetence.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }   
    public void triggerRefreshEvent() {
        if (refreshEvent != null) {
            refreshEvent.onRefresh();
        }
     }
     @FXML
    public void ajouterCandidat(ActionEvent event) throws IOException {
        if(tfmotdepasse.getText().equals(tfmotdepasse2.getText())){
        ServiceUser sp = new ServiceUser();
        sp.ajouter(new Candidat(tfnom.getText(), tfprenom.getText(), tfadresse.getText(), Integer.parseInt(tftelephone.getText()), tfmotdepasse.getText(), tadescription.getText(),cbdiplome.getValue(),tfgithub.getText(),cbexperience.getValue()));
        JOptionPane.showMessageDialog(null, "Candidat ajouté !");
        triggerRefreshEvent();
        }
        else {
            JOptionPane.showMessageDialog(null, "Mot de passe erronée");
        }
    }
    
}
