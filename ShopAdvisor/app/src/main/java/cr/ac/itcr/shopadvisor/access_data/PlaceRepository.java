package cr.ac.itcr.shopadvisor.access_data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import cr.ac.itcr.shopadvisor.entity.Place;

/**
 * Created by Efren on 29/3/2016.
 */
public class PlaceRepository implements IRepository<Place> {

    private Connexion connexion;

    public PlaceRepository(Context context)
    {
        connexion=new Connexion(context);
    }

    @Override
    public boolean Save(Place place) {

        try {
            SQLiteDatabase db = connexion.getWritableDatabase();

            if (db != null) {

               // db.beginTransaction();

                ContentValues newData = new ContentValues();
               // newData.put("id", place.getId());
                newData.put("name", place.getName());

                db.insert("place", null, newData);

               // db.endTransaction();

                //Cerramos la base de datos
                connexion.close();
                return false;
            }
        } catch (Exception error) {
            Log.d("error", error.getMessage());
        }

        return true;
    }

    @Override
    public boolean Update(Place place) {

        try {
            SQLiteDatabase db = connexion.getWritableDatabase();

            if (db != null) {

                ContentValues updateData = new ContentValues();
                updateData.put("name", place.getName());

                db.update("place", updateData, "id=?", new String[]{String.valueOf(place.getId())});

                //Cerramos la base de datos
                connexion.close();
                return false;
            }
        } catch (Exception error) {
            Log.d("error", error.getMessage());
        }

        return true;
    }

    @Override
    public boolean Delete(Place place) {
        try {
            SQLiteDatabase db = connexion.getWritableDatabase();

            if (db != null) {

                String[] args = new String[]{String.valueOf(place.getId())};

                db.delete("place", "id=?", args);

                //Cerramos la base de datos
                connexion.close();
                return false;
            }
        } catch (Exception error) {
            Log.d("error", error.getMessage());
        }

        return true;
    }

    @Override
    public ArrayList<Place> GetAll() {

        ArrayList<Place> listPlace = new ArrayList<Place>();
        try {
            SQLiteDatabase db = connexion.getWritableDatabase();

            if (db != null) {

                Cursor cursor = db.query("place", new String[]{"id", "name"},
                        null, null, null, null, "id desc", null);

                if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        int id = cursor.getInt(0);
                        String nombre = cursor.getString(1);

                        Place place = new Place();
                        place.setId(id);
                        place.setName(nombre);

                        listPlace.add(place);

                    } while (cursor.moveToNext());
                }
                //Cerramos la base de datos
                connexion.close();
                return listPlace;
            }
        } catch (Exception error) {
            Log.d("error", error.getMessage());
        }
        return listPlace;
    }

    @Override
    public ArrayList<Place> GetBy(Place place) {
        ArrayList<Place> listPlace = new ArrayList<Place>();
        try {
            SQLiteDatabase db = connexion.getWritableDatabase();

            if (db != null) {

                String[] args = new String[]{String.valueOf(place.getId())};
                Cursor cursor = db.query("place", new String[]{"id", "name"},
                        "id =?", args, null, null, "id desc", null);

                if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        int id = cursor.getInt(0);
                        String nombre = cursor.getString(1);

                        Place placeTemp = new Place();
                        placeTemp.setId(id);
                        placeTemp.setName(nombre);

                        listPlace.add(placeTemp);

                    } while (cursor.moveToNext());
                }


                //Cerramos la base de datos
                connexion.close();
                return listPlace;
            }
        } catch (Exception error) {
            Log.d("error", error.getMessage());
        }
        return listPlace;
    }
}



