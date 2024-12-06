package de.hsos.swa.boundary.dto;

import java.util.Objects;

public class AdresseWebDTO {
    String strasse;
    String hausnummer;
    String plz;
    String ort;

    public AdresseWebDTO() {
    }

    public AdresseWebDTO(String strasse, String hausnummer, String plz, String ort) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdresseWebDTO that = (AdresseWebDTO) o;
        return Objects.equals(strasse, that.strasse) &&
                Objects.equals(hausnummer, that.hausnummer) &&
                Objects.equals(plz, that.plz) &&
                Objects.equals(ort, that.ort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strasse, hausnummer, plz, ort);
    }
}