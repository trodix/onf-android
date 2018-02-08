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
import metier.Arbre;
import metier.Intervention;

public class FicheArbreActivity extends AppCompatActivity {

    //Progress Dialog
    private ProgressDialog pDialog;

    private Button btAccueilFicheArbre;
    private Button btCreerIntervention;
    private Button btAfficherIntervention;

    //Collection
    ArrayList<Intervention> lesInterventions = new ArrayList<>();

    //url pour récupérer la liste des interventions
    private static String urlLesInterventions = "http://10.121.38.143/onf/api/getLesInterventionsAll";

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

        btCreerIntervention = (Button) findViewById(R.id.btCreerIntervention);
        btCreerIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FicheArbreActivity.this, CreationInterventionActivity.class);
                startActivity(intent);
            }
        });
        afficherDetailsArbre();
        /*btAfficherIntervention = (Button)findViewById((R.id.btCreerIntervention));
        btCreerIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Récupération de l'id de l'arbre en chaine de caractère + conversion en int
                int id = Integer.parseInt(getIntent().getStringExtra("selected-item"));
                Intent intent = new Intent(FicheArbreActivity.this, FicheInterventionActivity.class);
                intent.putExtra("idArbre", id);

                startActivity(intent);
            }
        });*/

        new chargerLesInterventions().execute();

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


    class chargerLesInterventions extends AsyncTask<String, String, String> {

        //Affichage dans la barte de progression
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
            try{
                url = new URL(urlLesInterventions);
            } catch (MalformedURLException e){
                e.printStackTrace();
                Log.i("httpmen", "Pb URL mal formée");
            }

            HttpURLConnection urlConnection = null;
            try{
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
                while((line = r.readLine()) != null) {
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

                for (int i=0; i < array.length(); i++) {

                    //Récupération objet JSON du tableau
                    JSONObject obj = new JSONObject(array.getString(i));

                    //Lien Arbre - Objet JSON
                    Intervention uneIntervention = new Intervention();
                    uneIntervention.setIdArbre(Integer.parseInt(obj.getString("idArbre")));
                    uneIntervention.setId(Integer.parseInt(obj.getString("idIntervention")));
                    uneIntervention.setDateIntervention((obj.getString("dateIntervention")));
                    uneIntervention.setHeureIntervention(obj.getString("heureIntervention"));
                    uneIntervention.setObservations(obj.getString("observations"));

                    Log.i("httpmen", "uneIntervention " + uneIntervention.toString());;
                    //ajout de l'arbre à la collection
                    lesInterventions.add(uneIntervention);
                    Log.i("httpmen", "lesInterventions " + uneIntervention.toString());
                }
                Log.i("httpmen", "lesInterventions " + lesInterventions.toString());



            }
            catch (IOException e) { e.printStackTrace(); }
            catch (JSONException e) { e.printStackTrace(); }
            return null;
        }

        protected void onPostExecute(String data){
            //pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Nb d'interventions chargés : " + lesInterventions.size(),Toast.LENGTH_SHORT).show();

            InterventionDAO uneInterventionDAO = new InterventionDAO(getApplicationContext());

            uneInterventionDAO.open();

            ArbreDAO unArbreDAO = new ArbreDAO(getApplicationContext());
            unArbreDAO.open();
            Log.d("BDD", "Open réussi");


            ArrayList<HashMap<String, String>> lesInterventionsA = new ArrayList<>();

            for(Intervention uneIntervention : lesInterventions){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idArbre", Integer.toString(uneIntervention.getIdArbre()));
                hashMap.put("idIntervention", Integer.toString(uneIntervention.getId()));
                hashMap.put("dateIntervention", uneIntervention.getDateIntervention());
                hashMap.put("heureIntervention",uneIntervention.getHeureIntervention());
                hashMap.put("observations",uneIntervention.getObservations());

                lesInterventionsA.add(hashMap);

                Intervention lIntervention = new Intervention(
                        uneIntervention.getId(),
                        uneIntervention.getIdArbre(),
                        uneIntervention.getDateIntervention(),
                        uneIntervention.getHeureIntervention(),
                        uneIntervention.getObservations());

                Log.i("httpintervention", String.valueOf(lIntervention.toString()));

                if(unArbreDAO.uneInterventionServeur(uneIntervention.getId())){

                }else{
                    unArbreDAO.createIntervention(lIntervention);
                }

            }

            String[] from = new String[] {"_id","heureIntervention", "dateIntervention"};
            int[] to = new int[] {R.id.tvLibIdIntervention, R.id.ptTypeIntervention, R.id.tvLibDateIntervention};
            SimpleAdapter dataAdapter = new SimpleAdapter(getApplicationContext(), lesInterventionsA, R.layout.ligne_arbre, from, to);

            //Affectation Adapter list view
            ListView lvListeIntervention = (ListView) findViewById(R.id.lvListeIntervention);
            lvListeIntervention.setAdapter(dataAdapter);
        }
    }
}
