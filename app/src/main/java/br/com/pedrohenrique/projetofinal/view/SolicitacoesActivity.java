package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.adapters.SolicitacoesListAdapter;
import br.com.pedrohenrique.projetofinal.model.Solicitacao;
import android.view.View;
import android.widget.AdapterView;

public class SolicitacoesActivity extends AppCompatActivity {
    private ListView listViewSupplies;
    private ArrayList<Solicitacao> supplyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacoes);

        listViewSupplies = findViewById(R.id.listViewRequests);

        supplyList = new ArrayList<>();
        supplyList.add(new Solicitacao("1", "predo", "1", "2024-02-14", "ENTREGUE"));
        supplyList.add(new Solicitacao("1", "predo", "1", "2024-02-14", "ENTREGUE"));

        SolicitacoesListAdapter adapter = new SolicitacoesListAdapter(this, supplyList);

        listViewSupplies.setAdapter(adapter);
        listViewSupplies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aqui você pode implementar a lógica para editar ou excluir o suprimento selecionado
                // Por exemplo, você pode abrir uma nova atividade para edição ou exclusão do suprimento
                // Neste exemplo, não implementaremos essa lógica
            }
        });
    }
}