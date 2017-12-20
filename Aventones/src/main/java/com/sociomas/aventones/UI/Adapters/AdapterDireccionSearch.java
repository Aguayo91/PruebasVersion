package com.sociomas.aventones.UI.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.ViewHolder.ViewHolderAutocompleteSearch;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.AutoComplete.PredictionSections;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by oemy9 on 31/08/2017.
 */

/*ADAPTADOR QUE SE USARÁ EN EL DIALOGO DE BÚSQUEDA*/
public class AdapterDireccionSearch  extends SectionedRecyclerViewAdapter<ViewHolderAutocompleteSearch> {

    private ArrayList<PredictionSections>listPrediction;
    private Context context;
    private RecyclerItemClickListener itemClickListener;
    private LayoutInflater layoutInflater;

    public AdapterDireccionSearch(Context context,ArrayList<PredictionSections>listPrediction){
        this.context=context;
        this.listPrediction=listPrediction;
        Collections.reverse(this.listPrediction);
        this.layoutInflater=LayoutInflater.from(this.context);
    }

    public void setListPrediction(ArrayList<PredictionSections> listPredictionParam) {
        this.listPrediction = listPredictionParam;
        Collections.reverse(this.listPrediction);
        notifyDataSetChanged();
    }

    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        return super.getItemViewType(section, relativePosition, absolutePosition);
    }
    @Override
    public ViewHolderAutocompleteSearch onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(viewType==VIEW_TYPE_ITEM?R.layout.item_query_autocomplete : R.layout.item_header, parent, false);
        return new ViewHolderAutocompleteSearch(itemView);
    }


    @Override
    public int getSectionCount() {
        return listPrediction.size();
    }

    @Override
    public int getItemCount(int position) {
        return listPrediction.get(position).getListPrediction().size();
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolderAutocompleteSearch holder, int position) {
        String section=listPrediction.get(position).getHeaderTitle();
        holder.tvSeccion.setText(section);
    }

    @Override
    public void onBindViewHolder(ViewHolderAutocompleteSearch holder, int section, final int relativePosition, int absolutePosition) {
        ArrayList<Prediction>listPredictionSection=this.listPrediction.get(section).getListPrediction();
        final Prediction selectedItem=listPredictionSection.get(relativePosition);
        if(selectedItem.getStructuredFormatting()!=null) {
            holder.tvAutocompleteTitle.setText(selectedItem.getStructuredFormatting().getMainText());
            holder.tvAutocompleteDescription.setText(selectedItem.getStructuredFormatting().getSecondaryText());
            holder.tvAutocompleteDescription.setVisibility(View.VISIBLE);
        }
        else{
            holder.tvAutocompleteTitle.setText(selectedItem.getDescription());
            holder.tvAutocompleteDescription.setVisibility(View.INVISIBLE);// SE OCULTA LA DESCRIPCIÓN
        }
        Picasso.with(this.context).load(R.drawable.ic_marker).resize(60,60).into(holder.imageMaker);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.OnItemClickListener(relativePosition,selectedItem);
            }
        });
    }
    public int getTotal() {
        return listPrediction.size();
    }
    public void clearItems(){
        listPrediction.clear();
        notifyDataSetChanged();
    }
}
