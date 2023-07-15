package com.esprit.controllers;

import com.esprit.entities.MailException;
import com.esprit.entities.Review;
import com.esprit.services.ServiceOffre;

import com.esprit.services.ServiceReview;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ModifierReviewsController implements Initializable {
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
    private ListView lv1;

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private TextArea tf_commentaire;
    
    private ServiceReview sr;
    
    private ServiceUser su;
    
    private ServiceOffre so;

    private final Color filledColor = Color.YELLOW;
    private final Color unfilledColor = Color.BLACK;

    public ModifierReviewsController() {
        sr = new ServiceReview();
        su = new ServiceUser();
        so = new ServiceOffre();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillViewOptions();
    }

    private void fillViewOptions() {
        try {
            List<Review> reviews = sr.afficher();

            for (Review rev : reviews) {
                String output = su.getUserByID(rev.getId_entreprise()).getNom() + "      " + rev.getNbr_etoile() + "/5";

                rev.setOutput(output);
            }

            ObservableList<Review> data = FXCollections.observableArrayList();
            data.addAll(reviews);

            lv1.setItems(data);
        } catch (SQLException | MailException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'affichage des reviews !");
        }
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
    void selectViewOption(MouseEvent event) {
        SVGPath[] stars = new SVGPath[]{star1, star2, star3, star4, star5};
        for (SVGPath star : stars) {
            star.setFill(unfilledColor);
            star.setDisable(false);
        }

        Review selectedOption = (Review) lv1.getSelectionModel().getSelectedItem();

        tf_commentaire.setText(selectedOption.getCommentaire());

        for (int i = 0; i < selectedOption.getNbr_etoile(); i++) {
            SVGPath star = stars[i];
            star.setFill(filledColor);
        }

        b1.setDisable(false);
        b2.setDisable(false);

        tf_commentaire.setDisable(false);
    }

@FXML
private void modifier(ActionEvent event) throws SQLException {
    Review selectedOption = (Review) lv1.getSelectionModel().getSelectedItem();

    String commentaire = tf_commentaire.getText();

    ServiceReview sr = new ServiceReview();
    if (sr.containsBadWords(commentaire)) {
        JOptionPane.showMessageDialog(null, "Le commentaire contient des mots interdits !");
        return;
    }

    selectedOption.setCommentaire(commentaire);
    selectedOption.setNbr_etoile(countStars());

    try {
        sr.modifier(selectedOption);
        JOptionPane.showMessageDialog(null, "Review modifiée !");

        lv1.getSelectionModel().setSelectionMode(null);
        fillViewOptions();

        b1.setDisable(true);
        b2.setDisable(true);

        tf_commentaire.setText(null);
        tf_commentaire.setDisable(true);

        SVGPath[] stars = new SVGPath[]{star1, star2, star3, star4, star5};

        for (SVGPath star : stars) {
            star.setFill(unfilledColor);
            star.setDisable(true);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erreur lors de la modification de la review !");
    }
}


    @FXML
    private void supprimer(ActionEvent event) {
        Review selectedOption = (Review) lv1.getSelectionModel().getSelectedItem();
        
        try {
            sr.supprimer(selectedOption);

            JOptionPane.showMessageDialog(null, "Review supprimée !");

            lv1.getSelectionModel().setSelectionMode(null);
            fillViewOptions();

            b1.setDisable(true);
            b2.setDisable(true);

            tf_commentaire.setText(null);
            tf_commentaire.setDisable(true);

            SVGPath[] stars = new SVGPath[]{star1, star2, star3, star4, star5};

            for (SVGPath star : stars) {
                star.setFill(unfilledColor);
                star.setDisable(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la review !");
        }
    }
    
    @FXML
    private void ajoutReviews(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../review/AjoutReview.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.setTitle(loader.getController().getClass().getSimpleName());

        stage.show();
    }
}
