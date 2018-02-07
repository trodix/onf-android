package com.example.aurelienthazet.bubuche;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btListeArbres;
    private Button btImportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Les lignes suivantes permettent de rediriger l'utilisateur vers la page concernée après un click sur un bouton
        btListeArbres = (Button) findViewById(R.id.btListeArbres);
        btListeArbres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, ListeArbresActivity.class);
                startActivity(intent);
            }
        });

        btImportData = (Button) findViewById(R.id.btImportData);
        btImportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, ImportDataActivity.class);
                startActivity(intent);
            }
        });
    }
}
