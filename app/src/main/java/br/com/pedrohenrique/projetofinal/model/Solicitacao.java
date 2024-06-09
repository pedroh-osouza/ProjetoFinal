package br.com.pedrohenrique.projetofinal.model;
public class Solicitacao {
    public String uid;
    public String nomeSolicitante;
    public String enderecoSolicitante;
    public String contatoSolicitante;
    public String suprimentoUid;
    public String dataSolicitacao;
    public String status;

    public Solicitacao(String uid, String nomeSolicitante, String enderecoSolicitante, String contatoSolicitante, String suprimentoUid, String dataSolicitacao, String status) {
        this.uid = uid;
        this.nomeSolicitante = nomeSolicitante;
        this.enderecoSolicitante = enderecoSolicitante;
        this.contatoSolicitante = contatoSolicitante;
        this.suprimentoUid = suprimentoUid;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }
}
