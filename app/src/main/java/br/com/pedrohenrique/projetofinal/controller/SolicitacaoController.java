package br.com.pedrohenrique.projetofinal.controller;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import br.com.pedrohenrique.projetofinal.model.Solicitacao;

public class SolicitacaoController {
    private FirebaseFirestore db;
    private Context context;

    public SolicitacaoController(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> cadastrarSolicitacao(Solicitacao solicitacao) {
        String uid = db.collection("solicitacoes").document().getId();
        solicitacao.uid = uid;
        return db.collection("solicitacoes").document(uid).set(solicitacao);
    }

    public Task<QuerySnapshot> consultarSolicitacoes() {
        CollectionReference suprimentosRef = db.collection("solicitacoes");
        return suprimentosRef.get();
    }
}
