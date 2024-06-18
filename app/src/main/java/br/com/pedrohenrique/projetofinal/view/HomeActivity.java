package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.controller.UsuarioController;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private Button btnListSupplies, btnListRequests, btnListDeliveries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnListSupplies = findViewById(R.id.btnListSupplies);
        btnListRequests = findViewById(R.id.btnListRequests);
        btnListDeliveries = findViewById(R.id.btnListDeliveries);
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
                UsuarioController usuarioController = new UsuarioController(HomeActivity.this);
                if(usuarioController.isConvidado()) {
                    Toast.makeText(HomeActivity.this, "Faça Login para visualizar as solicitações", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeActivity.this, SolicitacoesActivity.class);
                startActivity(intent);
            }
        });

        btnListDeliveries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioController usuarioController = new UsuarioController(HomeActivity.this);
                if(usuarioController.isConvidado()) {
                    Toast.makeText(HomeActivity.this, "Faça Login para visualizar as solicitações", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeActivity.this, EntregasActivity.class);
                startActivity(intent);
            }
        });


    }
}