package br.com.pedrohenrique.projetofinal.model;

public class Suprimento {
    public String uid;
    public String descricao;
    public Integer quantidade;
    public String unidadeMedida;
    public String usuarioUid;

    public Suprimento() {
    }

    public Suprimento(String uid, String descricao, Integer quantidade, String unidadeMedida, String usuarioUid) {
        this.uid = uid;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
        this.usuarioUid = usuarioUid;
    }
}
