package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import me.adolfoquaranta.coletadigital.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarInicio();
            }
        }, 2500);
    }

    void mostrarInicio(){
        Intent intent = new Intent(SplashScreenActivity.this, Inicio.class);
        startActivity(intent);
        finish();
    }
}
