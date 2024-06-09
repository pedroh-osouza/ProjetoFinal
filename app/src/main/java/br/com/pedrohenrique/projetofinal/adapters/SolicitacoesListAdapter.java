package br.com.pedrohenrique.projetofinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.model.Solicitacao;
public class SolicitacoesListAdapter extends ArrayAdapter<Solicitacao> {

    private Context context;
    private ArrayList<Solicitacao> supplyList;

    public SolicitacoesListAdapter(Context context, ArrayList<Solicitacao> supplyList) {
        super(context, 0, supplyList);
        this.context = context;
        this.supplyList = supplyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_solicitacoes, parent, false);
        }

        Solicitacao currentSolicitacao= supplyList.get(position);

        TextView tvUsuario = listItemView.findViewById(R.id.tvUsuario);
        TextView tvQuantity = listItemView.findViewById(R.id.tvSuprimento);
        TextView tvData = listItemView.findViewById(R.id.tvData);

        tvUsuario.setText("Usuario");
        tvQuantity.setText("Quantidade: " + "");
        tvData.setText("Data: " + "");

        return listItemView;
    }
}
