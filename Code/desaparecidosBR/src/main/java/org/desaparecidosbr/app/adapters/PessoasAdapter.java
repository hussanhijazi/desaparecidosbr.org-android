package org.desaparecidosbr.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.desaparecidosbr.app.R;
import org.desaparecidosbr.app.entity.Pessoa;
import org.desaparecidosbr.app.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class PessoasAdapter extends BaseAdapter implements Filterable {

    // private final static String URL_LOGOS =
    // "http://images.pessoasnoparaguai.com.br.s3.amazonaws.com/logos/";
    PessoaSuporte itemHolder;
    private LayoutInflater mInflater;
    private ArrayList<Pessoa> origPessoasList;
    private ArrayList<Pessoa> pessoasList;
    private Context context;
    private Filter pessoasFilter;
//    private RequestQueue rq;


    public PessoasAdapter(Context context, ArrayList<Pessoa> pessoasList) {
        // Itens do listview
        this.pessoasList = pessoasList;
        this.origPessoasList = pessoasList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    public int getCount() {
        return pessoasList.size();
    }

    public Pessoa getItem(int position) {
        return pessoasList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void resetData() {
        pessoasList = origPessoasList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        itemHolder = new PessoaSuporte();
        // se a view estiver nula (nunca criada), inflamos o layout nela.
        if (convertView == null) {
            // if(!itens.get(position).isGroupHeader()){
            // infla o layout para podermos pegar as views
            view = mInflater.inflate(R.layout.activity_pessoa_row, parent, false);

            // cria um item de suporte para n�o precisarmos sempre
            // inflar as mesmas informacoes

            itemHolder.nome = ((TextView) view.findViewById(R.id.pessoa_txt_nome));
            itemHolder.foto = ((ImageView) view.findViewById(R.id.pessoa_img));
            itemHolder.dataDes = ((TextView) view.findViewById(R.id.pessoa_txt_data_des));
            itemHolder.localDes = ((TextView) view.findViewById(R.id.pessoa_txt_local));

            //  itemHolder.logo = ((ImageView) view.findViewById(R.id.loja_logo_img));
            view.setTag(itemHolder);
            // }
            // else
            // {
            // itemHolder = new LojaSuporte();
            // view = mInflater.inflate(R.layout.group_header_item, parent,
            // false);
            // itemHolder.txtTitle = ((TextView)
            // view.findViewById(R.id.header));
            // view.setTag(itemHolder);
            // }
        } else {

            itemHolder = (PessoaSuporte) view.getTag();
        }
        Pessoa item = pessoasList.get(position);
        itemHolder.nome.setText(item.getNome().toUpperCase());
        itemHolder.dataDes.setText(Utility.formatData(item.getData_des()));
        itemHolder.localDes.setText(item.getCidade() + "/" + item.getEstado());
        if (item.getFoto() != null)
            Picasso.get().load(item.getFoto().replace("newwidth=250&newheight=250", "newwidth=90&newheight=90")).into(itemHolder.foto);
        return view;
    }

    public ArrayList<Pessoa> getItens() {
        return pessoasList;
    }

    public void setItens(ArrayList<Pessoa> itens) {
        this.pessoasList = itens;
    }

    @Override
    public Filter getFilter() {
        if (pessoasFilter == null)
            pessoasFilter = new PessoasFilter();

        return pessoasFilter;
    }

    private class PessoaSuporte {
        ImageView foto;
        TextView nome;
        TextView dataDes;
        TextView localDes;
    }

    private class PessoasFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origPessoasList;
                results.count = origPessoasList.size();
            } else {
                // We perform filtering operation
                List<Pessoa> nPessoasList = new ArrayList<Pessoa>();

                for (Pessoa p : pessoasList) {
                    if (p.getNome().toUpperCase().startsWith(constraint.toString().toUpperCase())
                            || p.getCidade().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nPessoasList.add(p);
                }

                results.values = nPessoasList;
                results.count = nPessoasList.size();


            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            pessoasList = (ArrayList<Pessoa>) results.values;
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                pessoasList = (ArrayList<Pessoa>) results.values;

//                origPessoasList = pessoasList;
                notifyDataSetChanged();
            }

        }

    }

}
