package com.sociomas.aventones.UI.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Inicio.AventonInicioActivity;
import com.sociomas.aventones.UI.Activities.Publicar.PublicaActivity;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends BaseCoreCompactActivity {

    private static final long SPLASH_SCREEN_DELAY = 2800;
    private ImageView imgCar, imgLogo, imgHand;
    private Animation right, ray, pulso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imgCar = (ImageView) findViewById(R.id.imgCarSocio);
        imgHand = (ImageView) findViewById(R.id.imgHand);

        Animations();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent sliderIntent = new Intent(SplashScreenActivity.this, PublicaActivity.class);
                startActivity(sliderIntent);
                finish();
            }
        };
        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }

    public void Animations() {
        right = AnimationUtils.loadAnimation(this, R.anim.right);
        imgCar.startAnimation(right);
        ray = AnimationUtils.loadAnimation(this, R.anim.ray_move);
        imgHand.startAnimation(ray);

    }
}

