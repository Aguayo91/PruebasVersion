package com.gruposalinas.elektra.sociomas.UI.Adapters.Nomina;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.TextViews.TextViewMoney;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Response.Nomina.ListaRecibosNominaResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaDetalleCabecera;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNominaDetalleResponse;

import java.util.List;

import eu.davidea.flipview.FlipView;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 07/09/17.
 */
public class RecyclerAdapterRecibosNomina extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater li;
    private final List<ReciboNomina> list;
    private final View.OnClickListener listener;
    private LongClickListener longClickListener;
    public boolean flipAllItems;

    public RecyclerAdapterRecibosNomina(Context context, List<ReciboNomina> list, View.OnClickListener listener) {
        this.context = context;
        li = LayoutInflater.from(this.context);
        this.list = list;
        this.listener = listener;
    }

    public void setListenerLongClick(LongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface LongClickListener {
        void setRecibo(ReciboNomina recibo);

        void removeRecibo(ReciboNomina recibo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FlipView tvLettersCompany2;
        TextView tvTextoMonto;
        RelativeLayout rlParentNominaReciboItem;
        LinearLayout llRow1, llSubitems;
        TextView tvConcepto, tvDescripcion, tvNameDesgloce;
        TextViewMoney tvMonto;

        public MyViewHolder(View view) {
            super(view);
            rlParentNominaReciboItem = (RelativeLayout) view.findViewById(R.id.rlParentNominaReciboItem);
            llRow1 = (LinearLayout) view.findViewById(R.id.llRow1);
            tvConcepto = (TextView) view.findViewById(R.id.tvConcepto);
            tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
            tvTextoMonto = (TextView) view.findViewById(R.id.tvTextoMonto);
            tvMonto = (TextViewMoney) view.findViewById(R.id.tvMonto);
            tvLettersCompany2 = (FlipView) view.findViewById(R.id.tvLettersCompany2);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recibos_nomina, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder mHolder = (MyViewHolder) holder;
        if (list != null) {
            if (list.get(position) != null) {

                if (list.get(position) != null) {
                    ReciboNomina reciboNomina = list.get(position);
                    mHolder.rlParentNominaReciboItem.setTag(reciboNomina);
                    mHolder.tvLettersCompany2.setTag(reciboNomina);
                    mHolder.rlParentNominaReciboItem.setOnClickListener(listener);
                    mHolder.tvConcepto.setText(reciboNomina.getIdEmpresa());
                    mHolder.tvDescripcion.setText(reciboNomina.getConcepto());
                    mHolder.tvMonto.setAmount(reciboNomina.getImporte());
                    mHolder.tvLettersCompany2.setFrontText(Utils.formatFirstLetters(reciboNomina.getIdEmpresa()));
                    mHolder.rlParentNominaReciboItem.setLongClickable(true);
                    mHolder.tvLettersCompany2.flip(flipAllItems);
                    if (flipAllItems) {
                        mHolder.rlParentNominaReciboItem.setBackgroundColor(context.getResources().getColor(R.color.gris_claro));
                    } else {
                        mHolder.rlParentNominaReciboItem.setBackgroundColor(context.getResources().getColor(R.color.blanco));
                    }
                    mHolder.tvLettersCompany2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ReciboNomina recibo = (ReciboNomina) v.getTag();
                            if (mHolder.tvLettersCompany2.isFlipped()) {
                                mHolder.tvLettersCompany2.flip(false);
                                longClickListener.removeRecibo(recibo);
                                mHolder.rlParentNominaReciboItem.setBackgroundColor(context.getResources().getColor(R.color.blanco));
                            } else {
                                longClickListener.setRecibo(recibo);
                                mHolder.tvLettersCompany2.flip(true);
                                mHolder.rlParentNominaReciboItem.setBackgroundColor(context.getResources().getColor(R.color.gris_claro));
                            }
                        }
                    });
                }
            }
        }
    }

    public void checkedAllItems(boolean checked) {
        flipAllItems = checked;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
