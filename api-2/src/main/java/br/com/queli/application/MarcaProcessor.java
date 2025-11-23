package br.com.queli.application;

import br.com.queli.domain.dto.MarcaDTO;
import br.com.queli.domain.dto.ModeloDTO;
import br.com.queli.domain.entity.VeiculoEntity;
import br.com.queli.infra.client.FipeClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class MarcaProcessor {

    @Inject
    ObjectMapper mapper;

    @RestClient
    FipeClient fipeClient;

    @Incoming("marcas-in") // Lê da fila
    @Transactional // Abre transação no banco
    public void processarMarca(String marcaJson) {
        try {
            System.out.println("Recebido: " + marcaJson);

            // 1. Converte JSON para Objeto
            MarcaDTO marca = mapper.readValue(marcaJson, MarcaDTO.class);

            // 2. Busca modelos na FIPE
            var resposta = fipeClient.buscarModelos(marca.codigo());

            if (resposta != null && resposta.modelos() != null) {
                // 3. Salva cada modelo no banco
                for (ModeloDTO modelo : resposta.modelos()) {
                    VeiculoEntity veiculo = new VeiculoEntity();
                    veiculo.codigoMarca = marca.codigo();
                    veiculo.nomeMarca = marca.nome();
                    veiculo.codigoModelo = String.valueOf(modelo.codigo());
                    veiculo.nomeModelo = modelo.nome();

                    veiculo.persist(); // Salva no Postgres
                }
                System.out.println("Salvos " + resposta.modelos().size() + " modelos para " + marca.nome());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}