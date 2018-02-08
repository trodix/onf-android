package com.example.aurelienthazet.bubuche;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import metier.Arbre;

public class ImportDataActivity extends AppCompatActivity {

    //Progress Dialog
    private ProgressDialog pDialog;

    //Collection
    ArrayList<Arbre> lesArbres;

    //url pour récupérer la liste des ménages
    private static String urlLesArbres = "http://10.121.38.143/onf/Import";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_arbres);

        lesArbres = new ArrayList<Arbre>();

        //Recherche les ménages en habitants dans un thread
        new chargerLesArbres().execute();
    }

    //Recherche les habitants par recherche HTTP
    class chargerLesArbres extends AsyncTask<String, String, String> {

        //Affichage dans la barte de progression
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ImportDataActivity.this);
            pDialog.setMessage("Importation des données. Merci de patienter..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try{
                url = new URL(urlLesArbres);
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
                Log.i("httpmen", "reponse test");
                //Récupération du tableau d'objets
                JSONArray array = new JSONArray(jsonObject.getString("lesArbres"));
                Log.i("httpmen", "taille du tableau " + array.length());
                //Chaque objet JSON devient un objet de type Menage, rangé dans la collection
                for (int i=0; i < array.length(); i++) {

                    //Récupération objet JSON du tableau
                    JSONObject obj = new JSONObject(array.getString(i));

                    //Lien Arbre - Objet JSON
                    Arbre unArbre = new Arbre();
                    unArbre.setId((Integer.parseInt(obj.getString("idArbre"))));
                    unArbre.setLibelleFrancais(obj.getString("libelleFrancais"));
                    unArbre.setCommune(obj.getString("commune"));
                    unArbre.setDatePlantation(obj.getString("datePlantation"));
                    unArbre.setCp(obj.getString("cp"));
                    unArbre.setGenre(obj.getString("libelleGenre"));
                    unArbre.setEspece(obj.getString("libelleEspece"));
                    Log.i("httpmen", "unArbre " + unArbre.toString());;
                    //ajout de l'arbre à la collection
                    lesArbres.add(unArbre);
                    Log.i("httpmen", "lesArbres " + unArbre.toString());
                }
                Log.i("httpmen", "lesArbres " + lesArbres.toString());



            }
            catch (IOException e) { e.printStackTrace(); }
            catch (JSONException e) { e.printStackTrace(); }
            return null;
        }

        protected void onPostExecute(String data){
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Nb d'arbres chargés : " + lesArbres.size(),Toast.LENGTH_SHORT).show();

            ArbreDAO unArbreDAO = new ArbreDAO(getApplicationContext());
            unArbreDAO.open();


            Log.d("BDD", "Open réussi");
            Cursor c =  unArbreDAO.readLesArbres();

            ArrayList<HashMap<String, String>> lesArbresE = new ArrayList<>();

            for(Arbre unArbre : lesArbres){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idArbre", Integer.toString(unArbre.getId()));
                hashMap.put("libelleFrancais", unArbre.getLibelleFrancais());
                hashMap.put("datePlantation", unArbre.getDatePlantation());
                hashMap.put("commune",unArbre.getCommune());
                hashMap.put("cp",unArbre.getCp());
                hashMap.put("genre",unArbre.getGenre());
                hashMap.put("espece",unArbre.getEspece());
                lesArbresE.add(hashMap);

                Arbre leArbre = new Arbre(unArbre.getId(),unArbre.getLibelleFrancais(),unArbre.getDatePlantation(),unArbre.getCommune(),unArbre.getCp(),unArbre.getEspece(),unArbre.getGenre());
                Log.i("httparbre", String.valueOf(leArbre));

                if(unArbreDAO.unArbreServeur(unArbre.getId())){

                }else{
                   unArbreDAO.create(leArbre);
                }

            }

            String[] from = new String[] {"_id","espece", "cp"};
            int[] to = new int[] {R.id.tvLibIdArbre, R.id.tvLibEspeceArbre, R.id.tvLibCpArbre};
            SimpleAdapter dataAdapter = new SimpleAdapter(getApplicationContext(), lesArbresE, R.layout.ligne_arbre, from, to);

            //Affectation Adapter list view
            ListView lvArbre = (ListView) findViewById(R.id.lvListeArbres);
            lvArbre.setAdapter(dataAdapter);
        }
    }
}
