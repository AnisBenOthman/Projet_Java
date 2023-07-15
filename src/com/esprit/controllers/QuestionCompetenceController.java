/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;
import com.esprit.entities.Question;
import com.esprit.entities.Proposition;
import com.esprit.entities.Competence;
import com.esprit.services.ServiceProposition;
import com.esprit.services.ServiceQuestion;
import com.esprit.services.ServiceCompetence;
import com.esprit.services.ServiceQuestion.QuestionView;
import com.esprit.controllers.ListeQuizController;
import com.esprit.entities.MailException;
import com.esprit.services.ServiceUser;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Mayssen
 */
public class QuestionCompetenceController implements Initializable {

    @FXML
    private Label lbCompetence;
    @FXML
    private Label lbQ1;
    @FXML
    private RadioButton rdQ11;
    @FXML
    private RadioButton rdQ12;
    @FXML
    private RadioButton rdQ13;
    @FXML
    private RadioButton rdQ14;
    @FXML
    private Label lbQ2;
    @FXML
    private RadioButton rdQ21;
    @FXML
    private RadioButton rdQ22;
    @FXML
    private RadioButton rdQ23;
    @FXML
    private RadioButton rdQ24;
    @FXML
    private Label lbQ3;
    @FXML
    private RadioButton rdQ31;
    @FXML
    private RadioButton rdQ32;
    @FXML
    private RadioButton rdQ33;
    @FXML
    private RadioButton rdQ34;
    @FXML
    private Label lbQ4;
    @FXML
    private RadioButton rdQ41;
    @FXML
    private RadioButton rdQ42;
    @FXML
    private RadioButton rdQ43;
    @FXML
    private RadioButton rdQ44;
    @FXML
    private Button btValider;
    @FXML
    private ToggleGroup Q1;
    @FXML
    private ToggleGroup Q2;
    @FXML
    private ToggleGroup Q3;
    @FXML
    private ToggleGroup Q4;
    private int iduser;

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

   
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //setCompetence(selectedCompetence);
    }    
 private Competence selectedCompetence;

    public void setCompetence(Competence competence) {
        selectedCompetence = competence;
       int id = competence.getId_c();
       
        ServiceProposition sp = new ServiceProposition();
        ServiceQuestion sq = new ServiceQuestion();
        ServiceCompetence sc = new ServiceCompetence();
        
        ObservableList<Question> questions = FXCollections.observableArrayList(sq.GetQuestionsByCompetences(id));

        
        if (questions.size() > 4) {
            
            Collections.shuffle(questions);
         
            questions = FXCollections.observableArrayList(questions.subList(0, 4));
        }
        
          lbCompetence.setText(sc.getNameCompetenceById(id).toUpperCase(Locale.ITALY));
       lbQ1.setText(questions.get(0).getLibelle());
        lbQ2.setText(questions.get(1).getLibelle());
        lbQ3.setText(questions.get(2).getLibelle());
        lbQ4.setText(questions.get(3).getLibelle());
         List<Proposition> prop1 = sp.afficherByIDquestion(sq.GetidQuestionbynom(lbQ1.getText()));
         List<Proposition> prop2 = sp.afficherByIDquestion(sq.GetidQuestionbynom(lbQ2.getText()));
        List<Proposition> prop3 = sp.afficherByIDquestion(sq.GetidQuestionbynom(lbQ3.getText()));
       List<Proposition> prop4 = sp.afficherByIDquestion(sq.GetidQuestionbynom(lbQ4.getText()));
         if (prop1.size() >= 1) {
         Proposition p11 = prop1.get(0);
        rdQ11.setText(p11.getDescription());
       
         }
         
         if (prop1.size() >= 2) {
         Proposition p12 = prop1.get(1);
        rdQ12.setText(p12.getDescription());
         }
         
         if (prop1.size() >= 3) {
         Proposition p13= prop1.get(2);
        rdQ13.setText(p13.getDescription());
         }
         
         if (prop1.size() >= 4) {
         Proposition p14 = prop1.get(3);
        rdQ14.setText(p14.getDescription());
         }
         
         
         if (prop2.size() >= 1) {
         Proposition p21 = prop2.get(0);
        rdQ21.setText(p21.getDescription());
        
         }
         
         if (prop2.size() >= 2) {
         Proposition p22 = prop2.get(1);
        rdQ22.setText(p22.getDescription());
         }
         
         if (prop2.size() >= 3) {
         Proposition p23= prop2.get(2);
        rdQ23.setText(p23.getDescription());
         }
         
         if (prop2.size() >= 4) {
         Proposition p24 = prop2.get(3);
        rdQ24.setText(p24.getDescription());
         }
         
         
         if (prop3.size() >= 1) {
         Proposition p31 = prop3.get(0);
        rdQ31.setText(p31.getDescription());
       
         }
         
         if (prop3.size() >= 2) {
         Proposition p32 = prop3.get(1);
        rdQ32.setText(p32.getDescription());
         }
         
         if (prop3.size() >= 3) {
         Proposition p33= prop3.get(2);
        rdQ33.setText(p33.getDescription());
         }
         
         if (prop3.size() >= 4) {
         Proposition p34 = prop3.get(3);
        rdQ34.setText(p34.getDescription());
         }
         
         
         if (prop4.size() >= 1) {
         Proposition p41 = prop4.get(0);
        rdQ41.setText(p41.getDescription());
       
         }
         
         if (prop4.size() >= 2) {
         Proposition p42 = prop4.get(1);
        rdQ42.setText(p42.getDescription());
         }
         
         if (prop4.size() >= 3) {
         Proposition p43= prop4.get(2);
        rdQ43.setText(p43.getDescription());
         }
         
         if (prop4.size() >= 4) {
         Proposition p44 = prop4.get(3);
        rdQ44.setText(p44.getDescription());
         }
         
         
          btValider.setOnAction(event -> {
        boolean isQ1Correct = isPropositionCorrect(prop1, Q1);
        boolean isQ2Correct = isPropositionCorrect(prop2, Q2);
        boolean isQ3Correct = isPropositionCorrect(prop3, Q3);
        boolean isQ4Correct = isPropositionCorrect(prop4, Q4);

        int score = 0;
    String R1 = "Votre réponse n'est pas Correcte";
   String R2 = "Votre réponse n'est pas Correcte";
   String R3 = "Votre réponse n'est pas Correcte";
   String R4 = "Votre réponse n'est pas Correcte";
   
    if (isQ1Correct) {
        score += 25;
        R1 = "Votre réponse est correcte";
    }  if (Q1.getSelectedToggle() == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Question 1");
        alert.setContentText("Veuillez sélectionner une proposition pour la question 1.");
        alert.showAndWait();
        return; 
    }

    if (isQ2Correct) {
        score += 25;
         R2 = "Votre réponse est correcte";
    }if (Q2.getSelectedToggle() == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Question 2");
        alert.setContentText("Veuillez sélectionner une proposition pour la question 2.");
        alert.showAndWait();
        return; 
    }
    if (isQ3Correct) {
        score += 25;
         R3 = "Votre réponse est correcte";
    }if (Q3.getSelectedToggle() == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Question 3");
        alert.setContentText("Veuillez sélectionner une proposition pour la question 3.");
        alert.showAndWait();
        return; 
    }

    if (isQ4Correct) {
        score += 25;
         R4 = "Votre réponse est correcte";
    }if (Q4.getSelectedToggle() == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Question 4");
        alert.setContentText("Veuillez sélectionner une proposition pour la question 4.");
        alert.showAndWait();
        return; 
    }
        
        
         
         try {
             
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("resultat_quiz_"+ lbCompetence.getText()+ ".pdf"));
        document.open();
       
       Paragraph Titre = new Paragraph ("RESULTAT DU QUIZ " + lbCompetence.getText().toUpperCase());
       Titre.setAlignment(Element.ALIGN_CENTER);
       document.add(Titre);
        document.add(new Paragraph("                             "));
        document.add(new Paragraph("                             "));
        document.add(new Paragraph("Question 1 : " + lbQ1.getText()+ "  " + R1));
        document.add(new Paragraph("La bonne réponse est : " + sp.BonnereponseByIDquestion(sq.GetidQuestionbynom(lbQ1.getText()))));
        document.add(new Paragraph("                             "));
        document.add(new Paragraph("Question 2 : " + lbQ2.getText()+ "  " + R2));
        document.add(new Paragraph("La bonne réponse est : " + sp.BonnereponseByIDquestion(sq.GetidQuestionbynom(lbQ2.getText()))));
        document.add(new Paragraph("                             "));
        document.add(new Paragraph("Question 3 : " + lbQ3.getText()+ "  " + R3));
        document.add(new Paragraph("La bonne réponse est : " + sp.BonnereponseByIDquestion(sq.GetidQuestionbynom(lbQ3.getText()))));
        document.add(new Paragraph("                             "));
        document.add(new Paragraph("Question 4 : " + lbQ4.getText()+ "  " + R4));
        document.add(new Paragraph("La bonne réponse est : " + sp.BonnereponseByIDquestion(sq.GetidQuestionbynom(lbQ4.getText()))));
        document.add(new Paragraph("                             "));
        document.add(new Paragraph("Votre Score est : " + score + "/100"));
        document.close();

        System.out.println("Le fichier PDF a été généré avec succès.");
    } catch (DocumentException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
              ServiceUser su = new ServiceUser();
         String senderEmail = "kacemtaieb@gmail.com";
   String senderPassword = "alqtmoozbqpbasvy";
String recipientEmail = "kacemtaieb@gmail.com";
            try {
                recipientEmail = su.getMailbyId(iduser);
            } catch (MailException ex) {
                Logger.getLogger(QuestionCompetenceController.class.getName()).log(Level.SEVERE, null, ex);
            }
sendPDFByEmail("resultat_quiz_"+ lbCompetence.getText()+".pdf",senderEmail,senderPassword,recipientEmail);
    rdQ11.setSelected(false);
    rdQ12.setSelected(false);
    rdQ13.setSelected(false);
    rdQ14.setSelected(false);
      rdQ21.setSelected(false);
    rdQ22.setSelected(false);
    rdQ23.setSelected(false);
    rdQ24.setSelected(false);  
    rdQ31.setSelected(false);
    rdQ32.setSelected(false);
    rdQ33.setSelected(false);
    rdQ34.setSelected(false);
    rdQ41.setSelected(false);
    rdQ42.setSelected(false);
    rdQ43.setSelected(false);
    rdQ44.setSelected(false);
    });
    }
    
    private boolean isPropositionCorrect(List<Proposition> propositions, ToggleGroup toggleGroup) {
    RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
    int score = 0;
    if (selectedRadioButton != null) {
        String selectedProposition = selectedRadioButton.getText();
        for (Proposition prop : propositions) {
            if (prop.getDescription().equals(selectedProposition) && prop.getEtat().equals("vrai")) {
                return true;
                            }
        }
    }
    return false;
       
}

  private void sendPDFByEmail(String filePath, String senderEmail, String senderPassword, String recipientEmail) {
    String host = "smtp.gmail.com";
    int port = 587;

   
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);

    
    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    });

    try {
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Resultat QUIZ");
        
       
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(filePath);

        
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Veuillez trouver ci-joint le document PDF.");

        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        
        Transport.send(message);

        System.out.println("Le document PDF a été envoyé par e-mail avec succès.");
    } catch (MessagingException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  
   

}
