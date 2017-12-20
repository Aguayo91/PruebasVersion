package com.sociomas.core.UI.Controls.ImageView;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by oemy9 on 30/10/2017.
 */

public class ParallaxImageViewD extends android.support.v7.widget.AppCompatImageView implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;
    ShapeDrawable mDrawable = new ShapeDrawable();
    private  int x;
    private  int y;


    public ParallaxImageViewD(Context context) {
        super(context);
    }

    public ParallaxImageViewD(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxImageViewD(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initConfig(){
        sensorManager = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
    }

    public void registrarSensor(){
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void eliminarSensor(){
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x -= (int) event.values[0];
            y += (int) event.values[1];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
