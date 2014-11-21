
package org.desaparecidosbr.app.entity;

import java.io.Serializable;

public class Pessoa implements Serializable
{
   private static final long serialVersionUID = 1L;

    private int id;
    private String nome;

    private String foto;
    private String data_des;
    private String cidade;
    private String estado;
    private String feito_boletim;
    private String sexo;
    private String idade_des;
    private String transtorno_mental;
    private String tipo_fisico;
    private String pele;
    private String peso;
    private String altura;
    private String olhos;
    private String cabelos;
    private String data_registro;
    private String data_atualizacao;
    private String situacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getData_des() {
        return data_des;
    }

    public void setData_des(String data_des) {
        this.data_des = data_des;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFeito_boletim() {
        return feito_boletim;
    }

    public void setFeito_boletim(String feito_boletim) {
        this.feito_boletim = feito_boletim;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdade_des() {
        return idade_des;
    }

    public void setIdade_des(String idade_des) {
        this.idade_des = idade_des;
    }

    public String getTranstorno_mental() {
        return transtorno_mental;
    }

    public void setTranstorno_mental(String transtorno_mental) {
        this.transtorno_mental = transtorno_mental;
    }

    public String getTipo_fisico() {
        return tipo_fisico;
    }

    public void setTipo_fisico(String tipo_fisico) {
        this.tipo_fisico = tipo_fisico;
    }

    public String getPele() {
        return pele;
    }

    public void setPele(String pele) {
        this.pele = pele;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getOlhos() {
        return olhos;
    }

    public void setOlhos(String olhos) {
        this.olhos = olhos;
    }

    public String getCabelos() {
        return cabelos;
    }

    public void setCabelos(String cabelos) {
        this.cabelos = cabelos;
    }

    public String getData_registro() {
        return data_registro;
    }

    public void setData_registro(String data_registro) {
        this.data_registro = data_registro;
    }

    public String getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(String data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
