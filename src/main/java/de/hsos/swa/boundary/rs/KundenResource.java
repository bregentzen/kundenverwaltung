package de.hsos.swa.boundary.rs;

import de.hsos.swa.boundary.dto.AdresseWebDTO;
import de.hsos.swa.boundary.dto.KundeWebDTO;
import de.hsos.swa.boundary.dto.KundeWebDTOId;
import de.hsos.swa.control.KundenService;
import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Collection;
import java.util.stream.Collectors;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Path("/kunden")
@Tag(name = "Kundenverwaltung", description = "Verwaltung von Kunden und Adressen")
public class KundenResource {

    @Inject
    KundenService kundenService;

    @POST
    @Operation(summary = "Erstellt einen neuen Kunden", description = "Erstellt einen neuen Kunden mit den angegebenen Daten")
    @APIResponse(responseCode = "201", description = "Kunde erstellt")
    public Response createKunde(KundeWebDTO kundeDTO) {
        Kunde kunde = kundenService.kundeAnlegen(kundeDTO.getName());
        KundeWebDTOId responseDTO = new KundeWebDTOId(kunde.getKundennr(), kunde.getVorname() + " " + kunde.getNachname());

        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @Operation(summary = "Gibt alle Kunden zurück", description = "Gibt eine Liste aller Kunden zurück")
    @APIResponse(responseCode = "200", description = "Liste der Kunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = KundeWebDTOId.class)))
    public Collection<KundeWebDTOId> getAllKunden() {
        return kundenService.kundenAbfragen().stream()
                .map(k -> new KundeWebDTOId(k.getKundennr(), k.getVorname() + " " + k.getNachname()))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Gibt einen Kunden nach ID zurück", description = "Gibt die Daten eines Kunden anhand der ID zurück")
    @APIResponse(responseCode = "200", description = "Kunde gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = KundeWebDTOId.class)))
    @APIResponse(responseCode = "404", description = "Kunde nicht gefunden")
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
    @Path("/{id}")
    @Operation(summary = "Löscht einen Kunden nach ID", description = "Löscht die Daten eines Kunden anhand der ID")
    @APIResponse(responseCode = "204", description = "Kunde gelöscht")
    @APIResponse(responseCode = "404", description = "Kunde nicht gefunden")
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
    @Operation(summary = "Erstellt eine neue Adresse für einen Kunden", description = "Erstellt eine neue Adresse für einen Kunden anhand der ID")
    @APIResponse(responseCode = "201", description = "Adresse erstellt")
    public Response createAdresse(@PathParam("id") long id, AdresseWebDTO adresse) {
        Adresse adresseEntity = new Adresse(adresse.getStrasse(), adresse.getHausnummer(), adresse.getPlz(), adresse.getOrt());
        kundenService.adresseAnlegen(id, adresseEntity);
        return Response.status(Response.Status.CREATED).entity(adresse).build();
    }

    @PUT
    @Path("/{id}/adresse")
    @Operation(summary = "Aktualisiert die Adresse eines Kunden", description = "Aktualisiert die Adresse eines Kunden anhand der ID")
    @APIResponse(responseCode = "200", description = "Adresse aktualisiert")
    public Response updateAdresse(@PathParam("id") long id, AdresseWebDTO adresse) {
        Adresse adresseEntity = new Adresse(adresse.getStrasse(), adresse.getHausnummer(), adresse.getPlz(), adresse.getOrt());
        kundenService.adresseAendern(id, adresseEntity);
        return Response.ok(adresse).build();
    }

    @DELETE
    @Path("/{id}/adresse")
    @Operation(summary = "Löscht die Adresse eines Kunden", description = "Löscht die Adresse eines Kunden anhand der ID")
    @APIResponse(responseCode = "204", description = "Adresse gelöscht")
    @APIResponse(responseCode = "404", description = "Adresse nicht gefunden")
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
    @Operation(summary = "Gibt die Adresse eines Kunden zurück", description = "Gibt die Adresse eines Kunden anhand der ID zurück")
    @APIResponse(responseCode = "200", description = "Adresse gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdresseWebDTO.class)))
    @APIResponse(responseCode = "404", description = "Adresse nicht gefunden")
    public Response getAdresseByKundenId(@PathParam("id") long id) {
        Adresse adresse = kundenService.adresseAbfragen(id);
        if (adresse != null) {
            AdresseWebDTO adresseDTO = new AdresseWebDTO(adresse.getStrasse(), adresse.getHausnummer(), adresse.getPlz(), adresse.getOrt());
            return Response.ok(adresseDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}