package com.example.aurelienthazet.bubuche;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LigneInterventionActivity extends AppCompatActivity {

    private Button btDetailsFicheIntervention;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ligne_intervention);

        //Les lignes suivantes permettent de rediriger l'utilisateur vers la page concernée après un click sur un bouton
        btDetailsFicheIntervention = (Button) findViewById(R.id.btDetailsFicheIntervention);
        btDetailsFicheIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(LigneInterventionActivity.this, FicheInterventionActivity.class);
                startActivity(intent);
            }
        });
    }
}
