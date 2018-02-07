package com.example.aurelienthazet.bubuche;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FicheInterventionActivity extends AppCompatActivity {

    private Button btAccueilFicheIntervention;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_intervention);

        btAccueilFicheIntervention = (Button) findViewById(R.id.btAccueilFicheIntervention);
        btAccueilFicheIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FicheInterventionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
