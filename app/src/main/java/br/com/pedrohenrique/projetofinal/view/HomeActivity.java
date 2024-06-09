package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import br.com.pedrohenrique.projetofinal.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button btnListSupplies;
    private Button btnListRequests;
    private Button btnAddSupply;
    private Button btnAddRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnListSupplies = findViewById(R.id.btnListSupplies);
        btnListRequests = findViewById(R.id.btnListRequests);
        btnAddSupply = findViewById(R.id.btnAddSupply);
        btnAddRequest = findViewById(R.id.btnAddRequest);

        btnListSupplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SuprimentosActivity.class);
                startActivity(intent);
            }
        });
//
//        // Navegação para listar as solicitações de suprimentos
//        btnListRequests.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ListRequestsActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Navegação para adicionar um novo suprimento
//        btnAddSupply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, AddSupplyActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Navegação para adicionar uma nova solicitação de suprimento
//        btnAddRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, AddRequestActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}