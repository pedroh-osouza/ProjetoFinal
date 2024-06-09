package br.com.pedrohenrique.projetofinal.controller;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentReference;
import br.com.pedrohenrique.projetofinal.model.Suprimento;

public class SuprimentoController {

    private FirebaseFirestore db;

    public SuprimentoController() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<QuerySnapshot> consultarSuprimentos() {
        CollectionReference suprimentosRef = db.collection("suprimentos");
        return suprimentosRef.get();
    }

    public void cadastrar(String descricao, Integer quantidade, String unidadeMedida) {
        UsuarioController usuarioController = new UsuarioController();
        String uidUsuario = usuarioController.getUidUsuarioAtual();
        DocumentReference referencia = db.collection("suprimentos").document();
        Suprimento suprimento = new Suprimento(referencia.getId(), descricao, quantidade, unidadeMedida, uidUsuario);
        referencia.set(suprimento);
    }
}
