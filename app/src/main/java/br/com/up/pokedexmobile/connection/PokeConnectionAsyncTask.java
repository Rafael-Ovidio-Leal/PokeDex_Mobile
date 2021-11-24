package br.com.up.pokedexmobile.connection;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import br.com.up.pokedexmobile.models.Pokemon;

public class PokeConnectionAsyncTask extends AsyncTask<String, Integer, String> {

    private OnRequestListener listener;

    public PokeConnectionAsyncTask(OnRequestListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {

        String urlString = strings[0];

        //Conexão API
        try {
            URL url = new URL(urlString);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200){

                InputStream inputStream = connection.getInputStream();
                return IOUtils.toString(inputStream, "UTF-8");

            }else{
                return null;
            }

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{

            JSONObject object = new JSONObject(s);
            listener.onRequestFinish(object);

        }catch (Exception e){
            listener.onRequestFinish(null);
        }

    }

    public interface OnRequestListener{
        void onRequestFinish(JSONObject object);
    }

}
