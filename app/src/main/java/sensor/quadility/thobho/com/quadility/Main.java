package sensor.quadility.thobho.com.quadility;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import sensor.quadility.thobho.com.quadility.message.Measurement;

public class Main extends AppCompatActivity {

    private boolean serviceBounded = false;
    private QuadilityService quadilityService;

    private TextView textView;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.sensor_count);
        startButton = findViewById(R.id.start_button);
        Intent serviceIntent = new Intent(this, QuadilityService.class);
        startService(serviceIntent);

        startButton.setOnClickListener(even -> {
            bindService(serviceIntent, quadilityServiceConnection, Context.BIND_AUTO_CREATE);
            if (serviceBounded) {
                Thread t = new Thread() {
                    //TODO fix workaround
                    @Override
                    public void run() {
                        try {
                            while (!isInterrupted()) {
                                Thread.sleep(1000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView.setText(quadilityService.getRequestStatus().toString()+"\n CT:"+new Date());
                                    }
                                });
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                };

                t.start();
            }
        });

    }

    private ServiceConnection quadilityServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBounded = true;
            quadilityService = ((QuadilityService.QuadilityBinder) service).getService();
            System.out.println("SERVICE BINDED");

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
