package com.example.lenovo7657.materialdesign;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo7657 on 29/10/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ObjPersonaViewHolder>{

    public Context context;
    private ArrayList<ObjetoPersona> items;
    private static final String DEBUG_TAG = "SampleMaterialAdapter";

    public RecyclerViewAdapter(Context context, ArrayList<ObjetoPersona>cardList){

        this.context=context;
        this.items=cardList;
    }

    @Override
    public void onBindViewHolder(ObjPersonaViewHolder objetoPersonaViewholder, int i){
        objetoPersonaViewholder.tvNombrePersona.setText(items.get(i).name);
        objetoPersonaViewholder.tvPersonAge.setText(items.get(i).age);
        objetoPersonaViewholder.imgPersonPhoto.setImageResource(items.get(i).photoId);

    }

    @Override
    public void onViewDetachedFromWindow(ObjPersonaViewHolder viewHolder){
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.itemView.clearAnimation();
    }
    @Override
    public void onViewAttachedToWindow(ObjPersonaViewHolder viewHolder){
        super.onViewDetachedFromWindow(viewHolder);
        animateCircularReveal(viewHolder.itemView);
    }

    public void animateCircularReveal(View view){
        int centerX = 0;
        int centerY = 0;
        int startRadius=0;
        int endRadius =Math.max(view.getWidth(),view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY,startRadius,endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    public void animateCircularDelete(final View view, final int list_position) {
        int centerX = view.getWidth();
        int centerY = view.getHeight();
        int startRadius = view.getWidth();
        int endRadius = 0;
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Log.d(DEBUG_TAG, "SampleMaterialAdapter onAnimationEnd for Edit adapter position " + list_position);
                Log.d(DEBUG_TAG, "SampleMaterialAdapter onAnimationEnd for Edit cardId " + getItemId(list_position));

                view.setVisibility(View.INVISIBLE);
                items.remove(list_position);
                notifyItemRemoved(list_position);
            }
        });
        animation.start();
    }

   /* public void addCard(String name, int color) {
        Card card = new Card();
        card.setName(name);
        card.setColorResource(color);
        card.setId(getItemCount());
        cardsList.add(card);
        ((SampleMaterialActivity) context).doSmoothScroll(getItemCount());
        notifyItemInserted(getItemCount());
    }*/

    /*public void updateCard(String name, int list_position) {
        items.get(list_position).setName(name);
        Log.d(DEBUG_TAG, "list_position is " + list_position);
        notifyItemChanged(list_position);
    }

    public void deleteCard(View view, int list_position) {
        animateCircularDelete(view, list_position);
    }*/

    @Override
    public int getItemCount() {

        if (items.isEmpty()) {
            return 0;
        } else {
            return items.size();
        }
    }

    @Override
    public ObjPersonaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler, viewGroup,false);
        ObjPersonaViewHolder objPerVH = new ObjPersonaViewHolder(v);
        return objPerVH;
    }

    public static class ObjPersonaViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView tvNombrePersona,tvPersonAge;
        ImageView imgPersonPhoto;

        ObjPersonaViewHolder(View itemView){
            super(itemView);
            cv=(CardView)itemView.findViewById(R.id.cv);
            tvNombrePersona=(TextView)itemView.findViewById(R.id.person_name);
            tvPersonAge=(TextView)itemView.findViewById(R.id.person_age);
            imgPersonPhoto=(ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    public RecyclerViewAdapter(ArrayList<ObjetoPersona> items){
        this.items=items;
    }


    //insertamos los datos ependiendo de la posicion del item


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
