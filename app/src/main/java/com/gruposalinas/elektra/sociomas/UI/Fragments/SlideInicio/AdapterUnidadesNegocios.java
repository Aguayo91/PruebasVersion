package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.UnidadNegocioSeleccionPresenter;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadNegocioResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by GiioToledo on 14/11/17.
 */

class AdapterUnidadesNegocios extends BaseAdapter implements Filterable {
    private final Context context;
    private final LayoutInflater li;
    private final ItemFilter itemFilter;
    private final UnidadNegocioSeleccionPresenter presenter;
    private List<UnidadNegocioResponse> lastListCategorias;
    List<UnidadNegocioResponse> listUnidadesNegocio = new ArrayList<>();

    public AdapterUnidadesNegocios(Context context, List<UnidadNegocioResponse> unidadNegocios, UnidadNegocioSeleccionPresenter presenter) {
        this.context = context;
        this.li = LayoutInflater.from(this.context);
        listUnidadesNegocio = unidadNegocios;
        lastListCategorias = listUnidadesNegocio;
        this.itemFilter = new ItemFilter();
        this.presenter = presenter;
    }



    public void reset() {
        this.listUnidadesNegocio = lastListCategorias;
        notifyDataSetChanged();
    }

    public void fillUnidadesNegocio(List<UnidadNegocioResponse> responseList){
        this.listUnidadesNegocio = responseList;
        this.lastListCategorias = listUnidadesNegocio;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listUnidadesNegocio.size();
    }

    @Override
    public UnidadNegocioResponse getItem(int position) {
        return listUnidadesNegocio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = li.inflate(R.layout.item_unidades_negocio, parent, false);
            holder.llParent = (LinearLayout) convertView.findViewById(R.id.llParent) ;
            holder.ivUnidadNegocio = (ImageView) convertView.findViewById(R.id.ivUnidadNegocio);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        UnidadNegocioResponse obj = listUnidadesNegocio.get(position);
        byte[] decodedString = Base64.decode(obj.getLogoBase64().getBytes(), Base64.DEFAULT);
        Glide.with(context)
                .asBitmap()
                .transition(BitmapTransitionOptions.withCrossFade())
                .load(decodedString)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (position == listUnidadesNegocio.size()) {
                            presenter.sendHideProgressEvent();
                        }
                        return false;
                    }
                })
                .into(holder.ivUnidadNegocio);
        holder.llParent.setTag(obj);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private static class Holder {
        LinearLayout llParent;
        ImageView ivUnidadNegocio;
    }

    class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            final ArrayList<UnidadNegocioResponse> nList = new ArrayList<>();
            String query = charSequence.toString().toLowerCase(Locale.ENGLISH);
            for (UnidadNegocioResponse item : listUnidadesNegocio) {
                String categoria = item.getDescripcion().toLowerCase(Locale.ENGLISH);
                if (categoria != null && (categoria.contains(query) || categoria.startsWith(query))) {
                    nList.add(item);
                }
            }
            results.values = nList.isEmpty() ? listUnidadesNegocio : nList;
            results.count = nList.size() == 0 ? listUnidadesNegocio.size() : nList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<UnidadNegocioResponse> listResults = (ArrayList<UnidadNegocioResponse>) filterResults.values;
            if (!listResults.isEmpty()) {
                listUnidadesNegocio = listResults;
                notifyDataSetChanged();
            }
        }
    }
}
