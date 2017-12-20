package com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolders.ViewHolderSearchDialog;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by oemy9 on 24/05/2017.
 */

@SuppressWarnings("unused")
public class AdapterSearchDialog extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SearchBoxItem>itemArrayList;
    private ArrayList<SearchBoxItem>filteArrayList;
    public AdapterSearchDialog(Context context,ArrayList<SearchBoxItem>itemArrayList){
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.itemArrayList=itemArrayList;
        this.filteArrayList=new ArrayList<>();
    }

    public void resetAdapter(ArrayList<SearchBoxItem>itemArrayList){
        this.itemArrayList=itemArrayList;
        notifyDataSetChanged();
    }

    public void filtrar(String query,ArrayList<SearchBoxItem>originalArrayList){
        this.itemArrayList=originalArrayList;
        this.filteArrayList.clear();
        for(SearchBoxItem item: itemArrayList){
            String nombre=item.getValue().replace(
                    item.getId().concat(" -")
                    , "").trim();
            if(nombre.toLowerCase(Locale.ENGLISH).startsWith(query) || nombre.toLowerCase(Locale.ENGLISH).contains(query) ||
                    item.getId().startsWith(query) || item.getId().contains(query))
            {
                filteArrayList.add(item);
            }
        }
        this.itemArrayList=filteArrayList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemArrayList.size();
    }

    @Override
    public SearchBoxItem getItem(int i) {
        return itemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolderSearchDialog holder;
        if(view==null) {
            holder =new  ViewHolderSearchDialog();
            view = layoutInflater.inflate(R.layout.item_search_item_dialog, viewGroup, false);
            holder.tvQuery=(TextView)view.findViewById(R.id.tvQuery);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolderSearchDialog)view.getTag();
        }
        SearchBoxItem item=getItem(position);
        holder.tvQuery.setText(getItem(position).getValue());
        return view;
    }
}
