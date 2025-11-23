package br.com.queli.infra.client;

import br.com.queli.domain.dto.FipeResponseDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "fipe-api")
public interface FipeClient {

    @GET
    @Path("/carros/marcas/{id}/modelos")
    FipeResponseDTO buscarModelos(@PathParam("id") String idMarca);
}