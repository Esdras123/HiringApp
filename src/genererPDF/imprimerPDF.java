/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genererPDF;

import appliLocation.Alertes;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ESDRAS
 */
public class imprimerPDF {
    public static void imprimerPDF(String nom) {
        if (Desktop.isDesktopSupported()) {
            if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.PRINT)){
                try {
                    java.awt.Desktop.getDesktop().print(new File(Constante.CHEMIN + nom));
                } catch (IOException ex) {
                    Logger.getLogger(imprimerPDF.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                Alertes.alerte("Problem", "The computer does not support the method");
            }
        }
        else{
            Alertes.alerte("Problem", "The computer does not support the method");
        }
    }
}
