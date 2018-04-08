package sensor.quadility.thobho.com.quadility.dataflow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import io.reactivex.Observable;
import sensor.quadility.thobho.com.quadility.message.Measurement;

import static android.content.Context.LOCATION_SERVICE;

public class MeasurementProducer {

    private LocationListener locationListener;
    private LocationManager locationManager;
    private SensorEventBuffer sensorEventBuffer;

    private Location previousLocation;

    public MeasurementProducer(Context context, int bufferSize) {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        sensorEventBuffer = new SensorEventBuffer(context, bufferSize);
    }

    @SuppressLint("MissingPermission")
    public Observable<Measurement> start() {
        if (locationManager == null) {
            return Observable.empty();
        }

        sensorEventBuffer.start();

        return Observable.create(emitter -> {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (previousLocation != null) {
                        emitter.onNext(new Measurement(previousLocation, location, sensorEventBuffer.getAccumulatedEvents()));
                        sensorEventBuffer.resetBuffer();
                    }
                    previousLocation = location;
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        });
    }

    public void stop() {
        locationManager.removeUpdates(locationListener);
        sensorEventBuffer.stop();
    }

    public void setBufforSize(int newBufferSize){
        this.sensorEventBuffer.setBufferSize(newBufferSize);
    }
}
