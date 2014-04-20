package com.earthwatch.googlemapsv2.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.earthwatch.googlemapsv2.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

//import android.support.v7.app.ActionBarActivity;

public class MainActivity extends Activity{
    Context context=this;
    // Google Map
    GoogleMap googleMap;
    Spinner spinner;

    String [] options = {"Bridge","Danger Zone","Water Tap"};

    MySQLiteHelper db = new MySQLiteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, "g7lPnTDvAdn5bDTqg6sndT5PH692LVnRptxwuapX", "BEBC5SJR2MIMkLsHlrFqobhtcKQo1A1c0rYvKQQn");

        final List<Marker> m=new ArrayList<Marker>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("marker");

        initilizeMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> markersParse, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("markers", "Retrieved " + markersParse.size() + " scores");

                    for(int i=0;i<markersParse.size();i++){
                        ParseObject tmp = markersParse.get(i);

                        Marker t = new Marker(tmp.get("type").toString(),tmp.get("title").toString(),tmp.get("description").toString(),tmp.get("latitude").toString(),tmp.get("longitude").toString());

                        Log.d("markers", "Type:" + t.getType() + "Latitude" + t.getLatitude() + "Longitutde" + t.getLongitude());
                        m.add(t);
                    }

                    if(m.size()>0)
                        for(Marker tmp:m){

                            if(tmp.getType().toString().equals("Bridge"))
                                googleMap.addMarker(new MarkerOptions()
                                        .title(tmp.getTitle().toString())
                                        .snippet(tmp.getDescription().toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bridge))
                                        .position(new LatLng(Double.parseDouble(tmp.getLatitude()), Double.parseDouble(tmp.getLongitude())))
                                );

                            if(tmp.getType().toString().equals("Mountain House"))
                                googleMap.addMarker(new MarkerOptions()
                                        .title(tmp.getTitle().toString())
                                        .snippet(tmp.getDescription().toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
                                        .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                                );

                            if(tmp.getType().toString().equals("Flora"))
                                googleMap.addMarker(new MarkerOptions()
                                        .title(tmp.getTitle().toString())
                                        .snippet(tmp.getDescription().toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.flora))
                                        .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                                );

                            if(tmp.getType().toString().equals("Fauna"))
                                googleMap.addMarker(new MarkerOptions()
                                        .title(tmp.getTitle().toString())
                                        .snippet(tmp.getDescription().toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fauna))
                                        .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                                );

                            if(tmp.getType().toString().equals("Danger Zone"))
                                googleMap.addMarker(new MarkerOptions()
                                        .title(tmp.getTitle().toString())
                                        .snippet(tmp.getDescription().toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.danger))
                                        .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                                );

                            if(tmp.getType().toString().equals("Water Tap"))
                                googleMap.addMarker(new MarkerOptions()
                                        .title(tmp.getTitle().toString())
                                        .snippet(tmp.getDescription().toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fap))
                                        .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                                );
                        }

                } else {
                    Log.d("markers", "Error: " + e.getMessage());
                }
            }
        });


        //m = db.getAllMarkers();

        try {
            // Loading map
            /*initilizeMap();
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            if(m.size()>0)
            for(Marker tmp:m){

                if(tmp.getType().toString().equals("Bridge"))
                    googleMap.addMarker(new MarkerOptions()
                            .title(tmp.getTitle().toString())
                            .snippet(tmp.getDescription().toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bridge))
                            .position(new LatLng(Double.parseDouble(tmp.getLatitude()), Double.parseDouble(tmp.getLongitude())))
                    );

                if(tmp.getType().toString().equals("Mountain House"))
                    googleMap.addMarker(new MarkerOptions()
                            .title(tmp.getTitle().toString())
                            .snippet(tmp.getDescription().toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
                            .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                    );

                if(tmp.getType().toString().equals("Flora"))
                    googleMap.addMarker(new MarkerOptions()
                            .title(tmp.getTitle().toString())
                            .snippet(tmp.getDescription().toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.flora))
                            .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                    );

                if(tmp.getType().toString().equals("Fauna"))
                    googleMap.addMarker(new MarkerOptions()
                            .title(tmp.getTitle().toString())
                            .snippet(tmp.getDescription().toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.fauna))
                            .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                    );

                if(tmp.getType().toString().equals("Danger Zone"))
                    googleMap.addMarker(new MarkerOptions()
                            .title(tmp.getTitle().toString())
                            .snippet(tmp.getDescription().toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.danger))
                            .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                    );

                if(tmp.getType().toString().equals("Water Tap"))
                    googleMap.addMarker(new MarkerOptions()
                            .title(tmp.getTitle().toString())
                            .snippet(tmp.getDescription().toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.fap))
                            .position(new LatLng(Double.parseDouble(tmp.getLatitude()),Double.parseDouble(tmp.getLongitude())))
                    );
            }*/

            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){

                @Override
                public void onMapLongClick(final LatLng latLng) {
                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    final View v = li.inflate(R.layout.alert_layout,null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setView(v);
                    builder.setCancelable(false);



                    spinner = (Spinner) v.findViewById(R.id.type);
// Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                            R.array.type_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    spinner.setAdapter(adapter);



                    builder.setPositiveButton("Create",new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText  title = (EditText) v.findViewById(R.id.title);
                            EditText description = (EditText) v.findViewById(R.id.descript);

                            try{
                                if(spinner.getSelectedItem().toString().equals("Bridge")){
                                    googleMap.addMarker(new MarkerOptions()
                                        .title(title.getText().toString())
                                        .snippet(description.getText().toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bridge))
                                        .position(latLng)
                                    );

                                }

                                if(spinner.getSelectedItem().toString().equals("Water Tap")){
                                    googleMap.addMarker(new MarkerOptions()
                                            .title(title.getText().toString())
                                            .snippet(description.getText().toString())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.fap))
                                            .position(latLng)
                                    );
                                }
                                if(spinner.getSelectedItem().toString().equals("Mountain House")){
                                    googleMap.addMarker(new MarkerOptions()
                                            .title(title.getText().toString())
                                            .snippet(description.getText().toString())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
                                            .position(latLng)
                                    );
                                }

                                if(spinner.getSelectedItem().toString().equals("Flora")){
                                    googleMap.addMarker(new MarkerOptions()
                                            .title(title.getText().toString())
                                            .snippet(description.getText().toString())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.flora))
                                            .position(latLng)
                                    );
                                }

                                if(spinner.getSelectedItem().toString().equals("Fauna")){
                                    googleMap.addMarker(new MarkerOptions()
                                            .title(title.getText().toString())
                                            .snippet(description.getText().toString())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.fauna))
                                            .position(latLng)
                                    );
                                }

                                if(spinner.getSelectedItem().toString().equals("Danger Zone")){
                                    googleMap.addMarker(new MarkerOptions()
                                            .title(title.getText().toString())
                                            .snippet(description.getText().toString())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.danger))
                                            .position(latLng)
                                    );
                                }

                                //db.addMARKER(new Marker(spinner.getSelectedItem().toString(),title.getText().toString(),description.getText().toString(),latLng.latitude+"",latLng.longitude+""));
                                ParseObject testObject = new ParseObject("marker");
                                testObject.put("type", spinner.getSelectedItem().toString());
                                testObject.put("title",title.getText().toString());
                                testObject.put("description",description.getText().toString());
                                testObject.put("latitude", latLng.latitude+"");
                                testObject.put("longitude", latLng.longitude+"");
                                testObject.saveInBackground();

                            }catch(Exception e){
                                //e.printStackTrace();
                            }

                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

}
