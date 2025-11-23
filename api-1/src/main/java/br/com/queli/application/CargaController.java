package application;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/carga")
public class CargaController {

    @Inject
    CargaService cargaService;

    @POST
    @Produces(MediaType.APPLICATION_JSON) // [cite: 6]
    public Response iniciarCarga() {
        cargaService.executarCargaInicial();
        return Response.accepted().entity("Carga iniciada. Processamento em background.").build();
    }
}