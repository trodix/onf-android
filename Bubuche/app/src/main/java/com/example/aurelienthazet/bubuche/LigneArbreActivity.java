package com.example.aurelienthazet.bubuche;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LigneArbreActivity extends AppCompatActivity {

    private Button btDetailsFicheArbre;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ligne_arbre);

        /*//Les lignes suivantes permettent de rediriger l'utilisateur vers la page concernée après un click sur un bouton
        btDetailsFicheArbre = (Button) findViewById(R.id.btDetailsFicheArbre);
        btDetailsFicheArbre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(LigneArbreActivity.this, FicheArbreActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
