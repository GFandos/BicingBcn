package gfandos.bicingbcn.Fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

import gfandos.bicingbcn.Pojo.Station;
import gfandos.bicingbcn.R;
import gfandos.bicingbcn.Utils.getStationsFromApiUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class MapActivityFragment extends Fragment {

    private MapView map;
    ArrayList<Station> stations;

    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;

    public MapActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map = (MapView) view.findViewById(R.id.map);

        initializeMap();
        setZoom();

        putMarkers();

        return view;
    }

    private void putMarkers() {

        setupMarkerOverlay();

        RefreshAsyncTask refreshAsyncTask = new RefreshAsyncTask();

        refreshAsyncTask.execute();

    }

    private void setupMarkerOverlay() {
    }

    private void setZoom() {

        GeoPoint startPoint = new GeoPoint(41.3851, 2.1734);
        IMapController mapController = map.getController();
        mapController.setZoom(13);
        mapController.setCenter(startPoint);

    }

    private void initializeMap() {

        map.invalidate();

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

    }

    private class RefreshAsyncTask extends AsyncTask<Void, Void, ArrayList<Station>> {

        @Override
        protected ArrayList<Station> doInBackground(Void... voids) {

            stations = getStationsFromApiUtils.getStations();

            for(int i = 0; i < stations.size(); ++i) {
                Log.d("DEBUG", stations.get(i).toString());
            }

            return stations;
        }

    }


}
