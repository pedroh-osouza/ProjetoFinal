package br.com.pedrohenrique.projetofinal.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.adapters.SolicitacoesListAdapter;
import br.com.pedrohenrique.projetofinal.controller.SolicitacaoController;
import br.com.pedrohenrique.projetofinal.model.Solicitacao;
import br.com.pedrohenrique.projetofinal.model.Suprimento;

public class SolicitacoesActivity extends AppCompatActivity {
    private ListView listViewSolicitacoes;
    private ArrayList<Solicitacao> solicitacaoList;
    private SolicitacoesListAdapter adapter;
    private SolicitacaoController solicitacaoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacoes);

        listViewSolicitacoes = findViewById(R.id.listViewSolicitacoes);
        solicitacaoList = new ArrayList<>();
        adapter = new SolicitacoesListAdapter(this, solicitacaoList);
        listViewSolicitacoes.setAdapter(adapter);
        solicitacaoController = new SolicitacaoController(this);

        consultarSolicitacoes();
        listViewSolicitacoes.setOnItemClickListener((parent, view, position, id) -> {
            // Obter a solicitação clicada
            Solicitacao solicitacaoClicada = solicitacaoList.get(position);

            // Criar uma Intent para iniciar DetalhesSolicitacaoActivity
            Intent intent = new Intent(SolicitacoesActivity.this, DetalhesSolicitacaoActivity.class);

            // Adicionar os dados da solicitação à Intent
            intent.putExtra("suprimentoUid", solicitacaoClicada.suprimentoUid);
            intent.putExtra("solicitacaoUid", solicitacaoClicada.uid);

            // Iniciar DetalhesSolicitacaoActivity
            startActivity(intent);
        });
    }

    private void consultarSolicitacoes() {
        solicitacaoController.consultarSolicitacoes().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    solicitacaoList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        String nomeSolicitante = document.getString("nomeSolicitante");
                        String enderecoSolicitante = document.getString("enderecoSolicitante");
                        String contatoSolicitante = document.getString("contatoSolicitante");
                        String suprimentoUid = document.getString("suprimentoUid");
                        String dataSolicitacao = document.getString("dataSolicitacao");
                        String status = document.getString("status");
                        solicitacaoList.add(new Solicitacao(uid, nomeSolicitante, enderecoSolicitante, contatoSolicitante, suprimentoUid, dataSolicitacao,status));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
