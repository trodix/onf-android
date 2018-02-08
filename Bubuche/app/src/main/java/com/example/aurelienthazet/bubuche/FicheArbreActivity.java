package com.example.aurelienthazet.bubuche;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bdd.ArbreDAO;

public class FicheArbreActivity extends AppCompatActivity {

    private Button btAccueilFicheArbre;
    private Button btCreerIntervention;

    private TextView tvDataIdFicheArbre;
    private TextView tvDataCommuneFicheArbre;
    private TextView tvDataDatePlantationFicheArbre;
    private TextView tvDataGenreFicheArbre;
    private TextView tvDataEspeceFicheArbre;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_arbre);

        btCreerIntervention = (Button)findViewById((R.id.btCreerIntervention));
        btCreerIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Récupération de l'id de l'arbre en chaine de caractère + conversion en int
                int id = Integer.parseInt(getIntent().getStringExtra("selected-item"));
                Intent intent = new Intent(FicheArbreActivity.this, FicheInterventionActivity.class);
                intent.putExtra("idArbre", id);

                startActivity(intent);
            }
        });

        afficherDetailsArbre();
        /*btAccueilFicheArbre = (Button) findViewById(R.id.btAccueilFicheArbre);
        btAccueilFicheArbre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FicheArbreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/


    }

    private void afficherDetailsArbre(){
        Log.d("BDD", "Début afficherDétailsArbre");

        //Accès BDD
        ArbreDAO detailArbreDAO = new ArbreDAO(this);
        Log.d("BDD","detailArbreDAO accessible");
        detailArbreDAO.open();
        Log.d("BDD", "Open réussi");

        //Récupération de l'id de l'arbre en chaine de caractère + conversion en int
        int id = Integer.parseInt(getIntent().getStringExtra("selected-item"));

        //Affichage de l'id pour test
        Log.d("BDD", "Id Arbre : " + id);

        //On récupére le résultat de la requête dans un curseur en passant l'id de l'arbre
        Cursor detailArbre = detailArbreDAO.readArbreDetail(id);
        detailArbre.moveToNext();

        tvDataIdFicheArbre = (TextView)findViewById(R.id.tvDataIdFicheArbre);
        tvDataCommuneFicheArbre = (TextView)findViewById(R.id.tvDataCommuneFicheArbre);
        tvDataDatePlantationFicheArbre = (TextView)findViewById(R.id.tvDataDatePlantaFicheArbre);
        tvDataEspeceFicheArbre = (TextView)findViewById(R.id.tvDataEspeceFicheArbre);
        tvDataGenreFicheArbre = (TextView)findViewById(R.id.tvDataGenreFicheArbre);


        Log.d("BDD", "Id Arbre : " + detailArbre.getInt(0));
        tvDataIdFicheArbre.setText(String.valueOf(detailArbre.getInt(0)));
        tvDataCommuneFicheArbre.setText(detailArbre.getString(2));
        tvDataDatePlantationFicheArbre.setText(detailArbre.getString(3));
        tvDataGenreFicheArbre.setText(detailArbre.getString(4));
        tvDataEspeceFicheArbre.setText(detailArbre.getString(5));








    }
}
