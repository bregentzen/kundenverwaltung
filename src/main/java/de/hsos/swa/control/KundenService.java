package de.hsos.swa.control;

import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collection;

@ApplicationScoped
public class KundenService {

    static long idCounter = 0;

    private Collection<Kunde> kunden;

    public KundenService() {
        this.kunden = new ArrayList<>();
    }

    public void kundeAnlegen(String name) {
        String[] nameParts = name.split(" ");
        String vorname = nameParts[0];
        String nachname = nameParts[1];
        Kunde kunde = new Kunde(vorname, nachname);
        kunde.setKundennr(++idCounter);
        kunden.add(kunde);
        System.out.println("Kunde angelegt: " + kunde.getKundennr() + " " + kunde.getVorname() + " " + kunde.getNachname());
    }

    public Collection<Kunde> kundenAbfragen() {
        return kunden;
    }

    public Kunde kundeAbfragen(long kundennr) {
        for (Kunde kunde : kunden) {
            if (kunde.getKundennr() == kundennr) {
                return kunde;
            }
        }
        return null;
    }

    public boolean kundeLoeschen(long kundennr) {
        for (Kunde kunde : kunden) {
            if (kunde.getKundennr() == kundennr) {
                kunden.remove(kunde);
                return true;
            }
        }
        return false;
    }

    public void adresseAnlegen(long kundennr, Adresse adresse) {
        for (Kunde kunde : kunden) {
            if (kunde.getKundennr() == kundennr) {
                kunde.setAdresse(adresse);
            }
        }
    }

    public void adresseAendern(long kundennr, Adresse neueAdresse) {
        for (Kunde kunde : kunden) {
            if (kunde.getKundennr() == kundennr) {
                kunde.setAdresse(neueAdresse);
            }
        }
    }

    public Adresse adresseAbfragen(long kundennr) {
        for (Kunde kunde : kunden) {
            if (kunde.getKundennr() == kundennr) {
                return kunde.getAdresse();
            }
        }
        return null;
    }

    public boolean adresseLoeschen(long kundennr) {
        for (Kunde kunde : kunden) {
            if (kunde.getKundennr() == kundennr) {
                kunde.setAdresse(null);
                return true;
            }
        }
        return false;
    }
}
