package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import metier.Arbre;
import metier.Intervention;

/**
 * Created by aurelien.thazet on 06/02/2018.
 */

public class ArbreDAO {

    private static final int VERSION_BDD = 13;
    private static final String NOM_BDD = "bubuche.db";
    private static final String TABLE_ARBRE = "arbre";
    protected SQLiteDatabase db = null;
    private static final String TABLE_INTERVENTION = "intervention";
    private static final String TABLE_TYPE_INTERVENTION = "typeIntervention";
    protected CreateBdBubuche createDb = null;
    /**
     * Constructeur
     * @param context
     */
    public ArbreDAO(Context context) {
        createDb = new CreateBdBubuche(context, NOM_BDD, null, VERSION_BDD);
    }

    public boolean uneInterventionServeur(int idIntervention){

        String reqSQL = "SELECT idIntervention FROM " + TABLE_INTERVENTION + " WHERE idIntervention = " + idIntervention + ";";

        Cursor idExist = db.rawQuery(reqSQL, null);

        return idExist.getCount() > 0;
    }
    /**
     * Ouverture de la base en mode écriture
     * @return
     */
    public SQLiteDatabase open() {
        db = createDb.getWritableDatabase();
        Log.d("BDD", "Base ouverte : " +  db.isDbLockedByCurrentThread());
        Log.d("BDD", "Base ouverte : " +  db.isDbLockedByOtherThreads());
        Log.d("BDD", "Base ouverte");
        return db;
    }

    public long createIntervention(Intervention uneIntervention) {
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

    public CreateBdBubuche getCreateDb() {
        return createDb;
    }

    public long create(Arbre unArbre) {
        ContentValues values = new ContentValues(); // Ensemble des valeurs à insérer, (eq HashMap)
        values.put("idArbre", unArbre.getId());
        values.put("libelleFrancais", unArbre.getLibelleFrancais());
        values.put("commune", unArbre.getCommune());
        values.put("cp", unArbre.getCp());
        values.put("datePlantation", unArbre.getDatePlantation());
        values.put("genre", unArbre.getGenre());
        values.put("espece", unArbre.getEspece());

        Log.d("BDD","create, idArbre : " +  unArbre.getId());
        Log.d("BDD","create, libelleFrancais : " + unArbre.getLibelleFrancais());
        Log.d("BDD","create, commune : " + unArbre.getCommune());
        Log.d("BDD","create, cp : " + unArbre.getCp());
        Log.d("BDD","create,libelleGenre :  " + unArbre.getGenre());
        Log.d("BDD","create,libelleEspece :  " + unArbre.getEspece());
        return db.insert(TABLE_ARBRE, null, values);
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

        String reqSQL = "SELECT idArbre FROM " + TABLE_ARBRE + " WHERE idArbre = " + idArbre + ";";

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

