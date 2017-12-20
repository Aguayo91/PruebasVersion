package com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.FullscreenImageActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.Utils.Blur;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.MultiTypeExpandableRecyclerViewAdapter;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.models.ExpandableGroup;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ExpandableGroupIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by oemy9 on 23/10/2017.
 */


public class AdapterIncidenciasExpandible extends MultiTypeExpandableRecyclerViewAdapter<ViewHolderIncidenciaHeader,ChildViewHolder> {

    private Context context;
    private SnackBarBuilder snackBarBuilder;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat dateFormatFrom;
    private SimpleDateFormat dateFormatTo;
    private Date date;
    private static final int CHILD_INCIDENCIA = 3;
    private static final int CHILD_INCIDENCIA_PLANTILLA = 4;
    public  interface ListenerActions{
        void rechazarAprobar(ListadoIncidencias selectedItem,boolean autorizar);
        void validarJustificacion(ListadoIncidencias selectedItem);
        void onSelectedItemdMios(ListadoIncidencias selectedItem);
    }

    private  ListenerActions listenerActions;

    public void setListenerActions(ListenerActions listenerActions) {
        this.listenerActions = listenerActions;
    }

    public AdapterIncidenciasExpandible(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
        this.snackBarBuilder = new SnackBarBuilder((Activity) context);
        this.layoutInflater = LayoutInflater.from(this.context);
        dateFormatFrom=new SimpleDateFormat(Constants.DATE_FORMAT_INCIDENCIAS_FROM);
        dateFormatTo=new SimpleDateFormat(Constants.DATE_FORMAT_INCIDENCIAS_TO);
    }


