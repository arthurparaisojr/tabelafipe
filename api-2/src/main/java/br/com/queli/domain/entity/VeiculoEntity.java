package br.com.queli.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
public class VeiculoEntity extends PanacheEntity {

    public String codigoMarca;
    public String nomeMarca;

    public String codigoModelo;
    public String nomeModelo;

    @Column(columnDefinition = "TEXT")
    public String observacoes;
}