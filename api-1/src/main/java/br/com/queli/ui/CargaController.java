package br.com.queli.ui;

import br.com.queli.application.CargaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/carga")
public class CargaController {

    @Inject
    CargaService cargaService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarCarga() {
        cargaService.executarCargaInicial();
        return Response.accepted().entity("Carga iniciada.").build();
    }
}