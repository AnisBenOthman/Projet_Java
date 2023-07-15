/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;
import com.esprit.entities.Question;
import com.esprit.controllers.GererQuestionController;
import com.esprit.entities.Proposition;
import com.esprit.entities.Competence;
import com.esprit.services.ServiceProposition;
import com.esprit.services.ServiceQuestion;
import com.esprit.services.ServiceCompetence;
import com.esprit.services.ServiceQuestion.QuestionView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Mayssen
 */
public class ListeQuizController implements Initializable {

private Competence selectedCompetence;
    @FXML
    private TableView<Competence> infocompetence;
    @FXML
    private TableColumn<Competence, String> colcompetence;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceCompetence sc = new ServiceCompetence();
        AfficherCompetence();
        infocompetence.setOnMouseClicked(event -> {
    if (event.getClickCount() == 2) {
        System.out.println("zz, "+infocompetence.getSelectionModel().getSelectedItem());
        afficherQuestionsCompetence(infocompetence.getSelectionModel().getSelectedItem(),event);
        
    }
});
    }   
    
    public void AfficherCompetence() {
    ServiceQuestion serviceQuestion = new ServiceQuestion();
    ServiceCompetence sc = new ServiceCompetence();
    ObservableList<Competence> competenceList = FXCollections.observableArrayList(sc.afficher()) ;
    infocompetence.setItems(competenceList);
    colcompetence.setCellValueFactory(new PropertyValueFactory<>("nom"));
    
}
    private void afficherQuestionsCompetence(Competence competence,MouseEvent event) {
      selectedCompetence = competence;
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/QuestionCompetence.fxml"));
        Parent root = loader.load();
        
       QuestionCompetenceController questionsController = loader.getController();
       questionsController.setIduser(id);
        
        questionsController.setCompetence(selectedCompetence);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}