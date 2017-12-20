package com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 31/05/2017.
 */

public class ViewHolderTimeLine extends RecyclerView.ViewHolder {

    public TextView tvDireccion;
    public TimelineView mTimelineView;
    public TextView tvDate;
    public ImageView imgActividad;
    public ImageView imgType;
    public ImageView imgBattery;
    public TextView  tvVelocidad;
    public TextView tvBattery;
    public ProgressBar progressUbicacion;
    public CardView cardViewTime;


    public ViewHolderTimeLine(View itemView) {
        super(itemView);
        this.tvDate=(TextView)itemView.findViewById(R.id.tvDate);
        this.tvDireccion=(TextView)itemView.findViewById(R.id.tvDireccion);
        this.mTimelineView=(TimelineView)itemView.findViewById(R.id.time_marker);
        this.imgActividad=(ImageView)itemView.findViewById(R.id.imgActividad);
        this.imgType=(ImageView)itemView.findViewById(R.id.img_type);
        this.tvVelocidad=(TextView)itemView.findViewById(R.id.tv_velocidad);
        this.imgBattery=(ImageView)itemView.findViewById(R.id.img_battery);
        this.tvBattery=(TextView)itemView.findViewById(R.id.tvBattery);
        this.progressUbicacion=(ProgressBar)itemView.findViewById(R.id.progressUbicacion);
        this.cardViewTime=(CardView)itemView.findViewById(R.id.cardViewTime);
    }
}
