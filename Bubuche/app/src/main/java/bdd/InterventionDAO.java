package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import metier.Arbre;
import metier.Intervention;

/**
 * Created by fabien.ladouce on 08/02/2018.
 */

public class InterventionDAO {


    private static final int VERSION_BDD = 12;
    private static final String NOM_BDD = "bubuche.db";
    private static final String TABLE_ARBRE = "arbre";

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
        /*db = createDb.getWritableDatabase();
        Log.d("BDD", "Base ouverte : " +  db.isDbLockedByCurrentThread());
        Log.d("BDD", "Base ouverte : " +  db.isDbLockedByOtherThreads());
        Log.d("BDD", "Base ouverte");*/
        return db;
    }

    public long create(Intervention uneIntervention) {
        ContentValues values = new ContentValues(); // Ensemble des valeurs à insérer, (eq HashMap)
        values.put("idIntervention", uneIntervention.getId());
        values.put("idArbre", uneIntervention.getIdArbre());
        values.put("dateIntervention", uneIntervention.getDateIntervention());
        values.put("heureIntervention", uneIntervention.getHeureIntervention());
        values.put("observations", uneIntervention.getObservations());

        Log.d("BDD","create,id :  " + uneIntervention.getId());
        Log.d("BDD","create,idArbre :  " + uneIntervention.getIdArbre());
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

    public boolean uneInterventionServeur(int idIntervention){

        String reqSQL = "SELECT idIntervention FROM " + TABLE_INTERVENTION + " WHERE idIntervention = " + idIntervention + ";";

        Cursor idExist = db.rawQuery(reqSQL, null);

        return idExist.getCount() > 0;
    }

    public Cursor readLesInterventions() {
        //Requete
        String reqSQL = "SELECT idIntervention as '_id', dateIntervention, heureIntervention, observations FROM " + TABLE_INTERVENTION;
        Log.d("BDD", reqSQL);

        //Exécution requete
        Cursor unCurseur = db.rawQuery(reqSQL, null);
        Log.d("BDD","Le curseur contient " + unCurseur.getCount() + " lignes");
        return unCurseur;
    }

}


