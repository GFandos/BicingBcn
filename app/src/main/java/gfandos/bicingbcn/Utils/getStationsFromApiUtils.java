package gfandos.bicingbcn.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import gfandos.bicingbcn.Pojo.Station;

/**
 * Created by 47989768s on 20/12/16.
 */

public class getStationsFromApiUtils {

    private static String url="http://wservice.viabicing.cat/v2/stations";

    public static ArrayList<Station> getStations() {

        ArrayList<Station> stations = new ArrayList<>();

        try {

            JSONObject jsonO = new JSONObject(HttpUtils.get(url));
            JSONArray jsonStations = jsonO.getJSONArray("stations");

            for (int i = 0; i < jsonStations.length(); ++i) {

                int id, occupation, bikes, slots;
                double latitude, longitude;
                String name, type;

                JSONObject object = jsonStations.getJSONObject(i);

                id = Integer.valueOf(object.getString("id"));
                bikes = Integer.valueOf(object.getString("bikes"));
                slots = Integer.valueOf(object.getString("slots"));
                name = object.getString("streetName");
                latitude = Double.valueOf(object.getString("latitude"));
                longitude = Double.valueOf(object.getString("longitude"));
                type = object.getString("type");

                occupation = getOcupation(slots, bikes);

                Station s = new Station(id, name, latitude, longitude, type, occupation);
                stations.add(s);

            }
            for(int i = 0; i < stations.size(); ++i) {
                Log.d("Llistat cartes: ", stations.get(i).toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stations;

    }

    private static int getOcupation(int slots, int bikes) {

        if(bikes == 0) return 0;

        double occupation = ((double) bikes/((double)slots + (double)bikes)) * 100;

        return ((int) occupation);

    }


}
