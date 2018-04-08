package sensor.quadility.thobho.com.quadility;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private boolean serviceBounded = false;
    private QuadilityService quadilityService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView statusTextBox = findViewById(R.id.sensor_count);
        TextView configurationTextView = findViewById(R.id.configuration);
        Button startButton = findViewById(R.id.start_button);
        Button applySettingsButton = findViewById(R.id.apply_settings);
        EditText measurementPerMessageEditText = findViewById(R.id.measurement_per_message);
        EditText bufferSizeEditText = findViewById(R.id.buffer_size);
        CheckBox localCheckbox = findViewById(R.id.checkbox_local);
        CheckBox serverCheckbox = findViewById(R.id.checkbox_server);

        Intent serviceIntent = new Intent(this, QuadilityService.class);
        startService(serviceIntent);
        bindService(serviceIntent, quadilityServiceConnection, Context.BIND_AUTO_CREATE);

        applySettingsButton.setOnClickListener(event -> {
            String mpm = measurementPerMessageEditText.getText().toString();
            String bs = bufferSizeEditText.getText().toString();
            if (serviceBounded) {
                if ("".equals(mpm) || "".equals(bs)) {
                    Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
                } else {
                    quadilityService.settMeasurementsPerMessage(Integer.parseInt(mpm));
                    quadilityService.setBufferSize(Integer.parseInt(bs));
                    if (localCheckbox.isChecked()) {
                        quadilityService.setUrl("http://192.168.1.5:8080");
                    }
                    if (serverCheckbox.isChecked()) {
                        quadilityService.setUrl("http://ec2-18-188-88-202.us-east-2.compute.amazonaws.com:8080");
                    }
                }
                Toast.makeText(this, "Settings applied", Toast.LENGTH_SHORT).show();
            }

            configurationTextView.setText("MpM: " + mpm + " / BS: " + bs);

        });

        localCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            serverCheckbox.setChecked(false);
        });

        serverCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            localCheckbox.setChecked(false);

        });

        startButton.setOnClickListener(event -> {
            if (serviceBounded) {
                quadilityService.getRequestStatus()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(requestStatus -> {
                            statusTextBox.setText(requestStatus.toString());
                        });
            }
        });

    }

    private ServiceConnection quadilityServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBounded = true;
            quadilityService = ((QuadilityService.QuadilityBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBounded = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(quadilityServiceConnection);
    }
}
