package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.controller.SolicitacaoController;
import br.com.pedrohenrique.projetofinal.model.Solicitacao;

public class SolicitarSuprimentoActivity extends AppCompatActivity {
    private TextView tvSupplyDetails;
    private EditText etNomeSolicitante, etEnderecoSolicitante, etContatoSolicitante;
    private Button btnRequestSupply;
    private SolicitacaoController solicitacaoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_suprimento);

        tvSupplyDetails = findViewById(R.id.tvSupplyDetails);
        etNomeSolicitante = findViewById(R.id.etNomeSolicitante);
        etEnderecoSolicitante = findViewById(R.id.etEnderecoSolicitante);
        etContatoSolicitante = findViewById(R.id.etContatoSolicitante);
        btnRequestSupply = findViewById(R.id.btnRequestSupply);
        solicitacaoController = new SolicitacaoController(this);

        String suprimentoUid = getIntent().getStringExtra("suprimentoUid");
        String suprimentoDescricao = getIntent().getStringExtra("suprimentoDescricao");

        tvSupplyDetails.setText("Detalhes do Suprimento: " + suprimentoDescricao);

        btnRequestSupply.setOnClickListener(view -> {
            String nomeSolicitante = etNomeSolicitante.getText().toString();
            String enderecoSolicitante = etEnderecoSolicitante.getText().toString();
            String contatoSolicitante = etContatoSolicitante.getText().toString();

            if (nomeSolicitante.isEmpty() || enderecoSolicitante.isEmpty() || contatoSolicitante.isEmpty()) {
                Toast.makeText(SolicitarSuprimentoActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            String dataSolicitacao = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String status = "Pendente";

            Solicitacao solicitacao = new Solicitacao(
                    null,
                    nomeSolicitante,
                    enderecoSolicitante,
                    contatoSolicitante,
                    suprimentoUid,
                    dataSolicitacao,
                    status
            );

            solicitarSuprimento(solicitacao);
        });
    }

    private void solicitarSuprimento(Solicitacao solicitacao) {
        solicitacaoController.cadastrarSolicitacao(solicitacao).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(SolicitarSuprimentoActivity.this, "Solicitação enviada com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(SolicitarSuprimentoActivity.this, "Erro ao enviar solicitação", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
