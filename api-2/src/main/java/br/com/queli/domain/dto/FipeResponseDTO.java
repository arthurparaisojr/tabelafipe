package br.com.queli.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FipeResponseDTO(List<ModeloDTO> modelos) {}