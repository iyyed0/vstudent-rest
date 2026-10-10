package tn.esprit.rest;

import entities.Etudiant;
import entities.EtudiantList;
import metiers.EtudiantBusiness;

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

@Path("etudiants")
public class EtudiantRessource {

    private final EtudiantBusiness etudiantBusiness = new EtudiantBusiness();

    // 1) Création d'un nouvel étudiant -> 201 Created | 404 Not Found
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.CREATED).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) Liste de tous les étudiants -> 200 OK
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiants() {
        List<Etudiant> etudiants = etudiantBusiness.getAllEtudiants();
        return Response.ok(etudiants).build();
    }

    // 6) Étudiants associés à une option donnée -> 200 OK | 404 Not Found (XML)
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        List<Etudiant> etudiants = etudiantBusiness.getEtudiantsByOption(codeOption);
        if (etudiants.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new EtudiantList(etudiants)).build();
    }

    // 3) Récupération d'un étudiant par identifiant -> 200 OK | 404 Not Found
    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // 4) Suppression d'un étudiant -> 200 OK | 404 Not Found
    @DELETE
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Mise à jour d'un étudiant -> 200 OK | 404 Not Found
    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        if (etudiant == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (etudiantBusiness.updateEtudiant(identifiant, etudiant)) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
