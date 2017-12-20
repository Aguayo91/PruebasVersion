package com.gruposalinas.elektra.sociomas.UI.Adapters.Log;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.DataBaseModel.PanicoLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oemy9 on 04/04/2017.
 */

public class AdapterLogPanico extends BaseAdapter  {
    public static final String TAG="ADAPTER_LOG";
    static  class ViewHolder{
         public  TextView tvFechaEnvio;
         public  TextView tvEnviado;
         public ImageView imgReproducir;
    }
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<PanicoLog> panicoLogs;
    private HashMap<Integer,MediaPlayer>playerHashMap=new HashMap<>();


    public AdapterLogPanico(Context context, ArrayList<PanicoLog>panicoLogs){
        this.context=context;
        this.panicoLogs =panicoLogs;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.panicoLogs.size();
    }

    @Override
    public PanicoLog getItem(int position) {
        return this.panicoLogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null) {
            holder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_log_panico, parent, false);
            holder.tvFechaEnvio=(TextView)convertView.findViewById(R.id.tvFechaEnvio);
            holder.tvEnviado=(TextView)convertView.findViewById(R.id.tvEnviado);
            holder.imgReproducir=(ImageView) convertView.findViewById(R.id.imgReproducir);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvFechaEnvio.setText(getItem(i).getFecha()+" "+ getItem(i).getHora());
        holder.tvEnviado.setTextColor(getItem(i).isSuccess()? Color.BLUE:Color.RED);
        holder.tvEnviado.setText(getItem(i).isSuccess()?"ENVIADO":"NO ENVIADO");
        holder.imgReproducir.setImageResource(getItem(i).isPlaying()? R.drawable.ic_pause_black: R.drawable.ic_play_arrow);
        holder.imgReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    reproducirAudio(i, holder);
            }
        });
        return convertView;

    }

    private void  reproducirAudio(final int position, final ViewHolder holder){
        try {
            int count=0;
            for(Map.Entry<Integer,MediaPlayer> item: playerHashMap.entrySet()){
                if(item.getValue()!=null &&(item.getValue().isPlaying()) && position!=count){
                    item.getValue().stop();
                    item.getValue().release();
                    item.setValue(new MediaPlayer());
                    getItem(count).setPlaying(false);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Stop audio",Toast.LENGTH_LONG).show();
                    count++;
                }
            }
            if(getItem(position).getPath()!=null && (!getItem(position).getPath().isEmpty())) {
                if(!playerHashMap.containsKey(position)) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(getItem(position).getPath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    playerHashMap.put(position, mediaPlayer);
                    getItem(position).setPlaying(true);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Reproduciendo audio",Toast.LENGTH_LONG).show();
                }
                else if(playerHashMap.get(position).isPlaying()){
                    playerHashMap.get(position).stop();
                    playerHashMap.get(position).release();
                    playerHashMap.put(position,new MediaPlayer());
                    getItem(position).setPlaying(false);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Stop audio",Toast.LENGTH_LONG).show();
                }
                else if(playerHashMap.get(position)!=null && (!playerHashMap.get(position).isPlaying())){
                    playerHashMap.get(position).setDataSource(getItem(position).getPath());
                    playerHashMap.get(position).prepare();
                    playerHashMap.get(position).start();
                    getItem(position).setPlaying(true);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Reproduciendo audio",Toast.LENGTH_LONG).show();
                }
                playerHashMap.get(position).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        holder.imgReproducir.setImageResource(R.drawable.ic_play_arrow);
                        playerHashMap.get(position).stop();
                        playerHashMap.get(position).release();
                        playerHashMap.put(position,new MediaPlayer());
                        getItem(position).setPlaying(false);
                        notifyDataSetChanged();
                        Toast.makeText(context,"Terminando reproducción",Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
