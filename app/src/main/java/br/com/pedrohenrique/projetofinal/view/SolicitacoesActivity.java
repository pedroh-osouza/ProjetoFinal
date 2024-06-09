package br.com.pedrohenrique.projetofinal.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
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

public class SolicitacoesActivity extends AppCompatActivity {
    private ListView listViewSolicitacoes;
    private ArrayList<Solicitacao> solicitacaoList;
    private SolicitacoesListAdapter adapter;
    private SolicitacaoController solicitacaoController;
    private TextView tvEmptyListMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacoes);

        listViewSolicitacoes = findViewById(R.id.listViewSolicitacoes);
        solicitacaoList = new ArrayList<>();
        adapter = new SolicitacoesListAdapter(this, solicitacaoList);
        listViewSolicitacoes.setAdapter(adapter);
        solicitacaoController = new SolicitacaoController(this);
        tvEmptyListMessage = findViewById(R.id.tvEmptyListMessage);

        consultarSolicitacoes();
        listViewSolicitacoes.setOnItemClickListener((parent, view, position, id) -> {
            Solicitacao solicitacaoClicada = solicitacaoList.get(position);
            Intent intent = new Intent(SolicitacoesActivity.this, DetalhesSolicitacaoActivity.class);
            intent.putExtra("suprimentoUid", solicitacaoClicada.suprimentoUid);
            intent.putExtra("solicitacaoUid", solicitacaoClicada.uid);
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
                    if (solicitacaoList.isEmpty()) {
                        tvEmptyListMessage.setVisibility(View.VISIBLE);
                    } else {
                        tvEmptyListMessage.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        consultarSolicitacoes();
    }
}
