package br.com.queli.infra.client;

import br.com.queli.domain.model.Marca;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "fipe-api")
public interface FipeClient {
    @GET
    @Path("/carros/marcas")
    List<Marca> buscarMarcas();
}