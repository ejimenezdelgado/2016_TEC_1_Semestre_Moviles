package cr.ac.itcr.shopadvisor.api;

import android.os.AsyncTask;

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
import java.util.List;
import java.util.concurrent.ExecutionException;

import cr.ac.itcr.shopadvisor.entity.Place;

/**
 * Created by Efren on 04/05/2016.
 */
public class PlaceApi implements IApi<Place> {


    @Override
    public boolean Save(Place place) {
        return false;
    }

    @Override
    public boolean Update(Place place) {
        return false;
    }

    @Override
    public boolean Delete(Place place) {
        return false;
    }

    @Override
    public ArrayList<Place> GetAll() throws ExecutionException, InterruptedException {

        PlaceApiGet s=new PlaceApiGet();
        s.execute(ConstantApi.url,ConstantApi.get);
        try {
            return s.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return s.get();
    }

    @Override
    public ArrayList<Place> GetBy(Place place) {
        return null;
    }


    public class PlaceApiGet extends AsyncTask<String,Void,ArrayList<Place>> {

        @Override
        protected ArrayList<Place> doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";
            ArrayList<Place> places = new ArrayList<Place>();

            try {
                if (params[1] == ConstantApi.get) {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject jsonRootObject  = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados
                        // estado es el nombre del campo en el JSON
                        JSONArray jsonArray = jsonRootObject.optJSONArray("places");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Place place =new Place();
                            place.setId(jsonObject.getInt("id"));
                            place.setName(jsonObject.getString("name"));
                            places.add(place);
                        }
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return places;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
