package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import br.com.pedrohenrique.projetofinal.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button btnListSupplies;
    private Button btnListRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnListSupplies = findViewById(R.id.btnListSupplies);
        btnListRequests = findViewById(R.id.btnListRequests);
        btnListSupplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SuprimentosActivity.class);
                startActivity(intent);
            }
        });

        btnListRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SolicitacoesActivity.class);
                startActivity(intent);
            }
        });
    }
}