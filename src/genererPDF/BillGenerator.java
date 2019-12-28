/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genererPDF;

import appliLocation.Facture;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ESDRAS
 */
public class BillGenerator {

    private static String template
            = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
            + ""
            + "<body style=\"font-family: Consolas;\">"
            + ""
            + "<div style=\"text-align:center;font-size:10px;\">"
            + "<p><b>Nso Boyz Express</b></p></div>"
            + "<br />"
            + "<table style=\"text-align: left;font-size:11px;\">"
            + "<tr>"
            + "<td><b><u>Date:</u></b>&nbsp;&nbsp; $date</td>"
            + "</tr>"
            + "<tr>"
            + "<td colspan=\"2\"><b><u>Cashier:</u></b>&nbsp;&nbsp; $caissiereName</td>"
            + "</tr>"
            + "<tr>"
            + "<td><b><u>Bill No:</u></b>&nbsp;&nbsp; $no</td>"
            + "</tr>"
            + "<tr>"
            + "<td><b><u>Tenant firstname:</u></b>&nbsp;&nbsp; $firstname</td>"
            + "<td></td>"
            + "</tr>"
            + "<tr>"
            + "<td><b><u>Tenant lastname:</u></b>&nbsp;&nbsp; $lastname</td>"
            + "</tr>"
            + "<tr>"
            + "<td><b><u>Reason:</u></b>&nbsp;&nbsp; $motif</td>"
            + "</tr>"
            + "<tr>"
            + "<td><b><u>Start Date:</u></b>&nbsp;&nbsp; $debut</td>"
            + "</tr>"
            + "<tr>"
            + "<td><b><u>End Date:</u></b>&nbsp;&nbsp; $fin</td>"
            + "</tr>"
            + "<tr>"
            + "<td><b><u>Amount:</u></b>&nbsp;&nbsp; $amount XAF</td>"
            + "</tr>"
            + "</table>"
            + "<p>-------------------------------------------------------------------------</p>"
            + "<p style=\"text-align:center;font-size:10px;\">Products and Services are not refundable</p>"
            + "<p>-------------------------------------------------------------------------</p>"
            + "</body>"
            + "</html>";

    public static boolean ecrireFacture(Facture facture, String billName, String caissiere) {
        template = template.replace("$date", new SimpleDateFormat("dd-MM-yyyy").format(facture.getDatePaiement()));
        template = template.replace("$caissiereName", caissiere);
        template = template.replace("$no", "" + facture.getCode());
        template = template.replace("$firstname", facture.getNomLocataire());
        template = template.replace("$lastname", facture.getPrenomLocataire());
        template = template.replace("$motif", facture.getMotif());
        template = template.replace("$debut", new SimpleDateFormat("dd-MM-yyyy").format(facture.getDateDebut()));
        template = template.replace("$fin", new SimpleDateFormat("dd-MM-yyyy").format(facture.getDateFin()));
        template = template.replace("$amount", "" + facture.getAmount());

        Document document = new Document(new Rectangle(315, 850));
        document.setMargins(10, 10, 0, 0);
        File file = new File(Constante.CHEMIN + billName);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            List<Element> parts = HTMLWorker.parseToList(new StringReader(template), null);
            for (int i = 0; i < parts.size(); i++) {
                Element e = (Element) parts.get(i);
                document.add(e);
            }
            document.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BillGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(BillGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BillGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
