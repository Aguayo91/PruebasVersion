package com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.UI.Presenters.DialogMessageNominaPresenter;
import com.gruposalinas.elektra.sociomas.UI.Presenters.impl.DialogMessageNominaPresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDialogMessageNomina#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDialogMessageNomina extends DialogFragment implements BaseView {
    public static final String TAG = FragmentDialogMessageNomina.class.getSimpleName();
    private View v;
    private DialogMessageNominaPresenter presenter;
    private ImageView ivImagenMensaje;
//    private Button btnContinue;
    int option = 0;
    String urlBanner1 = "http://sociomas.com.mx/staging/nomina/banner1.jpg";
    String urlBanner2 = "http://sociomas.com.mx/staging/nomina/banner2.jpg";
    String urlLoad = "";

    private MyDismissListener onDismissListener;

    public void setOnDismissListener(MyDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    private long timeMiliseconds = (1000 * 3) ;

    public FragmentDialogMessageNomina() {
        // Required empty public constructor
    }


    public static FragmentDialogMessageNomina newInstance(Bundle args) {
        FragmentDialogMessageNomina fragment = new FragmentDialogMessageNomina();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
//        if (option == 1) {
            setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragment_dialog_message_nomina, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void initView() {
        ivImagenMensaje = (ImageView) v.findViewById(R.id.ivImagenMensaje);
//        btnContinue = (Button) v.findViewById(R.id.btnContinue);
        setData();
    }

    private void setData() {
        if (getArguments() != null) {
            if (getArguments().containsKey(ViewEvent.RESOURCE_ID)) {
//                int idDrawable = getArguments().getInt(ViewEvent.RESOURCE_ID);
                option = getArguments().getInt(ViewEvent.ENTRY);

                switch (option) {
                    case 1: {
                        urlLoad = urlBanner1;
                    }
                    break;
                    case 2: {
                        urlLoad = urlBanner2;
                    }
                    break;
                }
                Picasso.with(getActivityInstance()).load(urlLoad).fit().into(ivImagenMensaje);

            }

//            TimerTask timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    dismiss();
//                    if (option == 2) {
//                        ((NominaActivity) getActivityInstance()).finish();
//                    }
//
//                }
//            };
//            Timer timer = new Timer();
//            timer.schedule(timerTask, timeMiliseconds);

            Observable.timer(3, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Long value) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    dismiss();
                    if (option == 2) {
//                        ((NominaActivity) getActivityInstance()).finish();
                    }
//                    onDismissListener.onDismiss();
                }
            });
        }
    }

    @Override
    public void setListeners() {
//        btnContinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                if (option == 2) {
//                    ((NominaActivity) getActivityInstance()).finish();
//                }
//            }
//        });

    }

    @Override
    public void onStart() {
        super.onStart();
//        Dialog d = getDialog();
//        if (d!=null){
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            d.getWindow().setLayout(width, height);
//
//        }
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        Window window = getDialog().getWindow();
//        window.setLayout(window.getWindowManager().getDefaultDisplay().getWidth() - 20,window.getWindowManager().getDefaultDisplay().getHeight() - 50);
    }

    @Override
    public void setPresenter() {
        presenter = new DialogMessageNominaPresenterImpl();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        switch (event.getEventType()) {
        }
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }

    public interface MyDismissListener{
        void onDismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        onDismissListener.onDismiss();
    }
}
