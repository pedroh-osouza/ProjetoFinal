package br.com.pedrohenrique.projetofinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.model.Suprimento;
public class SuprimentosListAdapter extends ArrayAdapter<Suprimento> {

    private Context context;
    private ArrayList<Suprimento> supplyList;

    public SuprimentosListAdapter(Context context, ArrayList<Suprimento> supplyList) {
        super(context, 0, supplyList);
        this.context = context;
        this.supplyList = supplyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_suprimentos, parent, false);
        }

        Suprimento currentSupply = supplyList.get(position);

        TextView tvDescription = listItemView.findViewById(R.id.tvDescription);
        TextView tvQuantity = listItemView.findViewById(R.id.tvQuantity);
        TextView tvUnit = listItemView.findViewById(R.id.tvUnit);

        tvDescription.setText(currentSupply.descricao);
        tvQuantity.setText("Quantidade: " + currentSupply.quantidade);
        tvUnit.setText("Unidade de Medida: " + currentSupply.unidadeMedida);

        return listItemView;
    }
}
