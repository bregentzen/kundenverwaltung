package de.hsos.swa.control;

import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@ApplicationScoped
public class KundenServiceClass {

    static long idCounter = 0;

    private Collection<Kunde> kunden;

    public KundenServiceClass() {
        this.kunden = new ArrayList<>();
    }

    public Kunde kundeAnlegen(String name) {
        String[] nameParts = name.split(" ");
        String vorname = nameParts[0];
        String nachname = "";
        if (nameParts.length > 1) {
            nachname = nameParts[1];
        }
        Kunde kunde = new Kunde(vorname, nachname);
        kunde.setKundennr(++idCounter);
        kunden.add(kunde);
        System.out.println("Kunde angelegt: " + kunde.getKundennr() + " " + kunde.getVorname() + " " + kunde.getNachname());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Kunde k : kunden) {
            if (Objects.equals(kunde.getKundennr(), k.getKundennr())) {
                return k;
            }
        }
        return null;
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

    public Adresse adresseAnlegen(long kundennr, Adresse adresse) {
        for (Kunde kunde : kunden) {
            if (kunde.getKundennr() == kundennr) {
                kunde.setAdresse(adresse);
                return kunde.getAdresse();
            }
        }
        return null;
    }

    public Adresse adresseAendern(long kundennr, Adresse neueAdresse) {
        for (Kunde kunde : kunden) {
            if (kunde.getKundennr() == kundennr) {
                kunde.setAdresse(neueAdresse);
                return kunde.getAdresse();
            }
        }
        return null;
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
