package venkov.vladimir.googleapisdemotelerik;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

//    @BindView(R.id.btn_open_map)
//    Button btnOpenMap;

    @BindView(R.id.img_mainpic)
    ImageView imgOpenMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 1);

        }
    }

    @OnClick(R.id.img_mainpic)
    public void openMapClick() {
            Intent viewMap = new Intent(MainActivity.this, MapActivity.class);
            startActivity(viewMap);
    }
}
