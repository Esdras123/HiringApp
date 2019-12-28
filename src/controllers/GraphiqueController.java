/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appliLocation.Alertes;
import appliLocation.AppliDeLocation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Event;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author ESDRAS
 */

public class GraphiqueController implements Initializable {
    private Label label;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXButton loginButton;
    
    public GraphiqueController(){
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void chargerVue(String vue, String nom){
        FXMLLoader loader = new FXMLLoader();
        try{
            loader.setLocation(getClass().getResource(vue));
            loader.setControllerFactory(c->{return new AccueilController(nom);});
            Scene scene = new Scene(loader.load());
            AppliDeLocation.stage.setScene(scene);
            AppliDeLocation.stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
            Alertes.alerte("Error", "Please retry");
        }
    }
    @FXML
    private void login() {
        if (AppliDeLocation.baseDonnee.validUser(username.getText(), password.getText())) {
            chargerVue("/vues/accueil.fxml", username.getText());
        }
        else{
            Alertes.alerte("Invalid Username and/or Password", "Enter the information correctly");
        }
    }

    @FXML
    private void verifier(KeyEvent event) {
        if(event.getCode().equals(Event.ENTER)){
            login();
        }
    }
    
}
