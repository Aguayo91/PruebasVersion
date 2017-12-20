package com.example.lenovo7657.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lManager;
    private FloatingActionButton fab;
    private static final String DEBUG_TAG = "AppCompatActivity";
    public static final String EXTRA_UPDATE = "update";
    public static final String EXTRA_DELETE = "delete";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_COLOR = "color";
    public static final String EXTRA_INITIAL = "initial";
    public static final String TRANSITION_FAB = "fab_transition";
    public static final String TRANSITION_INITIAL = "initial_transition";
    public static final String TRANSITION_NAME = "name_transition";
    public static final String TRANSITION_DELETE_BUTTON = "delete_button_transition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        ArrayList<ObjetoPersona> newList= new ArrayList();

        newList.add(new ObjetoPersona("Tarjeta 1", "Mensaje de prueba", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 2", "Mensaje de prueba 2 ", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 3", "Mensaje de prueba 3", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 4", "Mensaje de prueba 4", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 5", "Mensaje de prueba 5", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 6", "Mensaje de prueba 6", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 7", "Mensaje de prueba 7", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 8", "Mensaje de prueba 8", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 9", "Mensaje de prueba 9", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 10", "Mensaje de prueba", R.drawable.cotorros));
        newList.add(new ObjetoPersona("tarjeta 11", "Mensaje de prueba", R.drawable.cotorros));
        newList.add(new ObjetoPersona("Tarjeta 12", "Mensaje de prueba", R.drawable.cotorros));
        newList.add(new ObjetoPersona("tarjeta 13", "Mensaje de prueba", R.drawable.cotorros));

        recyclerView=(RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(newList);
        recyclerView.setAdapter(adapter);

        // Usar un administrador para vista vertical
        lManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lManager);

        //si quieres que sea horizontal
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

    }

    public void next(View v){
        Intent i = new Intent(this,Main2Activity.class);
        ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(this,fab,TRANSITION_FAB);
        startActivity(i,options.toBundle());
    }
}
