/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appliLocation.Alertes;
import appliLocation.AppliDeLocation;
import appliLocation.Facture;
import appliLocation.Locataire;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import envoiMail.Mailer;
import genererPDF.BillGenerator;
import genererPDF.PDFGenerator;
import genererPDF.imprimerPDF;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ESDRAS
 */
public class AccueilController implements Initializable {

    @FXML
    private TableView<Locataire> tableLocataire;
    @FXML
    private JFXTextField firstname;
    @FXML
    private JFXTextField lastname;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXComboBox<String> gender = new JFXComboBox<String>();
    @FXML
    private JFXTextField mail;
    @FXML
    private JFXDatePicker birthdate;
    @FXML
    private JFXTextField job;
    @FXML
    private JFXButton add;
    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton showInfo;
    @FXML
    private TableView<Facture> tableFactures;
    @FXML
    private JFXButton printButton;
    @FXML
    private JFXButton changeSettings;
    @FXML
    private JFXButton disconnect;
    @FXML
    private JFXButton modify;
    @FXML
    private JFXTextField amount;
    private String other = "Other";
    @FXML
    private JFXComboBox<String> type = new JFXComboBox<String>();
    @FXML
    private JFXDatePicker dateBegin;
    @FXML
    private JFXButton addBill;
    @FXML
    private JFXComboBox<String> billName;
    @FXML
    private JFXDatePicker dateEnd;
    @FXML
    private JFXTextField typeOther;
    @FXML
    private TableColumn<Locataire, Integer> codeClient;
    @FXML
    private TableColumn<Locataire, String> firstnameClient;
    @FXML
    private TableColumn<Locataire, String> lastnameClient;
    @FXML
    private TableColumn<Locataire, String> genderClient;
    @FXML
    private TableColumn<Locataire, String> jobClient;
    @FXML
    private TableColumn<Locataire, String> mailClient;
    @FXML
    private TableColumn<Locataire, String> phoneClient;
    @FXML
    private TableColumn<Locataire, Date> birthDateClient;
    @FXML
    private TableColumn<Facture, Integer> codeFacture;
    @FXML
    private TableColumn<Facture, String> firstnameFacture;
    @FXML
    private TableColumn<Facture, String> lastnameFacture;
    @FXML
    private TableColumn<Facture, Date> paidtheFacture;
    @FXML
    private TableColumn<Facture, Date> fromFacture;
    @FXML
    private TableColumn<Facture, Date> toFacture;
    @FXML
    private TableColumn<Facture, Integer> amountFacture;
    @FXML
    private TableColumn<Facture, String> typeFacture;
    @FXML
    private JFXButton refreshClient;
    @FXML
    private JFXButton refreshBill;
    @FXML
    private JFXButton clearTable;
    @FXML
    private JFXButton printAllClient;
    @FXML
    private JFXButton printAllBill;
    @FXML
    private JFXCheckBox sendClientMail;
    @FXML
    private JFXDatePicker beginHistory;
    @FXML
    private JFXDatePicker endHistory;
    @FXML
    private JFXCheckBox sendBillMail;
    private String nomUser;
    /**
     * Initializes the controller class.
     */
    public AccueilController(String nom) {
        nomUser = nom;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modify.setDisable(true);
        delete.setDisable(true);
        showInfo.setDisable(true);
        printButton.setDisable(true);
        gender.getItems().addAll("Male", "Female");
        type.getItems().addAll("Rent", "Water", "Electricity", other);
        type.valueProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals(other)) {
                    typeOther.setDisable(false);
                } else {
                    typeOther.setDisable(true);
                }
            }
        });
        chargerTableClient();
        remplirTableClient();
        chargerTableFacture();
        remplirTableFacture();
        billName.getItems().addAll(listeClients());
        tableFactures.getSelectionModel().selectedItemProperty().addListener((obs, ancVal, nouvVal) -> {
            if (nouvVal != null) {
                printButton.setDisable(false);
                printButton.setOnAction((ActionEvent e) -> {
                    print(nouvVal);
                });
            }
        });
        tableLocataire.getItems().addListener(new ListChangeListener<Locataire>() {
            public void onChanged(Change<? extends Locataire> c) {
                billName.getItems().clear();
                for (Locataire loc : tableLocataire.getItems()) {
                    billName.getItems().add(loc.toString());
                }
            }
        });
        tableLocataire.getSelectionModel().selectedItemProperty().addListener((obs, ancVal, nouvVal) -> {
            if (nouvVal != null) {
                modify.setDisable(false);
                delete.setDisable(false);
                showInfo.setDisable(false);
                modify.setOnAction((ActionEvent e) -> {
                    chargerPage("/vues/pagemodif.fxml", tableLocataire.getItems(), nouvVal.getCode(),
                            nouvVal.getNom(), nouvVal.getPrenom(), nouvVal.getPhone(), nouvVal.getMail(), nouvVal.getJob(), nouvVal.getBirthdate(), nouvVal.getSexe());
                });
                delete.setOnAction((ActionEvent e) -> {
                    Alert pope = new Alert(AlertType.CONFIRMATION);
                    pope.setTitle("Confirmation");
                    pope.setHeaderText(null);
                    pope.setContentText("Do you really want to delete the item?");
                    Optional<ButtonType> reponse = pope.showAndWait();
                    if (reponse.get() == ButtonType.OK) {
                        AppliDeLocation.baseDonnee.deleteClient(nouvVal.getCode());
                        tableLocataire.getItems().remove(fouille(nouvVal.getCode()));
                    }
                });
                showInfo.setOnAction((ActionEvent e) -> {
                    chargerPage("/vues/pageInfos.fxml", nouvVal.getCode(), nouvVal.toString());
                });
            }
        });

    }

    private int fouille(int code) {
        for (int i = 0; i < tableLocataire.getItems().size(); i++) {
            if (tableLocataire.getItems().get(i).getCode() == code) {
                return i;
            }
        }
        return -1;
    }

    private boolean valider(String... liste) {
        for (String val : liste) {
            if (val.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void remplirTableClient() {
        tableLocataire.getItems().addAll(AppliDeLocation.baseDonnee.afficherClient());
    }

    private void remplirTableFacture() {
        tableFactures.getItems().addAll(AppliDeLocation.baseDonnee.afficherFacture());
    }

    private void chargerTableFacture() {
        codeFacture.setCellValueFactory(new PropertyValueFactory<>("code"));
        firstnameFacture.setCellValueFactory(new PropertyValueFactory<>("nomLocataire"));
        lastnameFacture.setCellValueFactory(new PropertyValueFactory<>("prenomLocataire"));
        paidtheFacture.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
        fromFacture.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        toFacture.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        amountFacture.setCellValueFactory(new PropertyValueFactory<>("amount"));
        typeFacture.setCellValueFactory(new PropertyValueFactory<>("motif"));
    }

    private void chargerTableClient() {
        codeClient.setCellValueFactory(new PropertyValueFactory<>("code"));
        firstnameClient.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lastnameClient.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        genderClient.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        jobClient.setCellValueFactory(new PropertyValueFactory<>("job"));
        mailClient.setCellValueFactory(new PropertyValueFactory<>("mail"));
        phoneClient.setCellValueFactory(new PropertyValueFactory<>("phone"));
        birthDateClient.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void print(Facture facture) {
        String nomFac = "Bill" + facture.getCode() + ".pdf";
        if(BillGenerator.ecrireFacture(facture, nomFac, nomUser)){
            imprimerPDF.imprimerPDF(nomFac);
        }
    }

    @FXML
    private void changeSettings(ActionEvent event) {
        chargerPage("/vues/validationPage.fxml");
    }

    @FXML
    private void disconnect(ActionEvent event) {
        chargerVue("/vues/Graphique.fxml");
    }

    private void chargerPage(String vue, Integer code, String chaine) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource(vue));
            loader.setControllerFactory(c -> {
                return new PageInfosController(stage, code, chaine);
            });
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
            Alertes.alerte("Error", "Please retry");
        }
    }

    private void chargerPage(String vue, ObservableList<Locataire> liste, int code, String nom, String prenom,
            String telephone, String email, String jobClient, Date birth, String gender) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource(vue));
            loader.setControllerFactory(c -> {
                return new PagemodifController(stage, liste, code, nom, prenom, telephone, email, jobClient, birth, gender);
            });
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
            Alertes.alerte("Error", "Please retry");
        }
    }

    private void chargerPage(String vue) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource(vue));
            loader.setControllerFactory(c -> {
                return new ValidationPageController(stage);
            });
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alertes.alerte("Error", "Please retry");
        }
    }

    private void chargerVue(String vue) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource(vue));
            loader.setControllerFactory(c -> {
                return new GraphiqueController();
            });
            Scene scene = new Scene(loader.load());
            AppliDeLocation.stage.setScene(scene);
            AppliDeLocation.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alertes.alerte("Error", "Please retry");
        }
    }

    @FXML
    private void ajouterTenant(ActionEvent event) {
        LocalDate date = birthdate.getValue();
        if (valider(firstname.getText(), lastname.getText(), phone.getText(), mail.getText(),
                job.getText(), date.toString(), gender.getSelectionModel().getSelectedItem())) {
            if (AppliDeLocation.baseDonnee.insertClient(firstname.getText(), lastname.getText(),
                    gender.getSelectionModel().getSelectedItem(), job.getText(), mail.getText(), phone.getText(), Date.valueOf(birthdate.getValue()))) {
                int val = AppliDeLocation.baseDonnee.selectionnerClient(firstname.getText(), lastname.getText());
                if (val != -1) {
                    tableLocataire.getItems().add(new Locataire(val,
                            firstname.getText(), lastname.getText(), gender.getSelectionModel().getSelectedItem(),
                            job.getText(), mail.getText(), phone.getText(), Date.valueOf(birthdate.getValue())));
                }
            } else {
                Alertes.alerte("Impossible to add", "Please fill correctly the fields");
            }
        } else {
            Alertes.alerte("Impossible to add", "Please fill all the fields");
        }
    }

    private ArrayList<String> listeClients() {
        ArrayList<String> liste = new ArrayList<>();
        for (Locataire locataire : tableLocataire.getItems()) {
            liste.add(locataire.toString());
        }
        return liste;
    }

    @FXML
    private void ajouterBill(ActionEvent event) {
        String typeClient = new String();
        if (type.getSelectionModel().getSelectedItem().equals(other)) {
            typeClient = typeOther.getText();
        } else {
            typeClient = type.getSelectionModel().getSelectedItem();
        }
        LocalDate date = birthdate.getValue();
        Date dateActu = Date.valueOf(LocalDate.now());
        if (valider(typeClient, billName.getSelectionModel().getSelectedItem(), amount.getText(),
                dateBegin.getValue().toString(), dateEnd.getValue().toString())) {
            if (AppliDeLocation.baseDonnee.insertFacture(prendreCode(billName.getSelectionModel().getSelectedItem()), Integer.parseInt(amount.getText()),
                    dateActu, Date.valueOf(dateBegin.getValue()), Date.valueOf(dateEnd.getValue()), typeClient)) {
                int codeloc = prendreCode(billName.getSelectionModel().getSelectedItem());
                int val = AppliDeLocation.baseDonnee.selectionnerFacture(codeloc,
                        Integer.parseInt(amount.getText()), dateActu, Date.valueOf(dateBegin.getValue()), Date.valueOf(dateEnd.getValue()), typeClient);
                if (val != -1) {
                    String nomLocataire = AppliDeLocation.baseDonnee.retourner(codeloc, "nom");
                    String prenomLocataire = AppliDeLocation.baseDonnee.retourner(codeloc, "prenom");
                    if (nomLocataire != null && prenomLocataire != null) {
                        int code = AppliDeLocation.baseDonnee.selectionnerFacture(codeloc, Integer.parseInt(amount.getText()), dateActu,
                                Date.valueOf(dateBegin.getValue()), Date.valueOf(dateEnd.getValue()), typeClient);
                        if (code != -1) {
                            Facture facture = new Facture(code, nomLocataire, prenomLocataire,
                                    Integer.parseInt(amount.getText()), dateActu,
                                    Date.valueOf(dateBegin.getValue()), Date.valueOf(dateEnd.getValue()), typeClient);
                            tableFactures.getItems().add(facture);
                            print(facture);
                        }
                    } else {
                        Alertes.alerte("Error", "An error occured");
                    }
                } else {
                    Alertes.alerte("Error", "An error occured");
                }
            } else {
                Alertes.alerte("Impossible to add", "Please fill correctly the fields");
            }
        } else {
            Alertes.alerte("Impossible to add", "Please fill all the fields");
        }
    }

    private int prendreCode(String code) {
        String[] tab = code.split(" ");
        return Integer.parseInt(tab[0]);
    }

    @FXML
    private void refreshClient(ActionEvent event) {
        tableLocataire.getItems().clear();
        remplirTableClient();
    }

    @FXML
    private void refreshBill(ActionEvent event) {
        tableFactures.getItems().clear();
        remplirTableFacture();
    }

    @FXML
    private void clearTable(ActionEvent event) {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Warning");
        confirm.setHeaderText("Do you really want to delete the history?");
        confirm.setContentText("By doing this you will lose all the history of the bills");
        Optional<ButtonType> answer = confirm.showAndWait();
        if (answer.get() == ButtonType.OK) {
            if (AppliDeLocation.baseDonnee.clear()) {
                tableFactures.getItems().clear();
            }
        }
    }

    @FXML
    private void printAllClient(ActionEvent event) {
        Random r = new Random();
        String nomPDF = "report" + LocalDate.now().toString() + " " + r.nextInt(10) + ".pdf";
        PDFGenerator.ecrireAllClient(tableLocataire.getItems(), nomPDF);
        if (sendClientMail.isSelected()) {
            String mail = Alertes.textInput("Mail request", "Enter the email", "Email :", "Email");
            if (mail != null) {
                Mailer mailer = new Mailer(mail, nomPDF);
                Thread t = new Thread(mailer);
                t.start();
            }
        }
    }

    @FXML
    private void printAllBill(ActionEvent event) {
        Random r = new Random();
        String nomPDF = "report" + LocalDate.now().toString() + " " + r.nextInt(10) + ".pdf";
        LocalDate dateDebut = beginHistory.getValue();
        LocalDate dateFin = endHistory.getValue();
        if (dateDebut == null && dateFin == null) {
            dateDebut = LocalDate.of(2010, Month.JANUARY, 1);
            dateFin = LocalDate.now();
            facturer(dateDebut, dateFin, nomPDF);
        } else if (dateDebut != null && dateFin == null) {
            dateFin = LocalDate.now();
            nomPDF = "report" + dateDebut.toString() + "#" + dateFin.toString() + " " + r.nextInt(10) + ".pdf";
            facturer(dateDebut, dateFin, nomPDF);
        } else if (dateDebut == null && dateFin != null) {
            dateDebut = LocalDate.of(2010, Month.JANUARY, 1);
            nomPDF = "report" + dateDebut.toString() + "#" + dateFin.toString() + " " + r.nextInt(10) + ".pdf";
            facturer(dateDebut, dateFin, nomPDF);
        } else {
            if (dateDebut.isAfter(dateFin)) {
                Alertes.alerte("Failed", "Enter correctly the dates");
            } else {
                nomPDF = "report" + dateDebut.toString() + "#" + dateFin.toString() + " " + r.nextInt(10) + ".pdf";
                facturer(dateDebut, dateFin, nomPDF);
            }
        }
    }

    private void facturer(LocalDate dateDebut, LocalDate dateFin, String nomPDF) {
        ArrayList<Facture> liste = AppliDeLocation.baseDonnee.selecFactBetween(Date.valueOf(dateDebut), Date.valueOf(dateFin));
        PDFGenerator.ecrireFacture(liste, nomPDF);
        if (sendBillMail.isSelected()) {
            String mail = Alertes.textInput("Mail request", "Enter the email", "Email :", "Email");
            if (mail != null) {
                Mailer mailer = new Mailer(mail, nomPDF);
                Thread t = new Thread(mailer);
                t.start();
            }
        }
    }
}
