package venkov.vladimir.googleapisdemotelerik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {

    public GoogleMap mGoogleMap;
    final double LATITUDE = 42.6912445620777;
    final double LONGITUDE = 23.330605030059818;

    @BindView(R.id.btn_share_this_location)
    Button mShareThisLocationButton;


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
                            MarkerOptions marker = new MarkerOptions().position(coordinates).title("Your best Store location");
                            mGoogleMap.addMarker(marker);

                            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                                    coordinates).zoom(14).build();

                            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    });
        }
    }

    @OnClick(R.id.btn_share_this_location)
    public void openContactsClick() {
//        Intent pickContactToShareThisLocation = new Intent(this, .class);
//        startActivity(pickContactToShareThisLocation);
    }
}
