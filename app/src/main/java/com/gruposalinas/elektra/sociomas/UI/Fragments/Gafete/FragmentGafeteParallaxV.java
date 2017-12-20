package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.CrearGafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.GafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentLocation;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Parallax.ParallaxGafetePresenter;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Parallax.ParallaxGafetePresenterImpl;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.StringUtils;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Controls.Layout.ParallaxLayerLayout;
import com.sociomas.core.UI.Controls.Layout.SensorTranslationUpdater;
import com.sociomas.core.Utils.Enums.EnumDensidad;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Gafete.ResponseGafeteImagen;
import com.squareup.picasso.Picasso;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by oemy9 on 31/10/2017.
 */

public class FragmentGafeteParallaxV extends FragmentLocation implements  ParallaxGafetePresenterImpl.ParallaxView {
    private static final String TAG = "FragmentGafeteParallaxV";
    private ParallaxLayerLayout parallax;
    private View rootView;
    private SensorTranslationUpdater translationUpdater;
    private View layoutPlaca;
    private TextView tvNombre,tvApellidos, tvSocio,tvPuesto;
    private ParallaxGafetePresenter presenter;
    private boolean hasResponse=false;
    private RelativeLayout layoutPlecaMedia;
    private ImageView logoBgGafete,imgPleca,imgAvatarCircle,imgLogoGpo,
            imgUnidadNegocio,imgPlecaDatos,imgPlecaDatosBaja,imgQr;
    private CircleImageView imgAvatar;
    private View viewQr,viewArrow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gafete_parallax_s, container, false);
        setPresenter();
        return rootView;
    }


    public void initParalax(SensorTranslationUpdater translationUpdater) {
        parallax.setTranslationUpdater(translationUpdater);
    }

    @Override
    public void onResume() {
        super.onResume();
        translationUpdater.registerSensorManager();
        initParalax(translationUpdater);
    }

    @Override
    public void onPause() {
        super.onPause();
        translationUpdater.unregisterSensorManager();
    }


    @Override
    public void initView() {
        parallax=(ParallaxLayerLayout)rootView.findViewById(R.id.parallax);
        tvApellidos=(TextView)rootView.findViewById(R.id.tvApellidos);
        tvNombre=(TextView)rootView.findViewById(R.id.tvNombre);
        layoutPlaca=rootView.findViewById(R.id.layoutPleca);
        translationUpdater=new SensorTranslationUpdater(getContext());
        logoBgGafete=(ImageView)rootView.findViewById(R.id.logoBgGafete);
        imgAvatar=(CircleImageView) rootView.findViewById(R.id.imgAvatar);
        imgPleca=(ImageView)rootView.findViewById(R.id.imgPleca);
        imgAvatarCircle=(ImageView)rootView.findViewById(R.id.imgAvatarCircle);
        imgLogoGpo=(ImageView)rootView.findViewById(R.id.imgLogoGpo);
        imgUnidadNegocio=(ImageView)rootView.findViewById(R.id.imgUnidadNegocio);
        imgPlecaDatos=(ImageView)rootView.findViewById(R.id.imgPlecaDatos);
        imgPlecaDatosBaja=(ImageView)rootView.findViewById(R.id.imgPlecaDatosBaja);
        imgQr=(ImageView)rootView.findViewById(R.id.imgQr);
        tvApellidos = (TextView) rootView.findViewById(R.id.tvApellidos);
        tvNombre = (TextView) rootView.findViewById(R.id.tvNombre);
        tvSocio=(TextView)rootView.findViewById(R.id.tvSocio);
        tvPuesto=(TextView)rootView.findViewById(R.id.tvPuesto);
        layoutPlaca = rootView.findViewById(R.id.layoutPleca);
        layoutPlecaMedia=(RelativeLayout)rootView.findViewById(R.id.layoutPlecaMedia);
        translationUpdater = new SensorTranslationUpdater(getContext());
        logoBgGafete = (ImageView) rootView.findViewById(R.id.logoBgGafete);
        viewQr=rootView.findViewById(R.id.viewQr);
        viewArrow=rootView.findViewById(R.id.viewArrow);
        initParalax(translationUpdater);
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        requestGafete();
    }

    private void requestGafete(){
        if (presenter != null && !hasResponse) {
            presenter.requestGafete(currentLatLng.latitude, currentLatLng.longitude);
        }
    }

    @Override
    public void setListeners() {


        viewArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null) {
                    getActivity().onBackPressed();
                }
            }
        });

        viewQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null){
                    GafeteActivity act=(GafeteActivity)getActivity();
                    act.changeNavigationTab(GafeteActivity.POSITION_QR);
                }
            }
        });
    }

    @Override
    public void setPresenter() {
        this.presenter = new ParallaxGafetePresenterImpl();
        this.presenter.register(this);
    }

    @Override
    public void presentEvent(ViewEvent event) {
        if(getActivity()!=null){
            BaseCoreCompactActivity act=(BaseCoreCompactActivity)getActivity();
            act.presentEvent(event);
        }
    }


    @Override
    public void showImagenesGafete(ResponseGafeteImagen g) {

        if (isAdded() && (getActivity() != null)) {

            hasResponse = true;
            EnumDensidad densidad = presenter.getCurrentDensidad();
            switch (densidad) {
                case xhdpi: {
                    tvNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    tvApellidos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    tvSocio.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    tvPuesto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    Utils.setMargins(tvNombre, 0, 10, 0, 0);
                }
                break;
                case xxhdpi: {
                    tvNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                    tvApellidos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                    tvSocio.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    tvPuesto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    Utils.setMargins(tvNombre, 0, 20, 0, 0);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    if (height >= 2000) {
                        ViewGroup.LayoutParams paramsT = layoutPlecaMedia.getLayoutParams();
                        paramsT.height = paramsT.height + 300;
                        layoutPlecaMedia.setLayoutParams(paramsT);
                    }
                }
                break;
                case hdpi: {

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;

                    if (height <= 1000) {
                        tvNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        tvApellidos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        tvSocio.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        tvPuesto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        ViewGroup.LayoutParams paramsT = layoutPlecaMedia.getLayoutParams();
                        paramsT.height = paramsT.height - 200;
                        if (getContext() != null) {
                            layoutPlecaMedia.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gris_claro));
                        }

                    } else {
                        tvNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                        tvApellidos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                        tvSocio.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        tvPuesto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    }
                }
                break;
            }

            StringUtils stringUtils = new StringUtils();
            List<String> nombreSeparado = stringUtils.getNombreSeparadoInternal(g.getNombreEmpleado());
            if (nombreSeparado != null && !nombreSeparado.isEmpty()) {
                if (nombreSeparado.size() > 1) {
                    tvNombre.setText(nombreSeparado.get(0));
                    tvApellidos.setText(nombreSeparado.get(1));
                } else {
                    tvNombre.setText(nombreSeparado.get(0));

                }
                tvSocio.setText(getString(R.string.socio).concat(Utils.getNumeroEmpleado(getContext())));
                tvPuesto.setText(g.getPuestoEmpleado());
                parallax.setBackground(new BitmapDrawable(getResources(), Utils.decodeBase64(g.getImgBackground())));
                parallax.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
                //AVATAR
                imgAvatar.setImageBitmap(Utils.decodeBase64(g.getImgFoto()));
                Bitmap bmptPerfil = Utils.decodeBase64(g.getImgFoto());
                if (bmptPerfil != null) {
                    imgAvatar.setImageBitmap(Utils.getCroppedBitmap(bmptPerfil));
                }
                imgAvatar.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));

                //CIRCULO
                Glide.with(getContext()).load(Utils.decodeBase64String(g.getImgFramePic()))
                        .transition(DrawableTransitionOptions.withCrossFade()).into(imgAvatarCircle);

                //FONDO QUE VA DE
                // TRAS DE LA IMAGEN DE PERFIL
                Glide.with(getContext()).load(Utils.decodeBase64String(g.getImgLogoBgGafete())).
                        transition(DrawableTransitionOptions.withCrossFade()).into(logoBgGafete);


                Glide.with(getContext()).asBitmap().load(Utils.decodeBase64String(g.getImgLogoNegocio()))
                        .transition(BitmapTransitionOptions.withCrossFade())
                        .into(imgUnidadNegocio);

                //PLECA GAFETE
                Glide.with(getContext()).load(Utils.decodeBase64String(g.getImgPlecaAlta())).
                        transition(DrawableTransitionOptions.withCrossFade()).into(imgPleca);

                //LOGO DE GPO SALINAS
                Glide.with(getContext()).asBitmap().load(Utils.decodeBase64String(g.getImgLogoGs())).
                        transition(BitmapTransitionOptions.withCrossFade()).into(imgLogoGpo);

                //LOGO UNIDAD DE NEGOCIO

                //USUARIO   ES DE GRUPO SALINAS
                if (Utils.usuarioGrupoSalinas(getContext())) {
                    //SE CALCULA EL ANCHO Y ALTO DE ACUERDO A LA DENSIDAD DE LA PANTALLA
                    int width = densidad == EnumDensidad.xxhdpi ? 250 : 170;
                    int height = densidad == EnumDensidad.xxhdpi ? 250 : 170;
                    int margin = densidad == EnumDensidad.xxhdpi ? 115 : 80;
                    imgLogoGpo.setVisibility(View.GONE); // SE OCULTA LA IMAGEN DE LA UNIDAD DE NEGOCIO
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                            (width, height);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL); // SE ALINEA AL CENTRO
                    params.setMargins(0, margin, 0, 0);
                    imgUnidadNegocio.setLayoutParams(params);
                }

                //PLECA DE DATOS MEDIA
                Glide.with(getContext()).load(Utils.decodeBase64String(g.getImgPlecaMedia())).
                        transition(DrawableTransitionOptions.withCrossFade()).into(imgPlecaDatos);

                //PLECA DE DATOS BAJA
                Glide.with(getContext()).load(Utils.decodeBase64String(g.getImgPlecaBaja())).
                        transition(DrawableTransitionOptions.withCrossFade()).into(imgPlecaDatosBaja);

                Glide.with(getContext()).load(Utils.decodeBase64String(g.getImgCodeQr())).
                        transition(DrawableTransitionOptions.withCrossFade()).into(imgQr);
            }
        }
    }

    @Override
    public void navigateCredencializacion() {
        if(getActivity()!=null) {
            getActivity().startActivity(new Intent(getActivity(), CrearGafeteActivity.class));
            getActivity().finish();
        }
    }
    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }
}