    public AdapterIncidenciasExpandible(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ViewHolderIncidenciaHeader onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderIncidenciaHeader(layoutInflater.inflate(R.layout.item_lugar_header, parent, false));
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v;
        ChildViewHolder childViewHolder = null;
        switch (viewType) {
            case CHILD_INCIDENCIA:
                v = layoutInflater.inflate(R.layout.item_incidencia_v2, parent, false);
                childViewHolder = new ViewHolderIncidenciaChild(v);
                break;
            case CHILD_INCIDENCIA_PLANTILLA:
                v = layoutInflater.inflate(R.layout.item_incidencia_plantilla, parent, false);
                childViewHolder = new ViewHolderChildIncidenciaPlantilla(v);
                break;
        }
        return childViewHolder;
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        int viewType=getChildViewType(flatPosition,group,childIndex);
        final ListadoIncidencias selectedItem = ((ExpandableGroupIncidencias) group).getItems().get(childIndex);

        switch (viewType){
            case CHILD_INCIDENCIA: {
                        ViewHolderIncidenciaChild holderChild = (ViewHolderIncidenciaChild) holder;
                        try {
                            Picasso.with(this.context).load(AdapterUtils.getResourceFileByTipoJustificacion(selectedItem.getEstatusJustificacion())).resize(60, 60).into(holderChild.ImgJustificacion);
                            holderChild.tvTipoJustificacion.setText(selectedItem.getIncidencia());
                            holderChild.tvFechaJustificacion.setText(toStringDate(selectedItem.getFechaOcurrencia()));
                            holderChild.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    listenerActions.onSelectedItemdMios(selectedItem);
                                }
                            });
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                 }
                break;
            case CHILD_INCIDENCIA_PLANTILLA: {

                final ViewHolderChildIncidenciaPlantilla holderChild = (ViewHolderChildIncidenciaPlantilla) holder;
                //TIPO DE INCIDENCIA
                final EnumIncidencia enumIncidencia= EnumIncidencia.getFromSting(selectedItem.getEstatusJustificacion());
                /*¿TIENE ARCHIVO ADJUNTO?*/
                //SI TIENE UN ARCHIVO ADJUNTO PUEDO MOSTRAR EL PREVIEW DE LA IMAGEN
                holderChild.layoutImgIncidencia.setVisibility(selectedItem.getAdjunto()==null || (selectedItem.getAdjunto().isEmpty())? View.GONE:View.VISIBLE);
                //CARGAR IMAGENES DE EMPLEADO, TIPO DE JUSTIFICACIÓN Y EFECTO DE BLUR
                Picasso.with(context).load(R.drawable.ic_user).into(holderChild.ImgEmpleado);
                Picasso.with(context).load(AdapterUtils.getResourceFileByTipoJustificacion(selectedItem.getEstatusJustificacion())).resize(60,60).into(holderChild.ImgJustificacion);
                Picasso.with(context).load(R.drawable.noimage).transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        Bitmap blurred = Blur.fastblur(context, source, 10);
                        source.recycle();
                        return blurred;
                    }
                    @Override
                    public String key() {
                        return "blur";
                    }
                }).resize(200,200).centerCrop().into(holderChild.ImgIncidencia);
                if(selectedItem.isDescargada() && selectedItem.getSelectedBitmap()!=null){
                    holderChild.ImgIncidencia.setImageBitmap(selectedItem.getSelectedBitmap());
                }
                holderChild.txtComentarioIncidencia.setText("");
                holderChild.tvStatus.setText(selectedItem.getEstatusJustificacion());
                holderChild.tvName.setText(selectedItem.getNombre());
                holderChild.tvIncidencia.setText(selectedItem.getIncidencia());
                holderChild.layoutComentarios.setVisibility(selectedItem.getIdJustificacion()==0? View.GONE:View.VISIBLE);
                holderChild.layoutAutorizar.setVisibility(selectedItem.getIdJustificacion()==0? View.VISIBLE:View.GONE);
                holderChild.tvComentario.setVisibility(selectedItem.getComentarios()==null || (selectedItem.getComentarios().isEmpty())? View.GONE:View.VISIBLE);
                holderChild.tvComentario.setText(selectedItem.getComentarios());
                if(holderChild.layoutComentarios.getVisibility()==View.VISIBLE){
                    Animation show= AnimationUtils.loadAnimation(context, R.anim.view_show);
                    holderChild.layoutComentarios.startAnimation(show);
                }
                try {
                    holderChild.tvDate.setText(AdapterUtils.getRelativeDate(
                            this.context, selectedItem.getFechaOcurrencia(),dateFormatFrom,
                            date
                    ));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                holderChild.fabDescarga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selectedItem.isBotonClick()) {
                            Toast.makeText(context, context.getString(R.string.intentado_descargar), Toast.LENGTH_LONG).show();
                            return;
                        }
                        selectedItem.setBotonClick(!selectedItem.isBotonClick());
                        holderChild.fabDescarga.showProgress(selectedItem.isBotonClick());
                        final Target target=new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                selectedItem.setDescargada(true);
                                holderChild.ImgIncidencia.setImageBitmap(bitmap);
                                selectedItem.setSelectedBitmap(bitmap);
                                holderChild.fabDescarga.setVisibility(View.GONE);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                selectedItem.setBotonClick(!selectedItem.isBotonClick());
                                holderChild.fabDescarga.showProgress(selectedItem.isBotonClick());
                                Toast.makeText(context,context.getString(R.string.no_imagen_vinculada),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        };
                        Picasso.with(context).load(selectedItem.getAdjunto()).resize(300,300).into(target);
                        holderChild.ImgIncidencia.setTag(target);
                    }
                });
                holderChild.fabDescarga.setVisibility(selectedItem.isDescargada()? View.GONE:View.VISIBLE);
                holderChild.fabDescarga.showProgress(selectedItem.isBotonClick());
                holderChild.ImgIncidencia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(holderChild.fabDescarga.getVisibility()==View.GONE){;
                            Intent intent=new Intent(context, FullscreenImageActivity.class);
                            intent.putExtra(Constants.BITMAP_SELECTED_IMAGE, selectedItem.getSelectedBitmap());
                            context.startActivity(intent);
                        }
                    }
                });
                holderChild.txtComentarioIncidencia.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int start,
                                                  int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence texto, int start,
                                              int count, int after) {
                        selectedItem.setComentarioRechazo(texto.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                holderChild.btnAutorizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listenerActions.rechazarAprobar(selectedItem,true);
                    }
                });
                holderChild.btnRechazar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listenerActions.rechazarAprobar(selectedItem,false);
                    }
                });

                holderChild.btnAutorizarDirecto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listenerActions.validarJustificacion(selectedItem);
                    }
                });

                }
                break;
        }
    }

    @Override
    public void onBindGroupViewHolder(ViewHolderIncidenciaHeader holder, int flatPosition, ExpandableGroup group) {
        holder.tvDescripcion.setText(group.getTitle());
        if (!isGroupExpanded(flatPosition)) {
            holder.imageArrow.setImageResource(R.drawable.ic_arrow_down);
        } else {
            holder.imageArrow.setImageResource(R.drawable.ic_arrow_up);
        }

    }

    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        if (group.getTitle().indexOf("equipo")!=-1) {
            return CHILD_INCIDENCIA_PLANTILLA;
        } else {
            return CHILD_INCIDENCIA;
        }
    }

    @Override
    public boolean isChild(int viewType) {
        return viewType == CHILD_INCIDENCIA_PLANTILLA || viewType == CHILD_INCIDENCIA;
    }


    public String toStringDate(String input) throws ParseException {
        date=dateFormatFrom.parse(input);
        return dateFormatTo.format(date);
    }

}
