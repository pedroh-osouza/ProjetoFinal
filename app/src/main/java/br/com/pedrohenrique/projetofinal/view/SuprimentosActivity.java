package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.adapters.SuprimentosListAdapter;
import br.com.pedrohenrique.projetofinal.model.Suprimento;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class SuprimentosActivity extends AppCompatActivity {
    private ListView listViewSupplies;
    private ArrayList<Suprimento> supplyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suprimentos);

        listViewSupplies = findViewById(R.id.listViewSupplies);
        Button btnAddSupply = findViewById(R.id.btnAddSupply);

        // Aqui você precisa recuperar a lista de suprimentos do banco de dados ou de onde eles estão armazenados
        supplyList = new ArrayList<>();
        supplyList.add(new Suprimento("1", "Alimento enlatado", 10, "unidade", "usuario1"));
        supplyList.add(new Suprimento("2", "Água mineral", 20, "litro", "usuario2"));

        SuprimentosListAdapter adapter = new SuprimentosListAdapter(this, supplyList);

        listViewSupplies.setAdapter(adapter);

        listViewSupplies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aqui você pode implementar a lógica para editar ou excluir o suprimento selecionado
                // Por exemplo, você pode abrir uma nova atividade para edição ou exclusão do suprimento
                // Neste exemplo, não implementaremos essa lógica
            }
        });

        btnAddSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuprimentosActivity.this, AdicionarSuprimentoActivity.class);
                startActivity(intent);
            }
        });
    }
}