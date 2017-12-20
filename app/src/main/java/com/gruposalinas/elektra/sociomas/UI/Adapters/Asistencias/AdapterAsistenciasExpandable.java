package com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.IAdapterBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.MultiTypeExpandableRecyclerViewAdapter;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.models.ExpandableGroup;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;
import com.sociomas.core.WebService.Model.Response.Asistencia.ExpandableGroupAsistencia;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
/**
 * Created by oemy9 on 08/09/2017.
 */
public class AdapterAsistenciasExpandable extends MultiTypeExpandableRecyclerViewAdapter<ViewHolderAsistenciaExpandable, ChildViewHolder>
implements IAdapterBase{

    private static final int CHILD_ASISTENCIA_HEADER = 3;
    private static final int CHILD_ASISTENCIA = 4;
    private static final int DIA_HOY=10;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<? extends ExpandableGroup> listItems;
    private RecyclerItemClickListener listener;

    public AdapterAsistenciasExpandable(List<? extends ExpandableGroup> groups) {
        super(groups);
        Collections.reverse(groups);
    }


    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.listener = itemClickListener;

    }
    public void setContext(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolderAsistenciaExpandable onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_header_asistencia_descripcion, parent, false);
        return new ViewHolderAsistenciaExpandable(v);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CHILD_ASISTENCIA_HEADER: {
                View v = layoutInflater.inflate(R.layout.item_header_asistencia, parent, false);
                return new ViewHolderHeaderChildAsistencia(v);
            }
            case CHILD_ASISTENCIA: {
                View v = layoutInflater.inflate(R.layout.item_asistencia, parent, false);
                return new ViewHolderAsistencia(v);
            }
            default:
                View v = layoutInflater.inflate(R.layout.item_asistencia, parent, false);
                return new ViewHolderAsistencia(v);
        }

    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holderChild, final int flatPosition, ExpandableGroup group, int childIndex) {

        int viewType = getItemViewType(flatPosition);
        switch (viewType) {
            case CHILD_ASISTENCIA_HEADER: {
                ((ViewHolderHeaderChildAsistencia) holderChild).tvFechaAsistencia.setAllCaps(true);
                ((ViewHolderHeaderChildAsistencia) holderChild).tvEntradaAsistencia.setAllCaps(true);
                ((ViewHolderHeaderChildAsistencia) holderChild).tvSalidaAsistencia.setAllCaps(true);
            }
            break;
            case CHILD_ASISTENCIA: {
                ViewHolderAsistencia holder = (ViewHolderAsistencia) holderChild;
                AsistenciaContador asistenciaContador = new AsistenciaContador();
                ArrayList<ResultadoAsistencia> items = (ArrayList<ResultadoAsistencia>) group.getItems();
                final ResultadoAsistencia item = items.get(childIndex);
                boolean isFalta=item.getComentario().equalsIgnoreCase("falta");


                holder.tvFecha.setText(item.getFechaReporte());
                holder.tvEntrada.setText(!isFalta?item.getHoraEntrada():"Falta");
                holder.tvEntrada.setTextColor(isFalta?Color.RED: ContextCompat.getColor(context,R.color.gris_textos));
                holder.tvSalida.setText(!isFalta?item.getHoraSalida():"");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener!=null) {
                            listener.OnItemClickListener(flatPosition, item);
                        }
                    }
                });
                try {
                    Picasso.with(this.context).load(asistenciaContador.getHashIconos()
                            .get(item.getImagen().toLowerCase(Locale.ENGLISH)))
                            .into(holder.imageStatusAsistencia);
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
            break;
        }
    }



    @Override
    public void onBindGroupViewHolder(ViewHolderAsistenciaExpandable holder, int flatPosition, ExpandableGroup group) {
        ArrayList<ResultadoAsistencia> listAsistencias = (ArrayList<ResultadoAsistencia>) group.getItems();
        AsistenciaContador contador = getContador(listAsistencias);
        holder.tvFalta.setText(String.format("(%s)", contador.getNoAsistio()));
        holder.tvAsistenciaCorrecta.setText(String.format("(%s)", contador.getAsistio()));
        holder.tvEntradaDespues.setText(String.format("(%s)", contador.getDespuesHrLimite()));
        holder.tvSalidaAntes.setText(String.format("(%s)", contador.getSalidaAntes()));
        holder.tvRetardo.setText(String.format("(%s)",contador.getRetardo()));
        holder.tvNoTermina.setText(String.format("%s",contador.getNoTermina()));
        holder.tvMes.setText(Utils.getMesNombre(Integer.valueOf(group.getTitle())));

        if (!isGroupExpanded(flatPosition)) {
            holder.imageArrow.setImageResource(R.drawable.ic_arrow_down);
        } else {
            holder.imageArrow.setImageResource(R.drawable.ic_arrow_up);
        }
    }

    public AsistenciaContador getContador(ArrayList<ResultadoAsistencia> listItems) {
        AsistenciaContador asistenciaContador = new AsistenciaContador();
        for (ResultadoAsistencia item : listItems) {
            if (!item.isHeader() && (item.getImagen()!=null))
                asistenciaContador.countStatus(item.getImagen().toLowerCase());
        }
        return asistenciaContador;
    }

    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        if (((ExpandableGroupAsistencia) group).getItems().get(childIndex).isHeader()) {
            return CHILD_ASISTENCIA_HEADER;
        } else {
            return CHILD_ASISTENCIA;
        }
    }
    @Override
    public boolean isChild(int viewType) {
        return viewType == CHILD_ASISTENCIA_HEADER || viewType == CHILD_ASISTENCIA;
    }
}
