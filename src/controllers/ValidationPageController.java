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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ESDRAS
 */
public class ValidationPageController implements Initializable {
    private Stage stage;
    @FXML
    private JFXTextField newU;
    @FXML
    private JFXTextField newP;
    @FXML
    private JFXTextField oldU;
    @FXML
    private JFXTextField oldP;
    public ValidationPageController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private JFXButton validateButton;
    @FXML
    private JFXButton cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void changeSettings(ActionEvent event) {
        if(AppliDeLocation.baseDonnee.changeSettings(oldU.getText(), oldP.getText(), newU.getText(), newP.getText())){
            Alertes.information("Successful Operation", "Your username or password have been changed");
            stage.close();
        }
        else {
            Alertes.alerte("Error", "The operation failed, please retry");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        stage.close();
    }
    
}
