package dao;

import java.util.Date;

public class Produto{
    private int id;
    private String modelo;
    private String marca;
    private int quantidade;
    private String tipo;
    private Date data;

    // Metodo vazio onde está sendo utilizado na classe PrudutoDao no metodo para obterTodosProdutos()
    public Produto() {
    }

    public Produto(int id, String modelo, String marca, int quantidade, String tipo, Date data) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.data = data;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}