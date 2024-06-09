package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.controller.SuprimentoController;
import br.com.pedrohenrique.projetofinal.model.Suprimento;

public class AdicionarSuprimentoActivity extends AppCompatActivity {

    private EditText etDescricao;
    private EditText etQuantidade;
    private EditText etUnidadeMedida;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_suprimento);

        etDescricao = findViewById(R.id.etDescricao);
        etQuantidade = findViewById(R.id.etQuantidade);
        etUnidadeMedida = findViewById(R.id.etUnidadeMedida);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = etDescricao.getText().toString();
                int quantidade = Integer.parseInt(etQuantidade.getText().toString());
                String unidadeMedida = etUnidadeMedida.getText().toString();
                SuprimentoController suprimentoController = new SuprimentoController();
                suprimentoController.cadastrar(descricao, quantidade, unidadeMedida);
                finish();
            }
        });
    }
}