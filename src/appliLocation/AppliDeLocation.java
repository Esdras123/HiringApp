/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appliLocation;

import codesBd.BD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ESDRAS
 */
public class AppliDeLocation extends Application {
    public static BD baseDonnee = new BD();
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vues/Graphique.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Nso Boyz Express");
        AppliDeLocation.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
