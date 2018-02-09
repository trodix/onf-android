package com.example.aurelienthazet.bubuche;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import bdd.ArbreDAO;
import bdd.InterventionDAO;
import metier.Intervention;
import metier.TypeIntervention;

public class FicheInterventionActivity extends AppCompatActivity {

    private ImageButton btAccueilFicheIntervention;
    int idArbre;
    int idIntervention;
    private int idSale;


    private TextView tvDataIdFicheIntervention;
    private TextView tvDataIdArbreFicheIntervention;
    private TextView tvDataTypeFicheIntervention;
    private TextView tvDataDateFicheIntervention;
    private TextView tvDataHeureFicheIntervention;
    private TextView mtObsFicheIntervention;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_intervention);

        //Récupération de l'id de l'arbre et de l'intervention en chaine de caractère + conversion en int

        idIntervention = Integer.parseInt(getIntent().getStringExtra("idIntervention"));

        btAccueilFicheIntervention = (ImageButton) findViewById(R.id.btAccueilFicheIntervention);
        btAccueilFicheIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FicheInterventionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        afficherDetailsIntervention();

    }

    private void afficherDetailsIntervention(){
        Log.d("BDD", "Début afficherDétailsIntervention");

        //Accès BDD
        InterventionDAO detailInterventionDAO = new InterventionDAO(this);
        Log.d("BDD","detailInterventionDAO accessible");
        detailInterventionDAO.open();
        Log.d("BDD", "Open réussi");


        //Affichage de l'id pour test
        Log.d("BDD", "Id Intervention : " + idIntervention);
        idSale = idIntervention;
        //On récupère le résultat de la requête dans un curseur en passant l'id de l'intervention

        Cursor detailIntervention = detailInterventionDAO.readDetailInterventions(idIntervention);
        detailIntervention.moveToNext();

        tvDataIdFicheIntervention = (TextView)findViewById(R.id.tvDataIdFicheIntervention);
        tvDataIdArbreFicheIntervention = (TextView)findViewById(R.id.tvDataIdArbreFicheIntervention);
        tvDataTypeFicheIntervention = (TextView)findViewById(R.id.tvDataTypeFicheIntervention);
        tvDataDateFicheIntervention = (TextView)findViewById(R.id.tvDataDateFicheIntervention);
        tvDataHeureFicheIntervention = (TextView)findViewById(R.id.tvDataHeureFicheIntervention);
        mtObsFicheIntervention = (TextView)findViewById(R.id.mtObsFicheIntervention);


        Log.d("BDD", "Id Arbre : " + detailIntervention.getInt(0));
        tvDataIdFicheIntervention.setText(String.valueOf(detailIntervention.getInt(0)));
        tvDataIdArbreFicheIntervention.setText(String.valueOf(detailIntervention.getString(1)));
        tvDataTypeFicheIntervention.setText((getIntent().getStringExtra("typeIntervention")));
        tvDataDateFicheIntervention.setText(detailIntervention.getString(2));
        tvDataHeureFicheIntervention.setText(detailIntervention.getString(3));
        mtObsFicheIntervention.setText(detailIntervention.getString(4));

    }

}
