package de.hsos.swa.entity;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.*;
import jakarta.enterprise.inject.Vetoed;

@Entity(name="Adresse")
@Vetoed
@Table(name = "Adressen", schema = "public")
@Dependent
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Adr_ID")
    private Long id;

    @Column(name = "Straße")
    private String strasse;

    @Column(name = "Hausnummer")
    private String hausnummer;

    @Column(name = "PLZ")
    private String plz;

    @Column(name = "Ort")
    private String ort;

    public Adresse() {
    }

    public Adresse(String strasse, String hausnummer, String plz, String ort) {
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;
        this.ort = ort;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "strasse='" + strasse + '\'' +
                ", hausnummer='" + hausnummer + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                '}';
    }
}