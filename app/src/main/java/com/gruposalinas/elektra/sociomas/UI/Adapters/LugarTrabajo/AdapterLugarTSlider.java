package com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.IAdapterBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import java.util.ArrayList;

/**
 * Created by oemy9 on 17/11/2017.
 */

public class AdapterLugarTSlider extends RecyclerView.Adapter<AdapterLugarTSlider.ViewHolder> implements IAdapterBase {

    private Context ctx;
    private ArrayList<LugarTrabajo> listLugarTrabajo;
    private LayoutInflater layoutInflater;
    private RecyclerItemClickListener listener;

    public AdapterLugarTSlider(Context ctx, ArrayList<LugarTrabajo> listLugarTrabajo) {
        this.ctx = ctx;
        this.listLugarTrabajo = listLugarTrabajo;
        this.layoutInflater = LayoutInflater.from(this.ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_lugar_trabajo_slide, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LugarTrabajo t = getItem(position);
        if (t != null) {
            String title = t.getVaNombrePos() != null ? t.getVaNombrePos() : t.getVaDescripcionZnPosic();
            holder.tvAutocompleteTitle.setText(Utils.toTitleCase(title));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        t.setChecked(!t.isChecked());
                        holder.imgCheck.setVisibility(t.isChecked()? View.VISIBLE:View.GONE);
                        listener.OnItemClickListener(position, t);
                    }
                }
            });

        }
    }





    @Override
    public int getItemCount() {
        return listLugarTrabajo.size();
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.listener=itemClickListener;
    }

    @Override
    public LugarTrabajo getItem(int position) {
        return listLugarTrabajo.get(position);
    }

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        public TextView tvAutocompleteTitle;
        public ImageView imageMaker;
        public ImageView imgCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAutocompleteTitle =(TextView)itemView.findViewById(R.id.tvQueryTitle);
            imageMaker=(ImageView) itemView.findViewById(R.id.imageMaker);
            imgCheck=(ImageView)itemView.findViewById(R.id.imgCheck);
        }
    }
}
