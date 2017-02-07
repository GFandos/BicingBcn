package gfandos.bicingbcn.Fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
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

    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;
    private RadiusMarkerClusterer stationMarkers;

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

        stationMarkers = new RadiusMarkerClusterer(getContext());
        map.getOverlays().add(stationMarkers);

        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();

        stationMarkers.setIcon(clusterIcon);
        stationMarkers.setRadius(100);

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

    public void setupMarkers(ArrayList<Station> stations) {

        for(int i = 0; i < stations.size(); ++i) {

            Station station = stations.get(i);

            Marker marker = new Marker(map);
            GeoPoint point = new GeoPoint(
                    station.getLatitude(),
                    station.getLongitude()
            );
            marker.setPosition(point);

            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            marker = setIcon(marker, stations.get(i));

//            marker.setIcon(getResources().getDrawable(R.drawable.parking));


            marker.setTitle(station.getName());
            marker.setAlpha(0.6f);

            stationMarkers.add(marker);
        }

        stationMarkers.invalidate();
        map.invalidate();
    }

    private Marker setIcon(Marker marker, Station s) {

        String type = s.getType();
        int occupation = s.getOccupation();

        if(type.compareTo("BIKE") == 0) marker.setIcon(getResources().getDrawable(R.drawable.marker_mecanic));
        else marker.setIcon(getResources().getDrawable(R.drawable.marker_electric));

        return marker;

    }

    private class RefreshAsyncTask extends AsyncTask<Void, Void, ArrayList<Station>> {

        @Override
        protected ArrayList<Station> doInBackground(Void... voids) {

            ArrayList<Station> stations = getStationsFromApiUtils.getStations();

            for(int i = 0; i < stations.size(); ++i) {
                Log.d("DEBUG", stations.get(i).toString());
            }

            return stations;
        }

        @Override
        protected void onPostExecute(ArrayList<Station> stations) {

            setupMarkers(stations);

        }

    }


}
