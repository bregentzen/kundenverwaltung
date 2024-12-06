package de.hsos.swa.control;

import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;

import java.util.Collection;

public interface KundenService {
    Kunde kundeAnlegen(String name);
    Collection<Kunde> kundenAbfragen();
    Kunde kundeAbfragen(long kundennr);
    boolean kundeLoeschen(long kundennr);
    Adresse adresseAnlegen(long kundennr, Adresse adresse);
    Adresse adresseAendern(long kundennr, Adresse adresse);
    Adresse adresseAbfragen(long kundennr);
    boolean adresseLoeschen(long kundennr);
}
