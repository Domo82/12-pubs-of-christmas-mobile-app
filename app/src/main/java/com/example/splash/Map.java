package com.example.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;



public class Map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private static final String ERROR_MSG = "Google play services are unavailable";
    private TextView mTextView;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private Intent intent = getIntent();
    private List<Marker> mMarkers = new ArrayList<>();
    private Polyline mPolylone;

    public void centerMapOnLocation(Location location, String title) {
        //have user location located at the centre of the map
        if (location != null) {
            LatLng userlocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(userlocation).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userlocation, 12));

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        //Map is set to satellite style
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //Zoom is set to distance 10
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        //Set location search to both Fine AND Coarse location
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        PolylineOptions polylineOptions = new PolylineOptions()
                .color(Color.CYAN)
                .geodesic(true);
        mPolylone = mMap.addPolyline(polylineOptions);

        PolygonOptions polygonOptions = new PolygonOptions()
                .add(new LatLng(53.3434618,-6.2618775))
                .fillColor(Color.argb(44,00,00,44)
                );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mTextView = findViewById(R.id.myLocation);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //If connection fails, post error message
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if ( result != ConnectionResult.SUCCESS) {
            if (!googleApiAvailability.isUserResolvableError(result)) {
                Toast.makeText(this,ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        }
        //Location update every 50000 milliseconds
        mLocationRequest = new LocationRequest()
                .setInterval(50000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location location = locationResult.getLastLocation();
            if (location != null) {
                updateTextView(location);
                if (mMap != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    Calendar c = Calendar.getInstance();
                    String dateTime = new SimpleDateFormat("HH:mm yyyy-MM-dd").toString();

                    int markerNumber = mMarkers.size()+1;

                    mMarkers.add(mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(dateTime)
                            .snippet("My Location")));

                    // Pub markers
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.3434618,-6.2618775))
                            .title("O'Donoghues")
                            .snippet("15 Suffolk Street, Dublin"));

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.3502651,-6.2605541))
                            .title("The Confession Box")
                            .snippet("88 Marlborough Street, Dublin"));

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.3504318,-6.2574351))
                            .title("The Celt")
                            .snippet("81 Talbot St, Dublin"));

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.3458948,-6.261795))
                            .title("The Palace")
                            .snippet("21 Fleet St, Dublin"));

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.3439052,-6.26295))
                            .title("O'Neills")
                            .snippet("2 Suffolk St, Dublin"));

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.3469149,-6.257801))
                            .title("Mulligans")
                            .snippet("8 Poolbeg St, Dublin"));

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.3377566,-6.2546141))
                            .title("Toners")
                            .snippet("139 Baggot St Lower, Dublin"));
                }
            }
        }
    };

    private void requestLocationUpdates() {
        if (ActivityCompat
                .checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PERMISSION_GRANTED ||
                ActivityCompat
                        .checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                        == PERMISSION_GRANTED) { FusedLocationProviderClient fusedLocationClient
                = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
    }

    private static final int LOCATION_PERMISSION_REQUEST = 1;
    public static final String TAG = "WheresMyPubActivity";
    private static final int REQUEST_CHECK_SETTINGS = 2;
    @Override
    protected void onStart() {
        super.onStart();

        int permission = ActivityCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION);

        if(permission == PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task =
                client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                requestLocationUpdates();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes
                            .RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(Map.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            Log.e(TAG, "Location Settings resolution failed", sendEx);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d(TAG, "Location Settings can't be resolved.");
                        requestLocationUpdates();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final LocationSettingsStates states =
                LocationSettingsStates.fromIntent(data);

        //Request settings from device
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    requestLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    Log.d(TAG, "Requested settings changes declined by user.");
                    if (states.isLocationUsable())
                        requestLocationUpdates();
                    else
                        Log.d(TAG, "No Location Services available.");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int [] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults[0] != PERMISSION_GRANTED)
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();

            else
                getLastLocation();
        }
    }

    private void getLastLocation() {
        FusedLocationProviderClient fusedLocationClient;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (
                ActivityCompat
                        .checkSelfPermission(this, ACCESS_FINE_LOCATION)
                        == PERMISSION_GRANTED || ActivityCompat
                        .checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                        == PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateTextView(location);
                }
            });
        } else {
            Location placeLocation = new Location((LocationManager.GPS_PROVIDER));
            placeLocation.setLatitude(MainActivity.locations.get(intent.getIntExtra("",0)).latitude);
            placeLocation.setLongitude(MainActivity.locations.get(intent.getIntExtra("",0)).longitude);
            centerMapOnLocation(placeLocation, MainActivity.places.get(intent.getIntExtra("placeNumber",0)));
        }
    }

    //If location is or is not found post string
    private void updateTextView(Location location) {
        String latLongString = "No location found";
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString ="Your current location is:" +"\nLat: "+ lat +"\nLong: " + lng;
        }
        mTextView.setText(latLongString);
    }

    private String geocodeLocation(Location location) {
        String returnString="";
        return returnString;
    }

    //Long click option on the map
    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String address = "";

        try{
            List<Address> listAdresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (listAdresses != null && listAdresses.size() > 0) {
                if (listAdresses.get(0).getSubThoroughfare() != null) {
                    if (listAdresses.get(0).getSubThoroughfare() != null) {
                        address += listAdresses.get(0).getSubThoroughfare() + " ";
                    }
                    address += listAdresses.get(0).getThoroughfare();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //If address is blank on th map, print timestamp
        if (address.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
            address += sdf.format(new Date());
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        MainActivity.places.add(address);
        MainActivity.locations.add(latLng);

        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.splash;", Context.MODE_PRIVATE);

        try {

            ArrayList<String> latitude = new ArrayList<>();
            ArrayList<String> longitude = new ArrayList<>();

            for (LatLng coord : MainActivity.locations) {
                latitude.add(Double.toString(coord.latitude));
                longitude.add(Double.toString(coord.longitude));
            }

            sharedPreferences.edit().putString("places", ObjectSerializer.serialize(MainActivity.places)).apply();
            sharedPreferences.edit().putString("lats", ObjectSerializer.serialize(latitude)).apply();
            sharedPreferences.edit().putString("lons", ObjectSerializer.serialize(longitude)).apply();


        } catch (Exception e) {
            e.printStackTrace();
        }
        //When long click is activated, create a toast
        Toast.makeText(this, "Location Saved!", Toast.LENGTH_SHORT).show();

    }

}


