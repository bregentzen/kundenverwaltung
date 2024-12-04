package de.hsos.swa.boundary.rs;

import de.hsos.swa.boundary.dto.AdresseWebDTO;
import de.hsos.swa.boundary.dto.KundeWebDTO;
import de.hsos.swa.boundary.dto.KundeWebDTOId;
import de.hsos.swa.control.KundenService;
import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KundenResource {

    @Inject
    KundenService kundenService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/kunden")
    public Response createKunde(KundeWebDTO kundeDTO) {
        kundenService.kundeAnlegen(kundeDTO.getName());
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/kunden")
    public Collection<KundeWebDTOId> getAllKunden() {
        return kundenService.kundenAbfragen().stream()
                .map(k -> new KundeWebDTOId(k.getKundennr(), k.getVorname() + " " + k.getNachname()))
                .toList();
    }

    @GET
    @Path("/kunden/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKundeById(@PathParam("id") long id) {
        Kunde kunde = kundenService.kundeAbfragen(id);
        if (kunde != null) {
            KundeWebDTOId kundeDTO = new KundeWebDTOId(kunde.getKundennr(), kunde.getVorname() + " " + kunde.getNachname());
            return Response.ok(kundeDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/kunden/{id}")
    public Response deleteKundeById(@PathParam("id") long id) {
        boolean deleted = kundenService.kundeLoeschen(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{id}/adresse")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAdresse(@PathParam("id") long id, AdresseWebDTO adresse) {
        Adresse adresseEntity = new Adresse(adresse.getStrasse(), adresse.getHausnummer(), adresse.getPlz(), adresse.getOrt());
        kundenService.adresseAnlegen(id, adresseEntity);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}/adresse")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAdresse(@PathParam("id") long id, AdresseWebDTO adresse) {
        Adresse adresseEntity = new Adresse(adresse.getStrasse(), adresse.getHausnummer(), adresse.getPlz(), adresse.getOrt());
        kundenService.adresseAendern(id, adresseEntity);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}/adresse")
    public Response deleteAdresse(@PathParam("id") long id) {
        boolean deleted = kundenService.adresseLoeschen(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/adresse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdresseByKundenId(@PathParam("id") long id) {
        Adresse adresse = kundenService.adresseAbfragen(id);
        if (adresse != null) {
            return Response.ok(adresse).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}