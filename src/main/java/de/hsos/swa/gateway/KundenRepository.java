package de.hsos.swa.gateway;

import de.hsos.swa.control.KundenService;
import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class KundenRepository implements KundenService {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Kunde kundeAnlegen(String name) {
        String[] parts = name.split(" ");
        String vorname = parts[0];
        String nachname = parts.length > 1 ? parts[1] : "";
        Kunde kunde = new Kunde(vorname, nachname);
        em.persist(kunde);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return em.find(Kunde.class, kunde.getKundennr());
    }

    @Override
    public Collection<Kunde> kundenAbfragen() {
        return em.createQuery("SELECT k FROM Kunde k", Kunde.class).getResultList();
    }

    @Override
    public Kunde kundeAbfragen(long kundennr) {
        return em.find(Kunde.class, kundennr);
    }

    @Override
    @Transactional
    public boolean kundeLoeschen(long kundennr) {
        Kunde kunde = em.find(Kunde.class, kundennr);
        if (kunde != null) {
            em.remove(kunde);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Adresse adresseAnlegen(long kundennr, Adresse adresse) {
        Kunde kunde = em.find(Kunde.class, kundennr);
        if (kunde != null) {
            kunde.setAdresse(adresse);
            em.persist(adresse);
            em.merge(kunde);
            return adresse;
        }
        return null;
    }

    @Override
    @Transactional
    public Adresse adresseAendern(long kundennr, Adresse adresse) {
        Kunde kunde = em.find(Kunde.class, kundennr);
        if (kunde != null) {
            Adresse existingAdresse = kunde.getAdresse();
            if (existingAdresse != null) {
                existingAdresse.setStrasse(adresse.getStrasse());
                existingAdresse.setHausnummer(adresse.getHausnummer());
                existingAdresse.setPlz(adresse.getPlz());
                existingAdresse.setOrt(adresse.getOrt());
                em.merge(existingAdresse);
                return existingAdresse;
            }
        }
        return null;
    }

    @Override
    public Adresse adresseAbfragen(long kundennr) {
        Kunde kunde = em.find(Kunde.class, kundennr);
        return kunde != null ? kunde.getAdresse() : null;
    }

    @Override
    @Transactional
    public boolean adresseLoeschen(long kundennr) {
        Kunde kunde = em.find(Kunde.class, kundennr);
        if (kunde != null && kunde.getAdresse() != null) {
            em.remove(kunde.getAdresse());
            kunde.setAdresse(null);
            em.merge(kunde);
            return true;
        }
        return false;
    }
}