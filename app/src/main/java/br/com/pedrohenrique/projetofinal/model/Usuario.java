package br.com.pedrohenrique.projetofinal.model;

public class Usuario {

    public String uid;
    public String nome;
    public String email;
    public String telefone;
    public String endereco;

    public Usuario(String uid, String nome, String email, String telefone, String endereco) {
        this.uid = uid;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }
}
