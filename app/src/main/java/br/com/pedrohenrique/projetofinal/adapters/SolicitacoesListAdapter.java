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
    private ArrayList<Solicitacao> solicitacaoList;

    public SolicitacoesListAdapter(Context context, ArrayList<Solicitacao> solicitacaoList) {
        super(context, 0, solicitacaoList);
        this.context = context;
        this.solicitacaoList = solicitacaoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_solicitacoes, parent, false);
        }

        Solicitacao currentSolicitacao = solicitacaoList.get(position);

        TextView tvNomeSolicitante = listItemView.findViewById(R.id.tvNomeSolicitante);
        TextView tvEnderecoSolicitante = listItemView.findViewById(R.id.tvEnderecoSolicitante);
        TextView tvContatoSolicitante = listItemView.findViewById(R.id.tvContatoSolicitante);
        TextView tvSuprimentoUid = listItemView.findViewById(R.id.tvSuprimentoUid);
        TextView tvDataSolicitacao = listItemView.findViewById(R.id.tvDataSolicitacao);
        TextView tvStatus = listItemView.findViewById(R.id.tvStatus);

        tvNomeSolicitante.setText("Nome do Solicitante: " + currentSolicitacao.nomeSolicitante);
        tvEnderecoSolicitante.setText("Endereço do Solicitante: " + currentSolicitacao.enderecoSolicitante);
        tvContatoSolicitante.setText("Contato do Solicitante: " + currentSolicitacao.contatoSolicitante);
        tvSuprimentoUid.setText("ID do Suprimento: " + currentSolicitacao.suprimentoUid);
        tvDataSolicitacao.setText("Data da Solicitação: " + currentSolicitacao.dataSolicitacao);
        tvStatus.setText("Status: " + currentSolicitacao.status);

        return listItemView;
    }
}
