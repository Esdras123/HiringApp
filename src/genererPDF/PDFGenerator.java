/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genererPDF;

import appliLocation.Alertes;
import appliLocation.Facture;
import appliLocation.Locataire;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author ESDRAS
 */
public class PDFGenerator {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public static void ecrireFacture(ArrayList<Facture> factures, String nom) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(Constante.CHEMIN + nom));
            document.open();
            addTitle(document, "Bill History");
            Paragraph par = new Paragraph();
            par.add(createTableFacture(factures));
            addEmptyLine(par, 3);
            int total = 0;
            for (Facture fac: factures) {
                total += fac.getAmount();
            }
            par.add(new Paragraph(
                "Total: " + total + " XAF ", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
            document.add(par);
            document.close();
            open(Constante.CHEMIN+nom);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
            Alertes.alerte("Error", "There was a problem while creating the pdf");
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
            Alertes.alerte("Error", "There was a problem while creating the pdf");
        }
    }
    public static void open(String chemin) {
        Desktop d = Desktop.getDesktop();
        try {
            d.open(new File(chemin));
        } catch (IOException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static PdfPTable createTableClients(ObservableList<Locataire> locataires){
        PdfPTable table = new PdfPTable(8);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        table.setWidthPercentage(100);  
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        
        PdfPCell c1 = new PdfPCell(new Phrase("Code"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Firstname"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Lastname"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Gender"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Job"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Mail"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Phone"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Birth Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        table.setHeaderRows(1);
        
        for (Locataire loc : locataires){
            table.addCell(""+loc.getCode());
            table.addCell(loc.getNom());
            table.addCell(loc.getPrenom());
            table.addCell(""+loc.getSexe());
            table.addCell(loc.getJob());
            table.addCell(loc.getMail());
            table.addCell(loc.getPhone());
            table.addCell(loc.getBirthdate().toString());
        }
        return table;
    }
    public static void ecrireAllClient(ObservableList<Locataire> locataires, String nom) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(Constante.CHEMIN + nom));
            document.open();
            addTitle(document, "Client List Document");
            Paragraph par = new Paragraph();
            par.add(createTableClients(locataires));
            document.add(par);
            document.close();
            open(Constante.CHEMIN+nom);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
            Alertes.alerte("Error", "There was a problem while creating the pdf");
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
            Alertes.alerte("Error", "There was a problem while creating the pdf");
        }

    }
    public static PdfPTable createTableFacture(ArrayList<Facture> factures){
        PdfPTable table = new PdfPTable(8);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        table.setWidthPercentage(100);  
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);        
       // table.setTotalWidth(document.getPageSize().getWidth() - 80);
        //table.setLockedWidth(true);
        PdfPCell c1 = new PdfPCell(new Phrase("Code"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Firstname"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Lastname"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Amount"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Paid the"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Goes From"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Ends the"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        table.setHeaderRows(1);
        
        for (Facture fac : factures){
            table.addCell(""+fac.getCode());
            table.addCell(fac.getNomLocataire());
            table.addCell(fac.getPrenomLocataire());
            table.addCell(""+fac.getAmount());
            table.addCell(fac.getDatePaiement().toString());
            table.addCell(fac.getDateDebut().toString());
            table.addCell(fac.getDateFin().toString());
            table.addCell(fac.getMotif());
        }
        return table;
    }
    public static void addTitle(Document document, String nom) {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph(nom, catFont));
        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by: Nso Boys Hire" + ", " + LocalDate.now().toString(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        try {
            document.add(preface);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
