package com.example.aurelienthazet.bubuche;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import bdd.ArbreDAO;

public class ListeArbresActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_arbres);
        Log.d("BDD", "Début afficherLesArbres");
        afficherLesArbres();
    }

    private void afficherLesArbres(){
        Log.d("BDD", "Début afficherLesArbres");

        //Accès BDD
        ArbreDAO arbreDAO = new ArbreDAO(this);
        Log.d("BDD","arbreDAO accessible");
        arbreDAO.open();
        Log.d("BDD", "Open réussi");

        //Récupération arbre dans un curseur
        Cursor unCurseur = arbreDAO.readLesArbres();
        Toast.makeText(getApplicationContext(), "Il y a " + unCurseur.getCount() + " arbre(s)",
                Toast.LENGTH_SHORT).show();
        Log.d("BDD", "Curseur : " + unCurseur.getCount());

        //Nom des 4 attributs lus par la BDD
        String[] from = new String[] {"_id","espece", "cp"};

        //Références des contrôles graphiques qui afficheront les valeurs
        int[] to = new int[] {R.id.tvLibIdArbre, R.id.tvLibEspeceArbre, R.id.tvLibCpArbre};

        //Adapter lien dans la base de données et contrôles graphiques
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.ligne_arbre, unCurseur, from, to, 0);
        //SimpleAdapter dataAdapter = new SimpleAdapter(this, R.layout.ligne_arbre, unCurseur, from, to, 0);
        Log.d("BDD", "Curseur : " + unCurseur.getCount());
        //Affectation de l'Adapter à la ListView
        ListView lvListeArbres = (ListView) findViewById(R.id.lvListeArbres);
        lvListeArbres.setAdapter(dataAdapter);
        arbreDAO.close();

        //Gestion du clic sur une ligne pour afficher détails arbre


        lvListeArbres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView listTextArbre = (TextView) findViewById(R.id.tvLibIdArbre);
                String text = listTextArbre.getText().toString();
                int id = i +1;
                Log.d("BDD", "Id de l'arbre (Evenement) : " + id);
                Intent intent = new Intent(ListeArbresActivity.this, FicheArbreActivity.class);
                intent.putExtra("selected-item", String.valueOf(id));
                startActivity(intent);
            }
        });


    }

}
