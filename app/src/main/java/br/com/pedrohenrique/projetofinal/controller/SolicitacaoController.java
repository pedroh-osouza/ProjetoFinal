package br.com.pedrohenrique.projetofinal.controller;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import br.com.pedrohenrique.projetofinal.model.Solicitacao;
import br.com.pedrohenrique.projetofinal.view.DetalhesSolicitacaoActivity;

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

    public Task<QuerySnapshot> consultarSolicitacoesPendentes() {
        CollectionReference suprimentosRef = db.collection("solicitacoes");
        return suprimentosRef.whereEqualTo("status", "").get();
    }

    public Task<QuerySnapshot> consultarSolicitacoesParaEntrega() {
        CollectionReference suprimentosRef = db.collection("solicitacoes");
        return suprimentosRef.whereNotIn("status", Arrays.asList("", "Recusada")).get();
    }

    public Task<DocumentSnapshot> consultarSolicitacao(String uid) {
        return db.collection("solicitacoes").document(uid).get();
    }

    public void excluirSolicitacao(String solicitacaoUid) {
        db.collection("solicitacoes").document(solicitacaoUid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void atualizarSolicitacaoStatus(String solicitacaoUid, String novoStatus) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", novoStatus);
        db.collection("solicitacoes").document(solicitacaoUid)
                .set(updates, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Status da Solicitação atualizado", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Erro ao atualizar o Status da Solicitação", Toast.LENGTH_SHORT).show();
                });
    }
}
