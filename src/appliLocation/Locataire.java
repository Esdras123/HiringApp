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
public class Locataire {
    private Integer code;
    private String nom, prenom, sexe, job, mail, phone;
    private Date birthdate;
    
    public Locataire(int code, String nom, String prenom, String sexe, String job, String mail, String phone, Date birthdate) {
        this.code = code;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.job = job;
        this.mail = mail;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public String toString(){
        return code + " " + nom + " " + prenom;
    }
}
