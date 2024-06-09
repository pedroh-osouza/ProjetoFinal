package br.com.pedrohenrique.projetofinal.controller;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.DocumentReference;
import br.com.pedrohenrique.projetofinal.model.Suprimento;

public class SuprimentoController {

    private FirebaseFirestore db;
    private Suprimento suprimento;

    public SuprimentoController() {
        db = FirebaseFirestore.getInstance();
    }

    public Suprimento consultar(String uid) {
        CollectionReference referencia = db.collection("suprimentos");
        referencia.document(uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                }
            }
        });

    }

    public void cadastrar(String descricao, Integer quantidade, String unidadeMedida) {
        UsuarioController usuarioController = new UsuarioController();
        String uidUsuario = usuarioController.getUidUsuarioAtual();
        DocumentReference referencia = db.collection("suprimentos").document();
        Suprimento suprimento = new Suprimento(referencia.getId(), descricao, quantidade, unidadeMedida, uidUsuario);
        referencia.set(suprimento);
    }
}
