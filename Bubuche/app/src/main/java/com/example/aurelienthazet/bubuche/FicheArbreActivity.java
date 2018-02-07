package com.example.aurelienthazet.bubuche;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FicheArbreActivity extends AppCompatActivity {

    private Button btAccueilFicheArbre;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_arbre);

        btAccueilFicheArbre = (Button) findViewById(R.id.btAccueilFicheArbre);
        btAccueilFicheArbre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FicheArbreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
