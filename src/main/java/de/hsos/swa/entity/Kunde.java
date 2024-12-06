package de.hsos.swa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.enterprise.inject.Vetoed;

@Entity
@Vetoed
public class Kunde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kundennr;
    private String vorname;
    private String nachname;

    @OneToOne
    private Adresse adresse;

    public Kunde() {
    }

    public Kunde(Long kundennr, String vorname, String nachname, Adresse adresse) {
        this.kundennr = kundennr;
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
    }

    public Kunde(String vorname, String nachname, Adresse adresse) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
    }

    public Kunde(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Long getKundennr() {
        return kundennr;
    }

    public void setKundennr(Long kundennr) {
        this.kundennr = kundennr;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", adresse=" + adresse +
                '}';
    }
}