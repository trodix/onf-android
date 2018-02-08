package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import metier.Arbre;

/**
 * Created by fabien.ladouce on 08/02/2018.
 */

public class InterventionDAO {

    protected SQLiteDatabase db = null;
    protected CreateBdBubuche createDb = null;
    private static final String TABLE_INTERVENTION = "intervention";
    private static final String TABLE_TYPE_INTERVENTION = "typeIntervention";

        /**
         * Constructeur
         * @param context
         */
        public InterventionDAO(Context context) {

        }
        /**
         * Ouverture de la base en mode écriture
         * @return
         */

        public SQLiteDatabase open() {
            
            Log.d("BDD", "Base ouverte : " +  db.isDbLockedByCurrentThread());
            Log.d("BDD", "Base ouverte : " +  db.isDbLockedByOtherThreads());
            Log.d("BDD", "Base ouverte");
            return db;
        }

        public long create(Intervention uneIntervention) {
            ContentValues values = new ContentValues(); // Ensemble des valeurs à insérer, (eq HashMap)
            values.put("idIntervention", uneIntervention.getId());
            values.put("dateIntervention", uneIntervention.getDateIntervention());
            values.put("heureIntervention", uneIntervention.getHeureIntervention());
            values.put("observations", uneIntervention.getObservations());

            Log.d("BDD","create,id :  " + uneIntervention.getId());
            Log.d("BDD","create,date intervention :  " + uneIntervention.getDateIntervention());
            Log.d("BDD","create,heure Intervention :  " + uneIntervention.getHeureIntervention());
            Log.d("BDD","create,Observations :  " + uneIntervention.getObservations());
            return db.insert(TABLE_INTERVENTION, null, values);
        }

        /**
         * Fermeture de la base
         */
        public void close() {
            db.close();
        }


        public Cursor readLesArbres() {
            //Requete
            String reqSQL = "SELECT idArbre as '_id', libelleFrancais, commune, datePlantation, cp, genre, espece FROM " + TABLE_ARBRE;
            Log.d("BDD", reqSQL);

            //Exécution requete
            Cursor unCurseur = db.rawQuery(reqSQL, null);
            Log.d("BDD","Le curseur contient " + unCurseur.getCount() + " lignes");
            return unCurseur;
        }

        public boolean unArbreServeur(int idArbre){

            String reqSQL = "SELECT idArbre FROM " + TABLE_INTERVENTION + " WHERE idArbre = " + idArbre + ";";

            Cursor idExist = db.rawQuery(reqSQL, null);

            return idExist.getCount() > 0;
        }

        public Cursor readArbreDetail(int id) {
            //Requete
            String reqSQL = "SELECT idArbre as '_id', libelleFrancais, commune, datePlantation, cp, genre, espece FROM " + TABLE_ARBRE + " WHERE idArbre = " + id;
            Log.d("BDD", reqSQL);

            //Exécution requete
            Cursor unCurseur = db.rawQuery(reqSQL, null);
            Log.d("BDD","Ligne curseur :  " + unCurseur.getCount());

            return unCurseur;
        }

    }


}
