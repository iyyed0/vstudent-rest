package tn.esprit.rest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("options")
@Produces(MediaType.APPLICATION_JSON)
public class OptionRessource {

    private final OptionBusiness optionBusiness = new OptionBusiness();

    // 1) Création d'une nouvelle option -> 201 Created
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        if (option == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        optionBusiness.addOption(option);
        return Response.status(Response.Status.CREATED).entity(option).build();
    }

    // 2) Liste de toutes les options -> 200 OK
    // 3) Liste des options filtrées par domaine -> 200 OK
    @GET
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> liste;
        if (domaine == null || domaine.isEmpty()) {
            liste = optionBusiness.getListeOptions();
        } else {
            liste = optionBusiness.getOptionsByDomaine(domaine);
        }
        return Response.ok(liste).build();
    }

    // 6) Récupération d'une option ayant un code donné -> 200 OK | 404 Not Found
    @GET
    @Path("{code}")
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // 4) Suppression d'une option -> 200 OK | 404 Not Found
    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optionBusiness.deleteOption(code)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Mise à jour d'une option -> 200 OK | 404 Not Found
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (option == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (optionBusiness.updateOption(code, option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
