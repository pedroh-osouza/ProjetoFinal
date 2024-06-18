package br.com.pedrohenrique.projetofinal.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

        Places.initialize(getApplicationContext(), "AIzaSyDFpY4P6XztSphYhCuuN_OExabqj-iNiAE");

        etEnderecoSolicitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG
                        , Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(SolicitarSuprimentoActivity.this);
                startActivityForResult(intent, 100);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            etEnderecoSolicitante.setText(place.getAddress());
        } else {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
