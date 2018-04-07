package sensor.quadility.thobho.com.quadility.dataflow;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

import sensor.quadility.thobho.com.quadility.fixedqueue.FixedSizeQueue;

import static android.content.Context.SENSOR_SERVICE;

public class SensorEventBuffer implements SensorEventListener{

    private SensorManager sensorManager;

    private FixedSizeQueue<SensorEvent> accumulatedEvents;
    private static final int BUFFER_SIZE = 10000;

    public SensorEventBuffer(Context context) {
        this.accumulatedEvents = new FixedSizeQueue<>(BUFFER_SIZE);
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }

    public void start(){
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stop(){
        sensorManager.unregisterListener(this);
    }

    public List<SensorEvent> getAccumulatedEvents(){
        return this.accumulatedEvents;
    }

    public void resetBuffer(){
        accumulatedEvents = new FixedSizeQueue<>(BUFFER_SIZE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        accumulatedEvents.addFirstIfAllowed(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
