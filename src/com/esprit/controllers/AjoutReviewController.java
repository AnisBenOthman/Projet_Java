package com.esprit.controllers;

import com.esprit.entities.Review;
import com.esprit.services.ServiceReview;
import java.io.IOException;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;
import javafx.scene.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AjoutReviewController implements Initializable {
    @FXML
    private SVGPath star1;

    @FXML
    private SVGPath star2;

    @FXML
    private SVGPath star3;

    @FXML
    private SVGPath star4;

    @FXML
    private SVGPath star5;

    @FXML
    private Button b1;

    @FXML
    private TextArea tf_commentaire;

    private int user_id = 1;

    private int entreprise_id = 5;

    private final Color filledColor = Color.YELLOW;
    private final Color unfilledColor = Color.BLACK;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void selectStar(MouseEvent event) {
        SVGPath[] stars = new SVGPath[]{star1, star2, star3, star4, star5};
        SVGPath selectedStar = (SVGPath) event.getSource();

        for (SVGPath star : stars) {
            star.setFill(unfilledColor);
        }

        int i = -1;
        do {
            i++;
            stars[i].setFill(filledColor);
        } while (!selectedStar.equals(stars[i]));

        if (countStars() > 0) {
            b1.setDisable(false);
        } else {
            b1.setDisable(true);
        }
    }

    private int countStars() {
        int result = 0;
        for (SVGPath star : new SVGPath[]{star1, star2, star3, star4, star5}) {
            if (star.getFill().equals(filledColor)) {
                result++;
            }
        }

        return result;
    }

    @FXML
 private void ajouterReview(ActionEvent event) {
    ServiceReview sr = new ServiceReview();

    String commentaire = tf_commentaire.getText();
    Review review = new Review(countStars(), user_id, commentaire, entreprise_id);

    try {
        if (sr.containsBadWords(commentaire)) {
            JOptionPane.showMessageDialog(null, "Impossible d'ajouter la review. Elle contient des mots interdits. Veuillez fournir une autre review.");
        } else {
            sr.ajouter(review);
            JOptionPane.showMessageDialog(null, "Review ajoutée !");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la review !");
    }
 }
    @FXML
    private void mesReviews(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../review/ModifierReviews.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setTitle(loader.getController().getClass().getSimpleName());

                
        stage.setScene(scene);
        stage.show();
    }
}
