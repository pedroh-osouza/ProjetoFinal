package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class DetalhesEntregaActivity extends AppCompatActivity {
    private TextView tvTitulo, tvNomeSolicitante, tvEnderecoSolicitante, tvContatoSolicitante, tvEnderecoEntrega, tvContatoEntrega;
    private RadioGroup rgStatus;
    private Button btnConfirmar;
    private String suprimentoUid;
    private String solicitacaoUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_entrega);

        tvTitulo = findViewById(R.id.tvTitle);
        tvNomeSolicitante = findViewById(R.id.tvNomeSolicitante);
        tvEnderecoSolicitante = findViewById(R.id.tvEnderecoSolicitante);
        tvContatoSolicitante = findViewById(R.id.tvContatoSolicitante);
        tvEnderecoEntrega = findViewById(R.id.tvEnderecoEntrega);
        tvContatoEntrega = findViewById(R.id.tvContatoEntrega);
        rgStatus = findViewById(R.id.rgStatus);
        btnConfirmar = findViewById(R.id.btnConfirmar);

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
                        UsuarioController usuarioController = new UsuarioController(DetalhesEntregaActivity.this);
                        usuarioController.consultarUsuario(suprimento.usuarioUid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot.exists()) {
                                        Usuario usuario = documentSnapshot.toObject(Usuario.class);
                                        SolicitacaoController solicitacaoController = new SolicitacaoController(DetalhesEntregaActivity.this);
                                        solicitacaoController.consultarSolicitacao(solicitacaoUid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot documentSolicitacao = task.getResult();
                                                    if (documentSnapshot.exists()) {
                                                        Solicitacao solicitacao = documentSolicitacao.toObject(Solicitacao.class);
                                                        tvNomeSolicitante.setText("Nome do Solicitante: " + solicitacao.nomeSolicitante);
                                                        tvEnderecoSolicitante.setText("Endereço para entregar o suprimento: " + solicitacao.enderecoSolicitante);
                                                        tvContatoSolicitante.setText("Contato do Solicitante: " + solicitacao.contatoSolicitante);
                                                        tvEnderecoEntrega.setText("Endereço para retirar suprimento: " + usuario.endereco);
                                                        tvContatoEntrega.setText("Contato para retirada: " + usuario.telefone);
                                                        if (solicitacao.status.equals("Pendente")) {
                                                            RadioButton radioButtonPendente = findViewById(R.id.rbPendente);
                                                            radioButtonPendente.setChecked(true);
                                                        } else if (solicitacao.status.equals("Em Rota")) {
                                                            RadioButton radioButtonEmRota = findViewById(R.id.rbEmRota);
                                                            radioButtonEmRota.setChecked(true);
                                                        } else if (solicitacao.status.equals("Concluída")) {
                                                            RadioButton radioButtonConcluida = findViewById(R.id.rbConcluida);
                                                            radioButtonConcluida.setChecked(true);
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

        btnConfirmar.setOnClickListener(view -> {
            int selectedId = rgStatus.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            String novoStatus = radioButton.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> updates = new HashMap<>();
            updates.put("status", novoStatus);
            db.collection("solicitacoes").document(solicitacaoUid)
                    .set(updates, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Status da Solicitação atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erro ao atualizar o Status da Solicitação", Toast.LENGTH_SHORT).show();
                    });
            if (novoStatus.equals("Concluída")) {
                suprimentoController.excluirSuprimento(suprimentoUid);
                SolicitacaoController solicitacaoController = new SolicitacaoController(DetalhesEntregaActivity.this);
                solicitacaoController.excluirSolicitacao(solicitacaoUid);
            }
            finish();
        });
    }

}
