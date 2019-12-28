/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appliLocation.Alertes;
import appliLocation.AppliDeLocation;
import appliLocation.Locataire;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ESDRAS
 */
public class PagemodifController implements Initializable {

    private Stage stage;

    @FXML
    private JFXTextField firstname;
    @FXML
    private JFXTextField lastname;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXComboBox<String> gender;
    @FXML
    private JFXTextField mail;
    @FXML
    private JFXDatePicker birthdate;
    @FXML
    private JFXTextField job;
    @FXML
    private JFXButton modify;
    private int code;
    private ObservableList<Locataire> liste;
    private String nom, prenom, telephone, email, jobClient, genderClient;
    private LocalDate birth;
    public PagemodifController(Stage stage,ObservableList<Locataire> liste, Integer code, String nom, String prenom, String telephone, String email, String jobClient, Date birthdate, String genderClient) {
        this.liste = liste;
        this.code = code;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.jobClient = jobClient;
        this.birth = birthdate.toLocalDate();
        this.stage = stage;
        this.genderClient = genderClient;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modify.setDisable(false);
        firstname.setText(nom);
        lastname.setText(prenom);
        phone.setText(telephone);
        mail.setText(email);
        job.setText(jobClient);
        this.birthdate.setValue(birth);
        gender.getItems().addAll("Male", "Female");
        gender.getSelectionModel().select(genderClient);
    }

    @FXML
    private void modifier(ActionEvent event) {
        if (valider(firstname.getText(), lastname.getText(), gender.getSelectionModel().getSelectedItem(), job.getText(), 
                mail.getText(), phone.getText(), birthdate.getValue().toString())) {
            if(AppliDeLocation.baseDonnee.modifierClient(firstname.getText(), lastname.getText(),
                    gender.getSelectionModel().getSelectedItem(), job.getText(), mail.getText(),
                    phone.getText(), Date.valueOf(birthdate.getValue()), code)){
                Locataire loc = fouille(code);
                if (loc != null) {
                    loc.setBirthdate(Date.valueOf(birthdate.getValue()));
                    loc.setJob(job.getText());
                    loc.setMail(mail.getText());
                    loc.setNom(firstname.getText());
                    loc.setPhone(phone.getText());
                    loc.setPrenom(lastname.getText());
                    loc.setSexe(gender.getSelectionModel().getSelectedItem());
                }
                Alertes.information("Successful operation", "The information of the client has been modified");
                stage.close();
            }
        }
        
    }
    private Locataire fouille(int code){
        for(Locataire loc : liste){
            if(loc.getCode() == code)
                return loc;
        }
        return null;
    }
    private boolean valider(String... liste) {
        for (String val : liste) {
            if (val.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
