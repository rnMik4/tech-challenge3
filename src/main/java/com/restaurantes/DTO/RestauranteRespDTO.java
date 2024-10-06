package com.restaurantes.DTO;

import com.restaurantes.entity.MesasRestaurante;
import com.restaurantes.entity.Usuario;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RestauranteRespDTO {
    private Long id;
    private LocalDateTime dataRegistro;
    private String nomeRestaurante;
    private String tipoCozinha;
    private String logradouro;
    private String bairro;
    private String numero;
    private String cidade;
    private String uf;
    private String pais;
    private String cep;
    private String telefone;
    private String cnpj;
    private Integer horarioAbertura;
    private Integer horarioFechamento;
    private Usuario dono;
    private List<MesasRestaurante> mesas;

    public List<MesasRestaurante> getMesas() {
        return mesas;
    }

    public void setMesas(List<MesasRestaurante> mesas) {
        this.mesas = mesas;
    }

    public Integer getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(Integer horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public Integer getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(Integer horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public Usuario getDono() {
        return dono;
    }

    public void setDono(Usuario dono) {
        this.dono = dono;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
