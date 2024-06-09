package br.com.pedrohenrique.projetofinal.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.adapters.SuprimentosListAdapter;
import br.com.pedrohenrique.projetofinal.controller.UsuarioController;
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
        suprimentoController = new SuprimentoController(this);
        consultarSuprimentos();
        btnAddSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioController usuarioController = new UsuarioController(SuprimentosActivity.this);
                if(usuarioController.isConvidado()) {
                    Toast.makeText(SuprimentosActivity.this, "Faça login para cadastrar um suprimento", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                    supplyList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        String descricao = document.getString("descricao");
                        Integer quantidade = document.getLong("quantidade").intValue();
                        String unidadeMedida = document.getString("unidadeMedida");
                        String usuarioUid = document.getString("usuarioUid");
                        supplyList.add(new Suprimento(uid, descricao, quantidade, unidadeMedida, usuarioUid));
                    }
                    adapter.notifyDataSetChanged();
                } else {

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        consultarSuprimentos();
    }
}
