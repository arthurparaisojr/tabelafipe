package br.com.queli.application;

import br.com.queli.domain.model.Marca;
import br.com.queli.infra.client.FipeClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;

@ApplicationScoped
public class CargaService {

    @RestClient
    FipeClient fipeClient;

    @Inject
    @Channel("marcas-out")
    Emitter<String> marcaEmitter;

    @Inject
    ObjectMapper mapper;

    public void executarCargaInicial() {
        List<Marca> marcas = fipeClient.buscarMarcas();
        if (marcas != null) {
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
}