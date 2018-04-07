package sensor.quadility.thobho.com.quadility;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import io.reactivex.Observable;
import sensor.quadility.thobho.com.quadility.dataflow.MeasurementStream;
import sensor.quadility.thobho.com.quadility.dataflow.RequestStatus;
import sensor.quadility.thobho.com.quadility.message.Measurement;

public class QuadilityService extends Service {

    private Observable<Measurement> measurementStream;
    private HttpConnector httpConnector;
    public QuadilityService() {
        //default constructor
    }

    @Override
    public void onCreate() {
        super.onCreate();
        httpConnector = new HttpConnector(this);
        MeasurementStream measurementStreamProducer = new MeasurementStream(this);
        measurementStream = measurementStreamProducer.start();
        httpConnector.start(measurementStream);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public Observable<Measurement> getMeasurementStream() {
        return measurementStream;
    }

    public RequestStatus getRequestStatus(){
        return httpConnector.getStatus();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new QuadilityBinder();
    }

    public class QuadilityBinder extends Binder {
        public QuadilityService getService() {
            return QuadilityService.this;
        }
    }
}
