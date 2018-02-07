package bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aurelien.thazet on 06/02/2018.
 */

public class CreateBdBubuche extends SQLiteOpenHelper {


        private static String TABLE_ARBRE = "arbre";
        private static final String CREATE_BDD = "CREATE TABLE " + TABLE_ARBRE + "(" +
                "idArbre INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "libelleFrancais TEXT NOT NULL, " +
                "commune TEXT NOT NULL, " +
                "cp TEXT NOT NULL, " +
                "datePlantation DATE NOT NULL," +
                "genre TEXT NOT NULL," +
                "espece TEXT NOT NULL);";

        // Constructeur, à générer automatiquement
        public CreateBdBubuche(Context context, String name, SQLiteDatabase.CursorFactory
                factory, int version) {
            super(context, name, factory, version);
        }

        /**
         * Création de la base de données si elle n'existe pas
         *
         * @param db base
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_BDD);
            Log.d("BDD", "Base créée");
        }

        /**
         * Création d'une nouvelle base en cas de changement de version
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE " + TABLE_ARBRE + ";");
            Log.d("BDD", "Table arbre supprimée");
            onCreate(db);
        }
    }

