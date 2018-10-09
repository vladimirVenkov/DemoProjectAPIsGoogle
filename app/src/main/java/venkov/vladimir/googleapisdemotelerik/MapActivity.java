package venkov.vladimir.googleapisdemotelerik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity {

    public GoogleMap mGoogleMap;
    final double LATITUDE = 42.6912445620777;
    final double LONGITUDE = 23.330605030059818;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        LatLng coordinates = new LatLng( LATITUDE, LONGITUDE);

        try {
            initializeMap(coordinates);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initializeMap(LatLng coordinates) {
        if (mGoogleMap == null) {
            ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(googleMap1 -> {
                        mGoogleMap = googleMap1;
                        if (mGoogleMap == null) {
                            Toast.makeText(getApplicationContext(),
                                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            MarkerOptions marker = new MarkerOptions().position(coordinates).title("Your current location");
                            mGoogleMap.addMarker(marker);

                            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                                    coordinates).zoom(6).build();

                            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    });
        }
    }
}
