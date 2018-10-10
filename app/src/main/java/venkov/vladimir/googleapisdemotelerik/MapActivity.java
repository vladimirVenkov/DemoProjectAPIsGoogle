package venkov.vladimir.googleapisdemotelerik;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
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

        ButterKnife.bind(this);

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
                                    coordinates).zoom(16).build();

                            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    });
        }
    }

    @OnClick(R.id.btn_share_this_location)
    public void openContactsClick() {
//        Intent pickContactToShareThisLocation = new Intent(this, .class);
//        startActivity(pickContactToShareThisLocation);
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_CONTACTS)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_CONTACTS}, 1);
//
//        }

        if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            Cursor c = getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst()) {

//                String phoneNumber = "";
                String emailAddress = "";
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer
//
//                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//                if (hasPhone.equalsIgnoreCase("1"))
//                    hasPhone = "true";
//                else
//                    hasPhone = "false";
//
//                if (Boolean.parseBoolean(hasPhone)) {
//                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
//                    while (phones.moveToNext()) {
//                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    }
//                    phones.close();
//                }

                // Find Email Addresses
                Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
                while (emails.moveToNext()) {
                    emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }
                emails.close();

                //mainActivity.onBackPressed();
                // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();

//                tvname.setText("Name: " + name);
//                tvphone.setText("Phone: " + phoneNumber);
//                tvmail.setText("Email: " + emailAddress);
//                Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);

                // --- new stuff from here ---
                String[] toS = {emailAddress};
                String subS = "Extra Fine Location" ;
                String mesS = "Dear, " + name + ",\nCheck our Super Awesome Store on following Address " +
                        "(and remember to bring some serious Cash!" + "\nLatitude: " + LATITUDE + "\nLongitude: " + LONGITUDE;

                Toast.makeText(this,subS, Toast.LENGTH_LONG).show();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, toS); //todo email is not filled - need fix
                email.putExtra(Intent.EXTRA_SUBJECT, subS);
                email.putExtra(Intent.EXTRA_TEXT, mesS);

                email.setType("massager/rfc822");

                startActivity(Intent.createChooser(email, "Hurry up and Choose app to send this urgent mail!"));

            }
            c.close();
        }
    }
}
