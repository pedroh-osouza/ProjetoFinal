package br.com.pedrohenrique.projetofinal.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.adapters.SuprimentosListAdapter;
import br.com.pedrohenrique.projetofinal.model.Suprimento;
import br.com.pedrohenrique.projetofinal.controller.SuprimentoController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SuprimentosActivity extends AppCompatActivity {
    private ListView listViewSupplies;
    private ArrayList<Suprimento> supplyList;
    private SuprimentosListAdapter adapter;
    private SuprimentoController suprimentoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suprimentos);

        listViewSupplies = findViewById(R.id.listViewSupplies);
        Button btnAddSupply = findViewById(R.id.btnAddSupply);
        supplyList = new ArrayList<>();
        adapter = new SuprimentosListAdapter(this, supplyList);
        listViewSupplies.setAdapter(adapter);
        suprimentoController = new SuprimentoController();

        // Consultar os suprimentos no Firebase Firestore
        consultarSuprimentos();

        btnAddSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuprimentosActivity.this, AdicionarSuprimentoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void consultarSuprimentos() {
        suprimentoController.consultarSuprimentos().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Limpa a lista atual de suprimentos
                    supplyList.clear();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Para cada documento encontrado, cria um objeto Suprimento e adiciona à lista
                        String uid = document.getId();
                        String descricao = document.getString("descricao");
                        Integer quantidade = document.getLong("quantidade").intValue();
                        String unidadeMedida = document.getString("unidadeMedida");
                        String usuarioUid = document.getString("usuarioUid");
                        supplyList.add(new Suprimento(uid, descricao, quantidade, unidadeMedida, usuarioUid));
                    }
                    // Notifica o adapter que os dados foram alterados
                    adapter.notifyDataSetChanged();
                } else {
                    // Tratar erro
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega a lista de suprimentos ao retomar a atividade
        consultarSuprimentos();
    }
}
