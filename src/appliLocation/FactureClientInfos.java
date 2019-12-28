/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appliLocation;

import java.sql.Date;

/**
 *
 * @author ESDRAS 
 */
public class FactureClientInfos {
    private Integer amount;
    private Date datePaiement;
    private Date dateDebut;
    private Date dateFin;
    private String motif;

    public FactureClientInfos(int amount, Date datePaiement, Date dateDebut, Date dateFin, String motif) {
        this.amount = amount;
        this.datePaiement = datePaiement;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.motif = motif;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    
}
