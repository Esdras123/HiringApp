/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appliLocation.Alertes;
import appliLocation.AppliDeLocation;
import appliLocation.FactureClientInfos;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ESDRAS
 */
public class PageInfosController implements Initializable {
    private String nom;
    private Stage stage;
    @FXML
    private Label clientInfos;
    @FXML
    private TableView<FactureClientInfos> tableInfos;
    @FXML
    private TableColumn<FactureClientInfos, Integer> amountClient;
    @FXML
    private TableColumn<FactureClientInfos, Date> paidtheClient;
    @FXML
    private TableColumn<FactureClientInfos, Date> fromClient;
    @FXML
    private TableColumn<FactureClientInfos, Date> toClient;
    @FXML
    private TableColumn<FactureClientInfos, String> typeClient;
    private int code;
    public PageInfosController(Stage stage, Integer code, String nom) {
        this.code = code;
        this.nom = nom;
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chargerTableFacture();
        clientInfos.setText(nom);
        remplirTable(code);
    }

    private void remplirTable(int code) {
        ArrayList<FactureClientInfos> liste = AppliDeLocation.baseDonnee.afficheInfosClient(code);
        if (liste == null) {
            Alertes.alerte("Operation Failed", "Please retry");
        } else {
            tableInfos.getItems().addAll(liste);
        }

    }

    private void chargerTableFacture() {
        amountClient.setCellValueFactory(new PropertyValueFactory<>("amount"));
        paidtheClient.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
        fromClient.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        toClient.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        typeClient.setCellValueFactory(new PropertyValueFactory<>("motif"));
    }
}
