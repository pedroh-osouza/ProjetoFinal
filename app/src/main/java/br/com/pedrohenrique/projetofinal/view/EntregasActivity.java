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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.adapters.SolicitacoesListAdapter;
import br.com.pedrohenrique.projetofinal.controller.SolicitacaoController;
import br.com.pedrohenrique.projetofinal.controller.SuprimentoController;
import br.com.pedrohenrique.projetofinal.controller.UsuarioController;
import br.com.pedrohenrique.projetofinal.model.Solicitacao;
import br.com.pedrohenrique.projetofinal.model.Suprimento;

public class EntregasActivity extends AppCompatActivity {
    private ListView listViewSolicitacoes;
    private ArrayList<Solicitacao> solicitacaoList;
    private SolicitacoesListAdapter adapter;
    private SolicitacaoController solicitacaoController;
    private TextView tvEmptyListMessage, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacoes);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Lista Solicitações para Entrega");
        listViewSolicitacoes = findViewById(R.id.listViewSolicitacoes);
        solicitacaoList = new ArrayList<>();
        adapter = new SolicitacoesListAdapter(this, solicitacaoList);
        listViewSolicitacoes.setAdapter(adapter);
        solicitacaoController = new SolicitacaoController(this);
        tvEmptyListMessage = findViewById(R.id.tvEmptyListMessage);
        tvEmptyListMessage.setVisibility(View.INVISIBLE);
        consultarSolicitacoes();
        listViewSolicitacoes.setOnItemClickListener((parent, view, position, id) -> {
            Solicitacao solicitacaoClicada = solicitacaoList.get(position);
            Intent intent = new Intent(EntregasActivity.this, DetalhesEntregaActivity.class);
            intent.putExtra("suprimentoUid", solicitacaoClicada.suprimentoUid);
            intent.putExtra("solicitacaoUid", solicitacaoClicada.uid);
            startActivity(intent);
        });
    }

    private void consultarSolicitacoes() {
        solicitacaoController.consultarSolicitacoesParaEntrega().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    solicitacaoList.clear();
                    Set<String> suprimentoUids = new HashSet<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        String nomeSolicitante = document.getString("nomeSolicitante");
                        String enderecoSolicitante = document.getString("enderecoSolicitante");
                        String contatoSolicitante = document.getString("contatoSolicitante");
                        String suprimentoUid = document.getString("suprimentoUid");
                        String dataSolicitacao = document.getString("dataSolicitacao");
                        String status = document.getString("status");

                        solicitacaoList.add(new Solicitacao(uid, nomeSolicitante, enderecoSolicitante, contatoSolicitante, suprimentoUid, dataSolicitacao, status));
                        suprimentoUids.add(suprimentoUid);
                    }

                    if (suprimentoUids.isEmpty()) {
                        updateListView();
                        return;
                    }

                    for (String suprimentoUid : suprimentoUids) {
                        SuprimentoController suprimentoController = new SuprimentoController(EntregasActivity.this);
                        suprimentoController.consultarSuprimento(suprimentoUid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSuprimento = task.getResult();
                                    if (documentSuprimento.exists()) {
                                        Suprimento suprimento = documentSuprimento.toObject(Suprimento.class);
                                        UsuarioController usuarioController = new UsuarioController(EntregasActivity.this);
                                        assert suprimento != null;
                                        if (!suprimento.usuarioUid.equals(usuarioController.getUidUsuarioAtual())) {
                                            removeSolicitacoesWithSuprimentoUid(suprimentoUid);
                                        }
                                    }
                                }
                                if (suprimentoUid.equals(suprimentoUids.toArray()[suprimentoUids.size() - 1])) {
                                    updateListView();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void removeSolicitacoesWithSuprimentoUid(String suprimentoUid) {
        for (int i = 0; i < solicitacaoList.size(); i++) {
            if (solicitacaoList.get(i).suprimentoUid.equals(suprimentoUid)) {
                solicitacaoList.remove(i);
                i--;
            }
        }
    }

    private void updateListView() {
        if (solicitacaoList.isEmpty()) {
            tvEmptyListMessage.setVisibility(View.VISIBLE);
        } else {
            tvEmptyListMessage.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        consultarSolicitacoes();
    }
}
