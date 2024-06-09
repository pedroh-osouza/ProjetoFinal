package br.com.pedrohenrique.projetofinal.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentReference;
import br.com.pedrohenrique.projetofinal.model.Suprimento;

public class SuprimentoController {

    private FirebaseFirestore db;
    private Context context;
    public SuprimentoController(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    public Task<QuerySnapshot> consultarSuprimentos() {
        CollectionReference suprimentosRef = db.collection("suprimentos");
        return suprimentosRef.get();
    }

    public void cadastrar(String descricao, Integer quantidade, String unidadeMedida) {
        UsuarioController usuarioController = new UsuarioController(context);
        String uidUsuario = usuarioController.getUidUsuarioAtual();
        DocumentReference referencia = db.collection("suprimentos").document();
        Suprimento suprimento = new Suprimento(referencia.getId(), descricao, quantidade, unidadeMedida, uidUsuario);
        referencia.set(suprimento);
    }

    public Task<DocumentSnapshot> consultarSuprimento(String uid) {
        return db.collection("suprimentos").document(uid).get();
    }
    public void excluirSuprimento(String suprimentoUid) {
        db.collection("suprimentos").document(suprimentoUid)
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

}