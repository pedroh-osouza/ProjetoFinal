package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.controller.SolicitacaoController;
import br.com.pedrohenrique.projetofinal.controller.SuprimentoController;
import br.com.pedrohenrique.projetofinal.controller.UsuarioController;
import br.com.pedrohenrique.projetofinal.model.Solicitacao;
import br.com.pedrohenrique.projetofinal.model.Suprimento;
import br.com.pedrohenrique.projetofinal.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.util.HashMap;
import java.util.Map;

public class DetalhesSolicitacaoActivity extends AppCompatActivity {

    private TextView tvTitulo, tvNomeSolicitante, tvEnderecoSolicitante, tvContatoSolicitante, tvEnderecoEntrega, tvContatoEntrega;
    private Button btnAceitar, btnRecusar;
    private String suprimentoUid;
    private String solicitacaoUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_solicitacao);

        tvTitulo = findViewById(R.id.tvTitle);
        tvNomeSolicitante = findViewById(R.id.tvNomeSolicitante);
        tvEnderecoSolicitante = findViewById(R.id.tvEnderecoSolicitante);
        tvContatoSolicitante = findViewById(R.id.tvContatoSolicitante);
        tvEnderecoEntrega = findViewById(R.id.tvEnderecoEntrega);
        tvContatoEntrega = findViewById(R.id.tvContatoEntrega);
        btnAceitar = findViewById(R.id.btnAceitar);
        btnRecusar = findViewById(R.id.btnRecusar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            suprimentoUid = extras.getString("suprimentoUid");
            solicitacaoUid = extras.getString("solicitacaoUid");
        } else {
            Toast.makeText(this, "Dados da Solicitação não encontrados", Toast.LENGTH_SHORT).show();
            finish();
        }

        SuprimentoController suprimentoController = new SuprimentoController(this);
        suprimentoController.consultarSuprimento(suprimentoUid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Suprimento suprimento = document.toObject(Suprimento.class);
                        tvTitulo.setText("Detalhes da Solicitação: " + suprimento.descricao);
                        UsuarioController usuarioController = new UsuarioController(DetalhesSolicitacaoActivity.this);
                        usuarioController.consultarUsuario(suprimento.usuarioUid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot.exists()) {
                                        Usuario usuario = documentSnapshot.toObject(Usuario.class);
                                        SolicitacaoController solicitacaoController = new SolicitacaoController(DetalhesSolicitacaoActivity.this);
                                        solicitacaoController.consultarSolicitacao(solicitacaoUid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()) {
                                                    DocumentSnapshot documentSolicitacao = task.getResult();
                                                    if(documentSolicitacao.exists()) {
                                                        Solicitacao solicitacao = documentSolicitacao.toObject(Solicitacao.class);
                                                        tvNomeSolicitante.setText("Nome do Solicitante: " + solicitacao.nomeSolicitante);
                                                        tvEnderecoSolicitante.setText("Endereço para entregar o suprimento: " + solicitacao.enderecoSolicitante);
                                                        tvContatoSolicitante.setText("Contato do Solicitante: " + solicitacao.contatoSolicitante);
                                                        tvEnderecoEntrega.setText("Endereço para retirar suprimento: " + usuario.endereco);
                                                        tvContatoEntrega.setText("Contato para retirada: " + usuario.telefone);
                                                        if (solicitacao.status.equals("Aceita")) {
                                                            btnAceitar.setEnabled(false);
                                                            btnRecusar.setEnabled(true);
                                                        } else if (solicitacao.status.equals("Recusada")) {
                                                            btnAceitar.setEnabled(true);
                                                            btnRecusar.setEnabled(false);
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        SolicitacaoController solicitacaoController = new SolicitacaoController(DetalhesSolicitacaoActivity.this);
        btnAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitacaoController.atualizarSolicitacaoStatus(solicitacaoUid, "Pendente");
                finish();
            }
        });

        btnRecusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitacaoController.excluirSolicitacao(solicitacaoUid);
                finish();
            }
        });
    }
}
