package com.sociomas.aventones.UI.Adapters;

import com.sociomas.core.Listeners.RecyclerItemClickListener;

/**
 * Created by oemy9 on 05/09/2017.
 */

public interface IAdapterBase  {
    void setItemClickListener(RecyclerItemClickListener itemClickListener);
    Object getItem(int position);
}
