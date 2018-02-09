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

    //Progress Dialog
    private ProgressDialog pDialog;

    private ImageButton btAccueilFicheIntervention;
    int idArbre;
    int idIntervention;
    private int idSale;

    //Collection
    ArrayList<Intervention> lesInterventions = new ArrayList<>();
    ArrayList<TypeIntervention> lesTypesInterventions = new ArrayList<>();

    //url pour récupérer la liste des interventions
    private static String urlLesInterventions = "http://10.121.38.143/onf/api/getLesInterventionsAll";
    private static String urlLesTypesInterventions = "http://10.121.38.143/onf/api/getLesTypesIntervention";

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
        idArbre = Integer.parseInt(getIntent().getStringExtra("selected-item"));
        idIntervention = Integer.parseInt(getIntent().getStringExtra("selected-item"));

        btAccueilFicheIntervention = (ImageButton) findViewById(R.id.btAccueilFicheIntervention);
        btAccueilFicheIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FicheInterventionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        afficherDetailsIntervention();
        new chargerLesInterventions().execute();

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
        tvDataTypeFicheIntervention.setText(detailIntervention.getString(2));
        tvDataDateFicheIntervention.setText(detailIntervention.getString(3));
        tvDataHeureFicheIntervention.setText(detailIntervention.getString(4));
        mtObsFicheIntervention.setText(detailIntervention.getString(5));

    }

    class chargerLesInterventions extends AsyncTask<String, String, String> {

        //Affichage dans la barre de progression
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Importation des données. Merci de patienter..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(urlLesTypesInterventions);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("httpmen", "Pb URL mal formée");
            }

            HttpURLConnection urlConnection = null;

            /*Pour les types : */

            try {
                //ouverture de la connexion
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.i("httpmen", "après urlconnection " + url);

                //récupération des données
                InputStream in = null;
                in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));

                //Transformation de la réponse en chaine de caractères manipulable
                StringBuilder reponse = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    reponse.append(line).append('\n');
                }
                String result = reponse.toString();
                Log.i("httpmen", "reponse " + result);

                //Chaine de caractère retrouve le format JSON
                JSONObject jsonObject = new JSONObject(result);

                //Récupération du tableau d'objets
                JSONArray array = new JSONArray(jsonObject.getString("lesTypesInterventions"));
                Log.i("httpmen", "taille du tableau " + array.length());
                //Chaque objet JSON devient un objet de type Intervention, rangé dans la collection

                for (int i = 0; i < array.length(); i++) {

                    //Récupération objet JSON du tableau
                    JSONObject obj = new JSONObject(array.getString(i));

                    //Lien Arbre - Objet JSON
                    TypeIntervention unTypeIntervention = new TypeIntervention();
                    unTypeIntervention.setId(obj.getInt("idType"));
                    unTypeIntervention.setLibelle(obj.getString("libelleType"));

                    Log.i("httpmen", "type intervention : " + unTypeIntervention.toString());
                    ;
                    //ajout de l'arbre à la collection
                    lesTypesInterventions.add(unTypeIntervention);
                }
                Log.i("httpmen", "lesInterventions " + lesInterventions.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }



            /*Pour les interventions : */

            try {
                url = new URL(urlLesInterventions);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("httpmen", "Pb URL mal formée");
            }

            try {
                //ouverture de la connexion
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.i("httpmen", "après urlconnection " + url);

                //récupération des données
                InputStream in = null;
                in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));

                //Transformation de la réponse en chaine de caractères manipulable
                StringBuilder reponse = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    reponse.append(line).append('\n');
                }
                String result = reponse.toString();
                Log.i("httpmen", "reponse " + result);

                //Chaine de caractère retrouve le format JSON
                JSONObject jsonObject = new JSONObject(result);

                //Récupération du tableau d'objets
                JSONArray array = new JSONArray(jsonObject.getString("lesInterventions"));
                Log.i("httpmen", "taille du tableau " + array.length());
                //Chaque objet JSON devient un objet de type Intervention, rangé dans la collection

                for (int i = 0; i < array.length(); i++) {

                    //Récupération objet JSON du tableau
                    JSONObject obj = new JSONObject(array.getString(i));

                    //Lien Arbre - Objet JSON
                    Intervention uneIntervention = new Intervention();
                    uneIntervention.setIdArbre(Integer.parseInt(obj.getString("idArbre")));
                    uneIntervention.setId(Integer.parseInt(obj.getString("idIntervention")));
                    uneIntervention.setIdType(obj.getInt("idType"));
                    uneIntervention.setTypeIntervention(obj.getString("libelleType"));
                    uneIntervention.setDateIntervention((obj.getString("dateIntervention")));
                    uneIntervention.setHeureIntervention(obj.getString("heureIntervention"));
                    uneIntervention.setObservations(obj.getString("observations"));

                    Log.i("httpmen", "uneIntervention " + uneIntervention.toString());
                    ;
                    //ajout de l'arbre à la collection

                    lesInterventions.add(uneIntervention);


                    Log.i("httpmen", "lesInterventions " + uneIntervention.toString());
                }
                Log.i("httpmen", "lesInterventions " + lesInterventions.toString());


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String data) {
            //pDialog.dismiss();


            InterventionDAO uneInterventionDAO = new InterventionDAO(getApplicationContext());

            uneInterventionDAO.open();

            Log.d("BDD", "Open réussi");


            ArrayList<HashMap<String, String>> lesInterventionsA = new ArrayList<>();

            for (Intervention uneIntervention : lesInterventions) {

                if (idArbre == uneIntervention.getIdArbre()) {

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("idIntervention", Integer.toString(uneIntervention.getId()));
                    hashMap.put("idArbre", Integer.toString(uneIntervention.getIdArbre()));
                    hashMap.put("libelleType", uneIntervention.getTypeIntervention());
                    hashMap.put("idType", Integer.toString(uneIntervention.getIdType()));
                    hashMap.put("dateIntervention", uneIntervention.getDateIntervention());
                    hashMap.put("heureIntervention", uneIntervention.getHeureIntervention());
                    hashMap.put("observations", uneIntervention.getObservations());
                    lesInterventionsA.add(hashMap);
                }


                Intervention lIntervention = new Intervention(
                        uneIntervention.getId(),
                        uneIntervention.getIdArbre(),
                        uneIntervention.getTypeIntervention(),
                        uneIntervention.getIdType(),
                        uneIntervention.getDateIntervention(),
                        uneIntervention.getDateIntervention(),
                        uneIntervention.getObservations());

                Log.i("httpintervention", String.valueOf(lIntervention.toString()));

                if (uneInterventionDAO.uneInterventionServeur(uneIntervention.getId())) {

                } else {
                    uneInterventionDAO.create(lIntervention);
                    Log.i("httpintervention", lIntervention.getTypeIntervention());
                }

            }

            Toast.makeText(getApplicationContext(), "Nb d'interventions chargés : " + lesInterventionsA.size(), Toast.LENGTH_SHORT).show();

            Log.i("httpintervention", String.valueOf(lesInterventionsA.toString()));
            String[] from = new String[]{"idIntervention", "idArbre", "libelleType", "dateIntervention", "heureIntervention", "observations"};
            int[] to = new int[]{R.id.tvDataIdFicheIntervention, R.id.tvDataIdArbreFicheIntervention, R.id.tvDataTypeFicheIntervention, R.id.tvDataDateFicheIntervention, R.id.tvDataHeureFicheIntervention, R.id.mtObsFicheIntervention};
            /*SimpleAdapter dataAdapter = new SimpleAdapter(getApplicationContext(), lesInterventionsA, R.layout.activity_fiche_intervention, from, to);

            //Affectation Adapter list view
            ListView lvListeIntervention = (ListView) findViewById(R.id.lvListeIntervention);
            lvListeIntervention.setAdapter(dataAdapter);*/
        }
    }
}
