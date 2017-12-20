package com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.IAdapterBase;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.MultiTypeExpandableRecyclerViewAdapter;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.models.ExpandableGroup;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Zonas.ExpandableGroupPosicionValida;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import com.squareup.picasso.Picasso;
import java.util.Collections;
import java.util.List;

/**
 * Created by oemy9 on 18/09/2017.
 */

public class AdapterLugarTrabajo  extends MultiTypeExpandableRecyclerViewAdapter<ViewHolderLugarHeader,ChildViewHolder>
implements IAdapterBase{
    private static final int CHILD_LUGAR_TRABAJO = 3;
    private static final int CHILD_LUGAR_TRABAJO_PLANTILLA= 4;
    private Context context;
    private LayoutInflater layoutInflater;
    private RecyclerItemClickListener listener;
    private SnackBarBuilder snackBarBuilder;

    public interface ListenerActions{
        void onAutorizarRechazar(int group, int childIndex, LugarTrabajo p, boolean autorizar);
    }
    private ListenerActions listenerActions;


    public void setListenerActions(ListenerActions listenerActions) {
        this.listenerActions = listenerActions;
    }

    public AdapterLugarTrabajo(Context context, List<? extends ExpandableGroup> groups){
        super(groups);
        this.context=context;
        this.snackBarBuilder=new SnackBarBuilder((Activity)context);
        this.layoutInflater=LayoutInflater.from(this.context);
        Collections.reverse(groups);
    }

    public AdapterLugarTrabajo(List<? extends ExpandableGroup> groups) {
        super(groups);
     }


    @Override
    public ViewHolderLugarHeader onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderLugarHeader(layoutInflater.inflate(R.layout.item_lugar_header,parent,false));
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v;
        ChildViewHolder childViewHolder = null;
        switch (viewType){
            case CHILD_LUGAR_TRABAJO:
                 v = layoutInflater.inflate(R.layout.item_lugar_trabajo, parent, false);
                 childViewHolder=new ViewHolderLugarChild(v);
            break;
            case CHILD_LUGAR_TRABAJO_PLANTILLA:
                     v = layoutInflater.inflate(R.layout.item_lugar_trabajo_plantilla, parent, false);
                     childViewHolder=new ViewHolderLugarChildPlantilla(v);
                break;
        }
        return childViewHolder;
    }

    @Override
    public void onBindChildViewHolder(final ChildViewHolder holder, final int flatPosition, ExpandableGroup group, final int childIndex) {

        int viewType=getChildViewType(flatPosition,group,childIndex);

        final LugarTrabajo selectedItem = ((ExpandableGroupPosicionValida) group).getItems().get(childIndex);
        ColorGenerator generator = ColorGenerator.MATERIAL;

        switch (viewType){
            case CHILD_LUGAR_TRABAJO: {
                ViewHolderLugarChild holderChild = (ViewHolderLugarChild) holder;
                int selectedColor = ContextCompat.getColor(context,R.color.colorPrimary);
                String title = selectedItem.getVaNombrePos() != null ? selectedItem.getVaNombrePos() : selectedItem.getVaDescripcionZnPosic();
                TextDrawable drawable = TextDrawable.builder().buildRound(title.substring(0, 1), selectedColor);
                holderChild.imageViewCircleNombre.setImageDrawable(drawable);
                holderChild.tvLugarTrabajo.setText(title);
                holderChild.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.OnItemClickListener(childIndex, selectedItem);
                        }
                    }
                });
            }
            break;
            case CHILD_LUGAR_TRABAJO_PLANTILLA: {
                final ViewHolderLugarChildPlantilla holderChild=(ViewHolderLugarChildPlantilla) holder;
                holderChild.tvNombre.setText(selectedItem.getNombrCompleto());
                String urlMapa= Utils.getMapaUbicacion(selectedItem.getDecLatitud(),selectedItem.getDecLongitud());
                int selectedColor = ContextCompat.getColor(context,R.color.colorPrimary);
                String title = selectedItem.getVaNombrePos() != null ? selectedItem.getVaNombrePos() : selectedItem.getVaDescripcionZnPosic();
                TextDrawable drawable = TextDrawable.builder().buildRound(title.substring(0, 1), selectedColor);
                holderChild.imageCircleNombre.setImageDrawable(drawable);
                holderChild.tvLugarTrabajo.setText(title);
                holderChild.txtComentario.setText("");
                holderChild.btnAutorizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listenerActions!=null){
                            if(holderChild.txtComentario.getText().toString().isEmpty()){
                                snackBarBuilder.showError(context.getString(R.string.comentario_vacio));
                            }
                            else{
                                listenerActions.onAutorizarRechazar(2,childIndex,selectedItem,true);
                            }
                        }
                    }
                });
                holderChild.btnRechazar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listenerActions!=null){
                            if(holderChild.txtComentario.getText().toString().isEmpty()){
                                snackBarBuilder.showError(context.getString(R.string.comentario_vacio));
                            }
                            else {
                                listenerActions.onAutorizarRechazar(2, childIndex, selectedItem, false);
                            }
                        }
                    }
                });
                Picasso.with(context).load(urlMapa).into(holderChild.imgMapa);

            }
            break;
        }



    }
    @Override
    public void onBindGroupViewHolder(ViewHolderLugarHeader holder, int flatPosition, ExpandableGroup group) {
        holder.tvDescripcionLugar.setText(getDetalleDescripcion(group.getTitle(),group.getItemCount()));
        if (!isGroupExpanded(flatPosition)) {
            holder.imageArrow.setImageResource(R.mipmap.ic_arrorw_white);
        } else {
            holder.imageArrow.setImageResource(R.mipmap.ic_arrow_white_up);
        }
    }
    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.listener=itemClickListener;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        if (group.getTitle().equalsIgnoreCase("t")) {
            return CHILD_LUGAR_TRABAJO_PLANTILLA;
        } else {
            return CHILD_LUGAR_TRABAJO;
        }
    }

    @Override
    public boolean isChild(int viewType) {
        return viewType == CHILD_LUGAR_TRABAJO_PLANTILLA || viewType == CHILD_LUGAR_TRABAJO;
    }
    public String getDetalleDescripcion(String title, int total){
        String descripcion="";
        switch (title.toLowerCase()){
            case "p":
                descripcion=String.format("Lugares pendientes de autorizar (%s)",total);
                break;
            case "c":
                descripcion=String.format("Lugares de trabajo (%s)",total);
                break;
            case "t":
                descripcion=String.format("Lugares de mi equipo pendientes (%s)",total);
                break;
        }
        return descripcion;
    }


}
