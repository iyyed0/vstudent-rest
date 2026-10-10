package tn.esprit.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Fournit un ObjectMapper Jackson "standard" (sans l'introspecteur JAXB).
 *
 * Sans ceci, Jersey enregistre automatiquement le JacksonJaxbJsonProvider qui
 * tient compte de l'annotation @XmlTransient : le champ "option" serait alors
 * absent du JSON. L'atelier attend "option" dans le JSON des étudiants mais
 * pas dans le XML (géré, lui, par le provider JAXB). Ce ContextResolver
 * garantit donc : option présente en JSON, absente en XML.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
