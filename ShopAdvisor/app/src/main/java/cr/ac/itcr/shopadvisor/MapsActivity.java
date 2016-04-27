package cr.ac.itcr.shopadvisor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;





public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
    Button bmapa;
    Button bterreno;
    Button bhibrido;
    Button binterior;









    TextView latitud;
    TextView longitud;
    Location location;
    LocationManager locationManager;
    LocationListener locationListener;
    AlertDialog alert = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        bmapa = (Button)findViewById(R.id.bmapa);
        bterreno = (Button)findViewById(R.id.bterreno);
        bhibrido = (Button)findViewById(R.id.bhibrido);
        binterior = (Button)findViewById(R.id.binterior);

        bmapa.setOnClickListener(this);
        bterreno.setOnClickListener(this);
        bhibrido.setOnClickListener(this);
        binterior.setOnClickListener(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latitud = (TextView) findViewById(R.id.latitud);
        longitud = (TextView) findViewById(R.id.longitud);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertNoGps();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String mejorProveedor=locationManager.getBestProvider(criteria,true);
        location = locationManager.getLastKnownLocation(mejorProveedor);

        MostrarLocalizacion(location);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                MostrarLocalizacion(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
    }

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(alert != null)
        {
            alert.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                locationManager.removeUpdates(locationListener);
            }
        } else {
            locationManager.removeUpdates(locationListener);
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }


    public void MostrarLocalizacion(Location loc){
        if (loc!=null){
            latitud.setText((int) loc.getLatitude());
            longitud.setText((int) loc.getLongitude());
            //hiloconexion = new ObtenerWebService();
           // hiloconexion.execute(String.valueOf(loc.getLatitude()),String.valueOf(loc.getLongitude()));
        }
        else
        {
            latitud.setText("Latitud: (sin_datos)");
            longitud.setText("Longitud: (sin_datos)");
        }
    }








































    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bmapa:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.bhibrido:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.bterreno:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.binterior:
                // Algunos edificios tienen mapa de interior. Hay que ponerse sobre ellos y directamente veremos las plantas
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(-33.86997, 151.2089), 18));

                break;
            default:
                break;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in tec and move the camera
        LatLng tec = new LatLng(10.362250,-84.509969);
        mMap.addMarker(new MarkerOptions().position(tec).title("Marker in TEC"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tec,19));


       mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_shopadvisor))
                        .anchor(0.0f, 1.0f)
                        .position(latLng));

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "Has pulsado una marca",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }
}
