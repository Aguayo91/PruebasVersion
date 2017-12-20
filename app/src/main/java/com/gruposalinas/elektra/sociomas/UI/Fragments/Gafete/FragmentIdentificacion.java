package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gruposalinas.elektra.sociomas.R;
/**
 * Created by oemy9 on 22/08/2017.
 */
public class FragmentIdentificacion extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_id_oficial, container, false);
        return rootView;
    }
}
