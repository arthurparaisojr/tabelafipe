package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.Marca;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CargaService {

    @RestClient
    FipeClient fipeClient;

    @Inject
    @Channel("marcas-out") // [cite: 9]
    Emitter<String> marcaEmitter;

    @Inject
    ObjectMapper mapper;

    public void executarCargaInicial() {
        List<Marca> marcas = fipeClient.buscarMarcas();

        // Envia uma por uma para a fila para processamento assíncrono
        marcas.forEach(marca -> {
            try {
                String json = mapper.writeValueAsString(marca);
                marcaEmitter.send(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}