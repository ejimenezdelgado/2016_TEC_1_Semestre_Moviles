package cr.ac.itcr.shopadvisor.access_data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Efren on 29/3/2016.
 */
public class Connexion extends SQLiteOpenHelper {

    private static final int VERSION_BDD = 1;
    private static final String NAME_BDD = "shopadvisor";

    public Connexion(Context context) {
        super(context, NAME_BDD, null, VERSION_BDD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            StringBuilder sql = new StringBuilder();
            //Create
            String sqlCreatePlace = "CREATE TABLE IF NOT EXISTS place" +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, name CHAR(100))";
            sql.append(sqlCreatePlace);

            db.execSQL(sql.toString());

        } catch (Exception error) {
            Log.d("error", error.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            StringBuilder sql = new StringBuilder();

            for(int indiceVersion =oldVersion ; indiceVersion <newVersion; indiceVersion ++) {
                int nextVersion = indiceVersion + 1;
                switch (nextVersion) {
                    case 1:
                        String sqlDropPlace = "DROP TABLE IF EXISTS place";
                        sql.append(sqlDropPlace);
                        break;
                    case 2:
                        String sqlCreatePlace = "CREATE TABLE IF NOT EXISTS users" +
                                " (id INTEGER PRIMARY KEY AUTOINCREMENT, name CHAR(100))";
                        sql.append(sqlCreatePlace);
                        break;
                    case 3:
                        String sqlCreateAdvisor = "CREATE TABLE IF NOT EXISTS advisor" +
                                " (id INTEGER PRIMARY KEY AUTOINCREMENT, name CHAR(100))";
                        sql.append(sqlCreateAdvisor);
                        break;

                }
            }
            db.execSQL(sql.toString());
        } catch (Exception error) {
            Log.d("error", error.getMessage());
        }
    }
}
