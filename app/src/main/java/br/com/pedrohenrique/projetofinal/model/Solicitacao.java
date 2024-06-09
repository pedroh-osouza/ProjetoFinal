package br.com.pedrohenrique.projetofinal.model;

import java.time.LocalDateTime;

public class Solicitacao {
    public String uid;
    public String usuarioUid;
    public String suprimentoUid;
    public String dataSolicitacao;
    public String status;

    public Solicitacao(String uid, String usuarioUid, String suprimentoUid, String dataSolicitacao, String status) {
        this.uid = uid;
        this.usuarioUid = usuarioUid;
        this.suprimentoUid = suprimentoUid;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }
}
