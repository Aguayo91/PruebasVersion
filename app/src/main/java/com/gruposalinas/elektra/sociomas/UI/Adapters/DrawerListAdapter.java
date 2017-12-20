package com.gruposalinas.elektra.sociomas.UI.Adapters;

/**
 * Created by oemy9 on 27/09/2017.
 */


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.UI.Controls.Model.DrawerItem;
import com.gruposalinas.elektra.sociomas.R;

import java.util.List;
/**
 * Created by oemy9 on 27/07/2017.
 */

public class DrawerListAdapter extends ArrayAdapter<DrawerItem> {

    public DrawerListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DrawerItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater =LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_drawer, null);
        }
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        DrawerItem item = getItem(position);
        icon.setImageResource(item.getIcon());
        name.setText(item.getDescription());
        return convertView;
    }

}
