package sensor.quadility.thobho.com.quadility;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import io.reactivex.Observable;
import sensor.quadility.thobho.com.quadility.dataflow.MeasurementProducer;
import sensor.quadility.thobho.com.quadility.dataflow.RequestStatus;
import sensor.quadility.thobho.com.quadility.http.HttpConnection;
import sensor.quadility.thobho.com.quadility.message.Measurement;

public class QuadilityService extends Service {

    private HttpConnection httpConnection;
    private MeasurementProducer measurementProducer;
    private Observable<Measurement> measurementStream;

    public QuadilityService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        httpConnection = new HttpConnection(this, "http://192.168.1.5:8080", 2);
        measurementProducer = new MeasurementProducer(this, 1000);
        measurementStream = measurementProducer.start();
        httpConnection.start(measurementStream);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        measurementProducer.stop();
    }

    public Observable<Measurement> getMeasurementStream() {
        return measurementStream;
    }

    public Observable<RequestStatus> getRequestStatus(){
        return httpConnection.getStatus();
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

    public void setUrl(String newUrl){
        this.httpConnection.setUrl(newUrl);
    }

    public void settMeasurementsPerMessage(int measurementsPerMessage){
        this.httpConnection.setMeasurementsPerMessage(measurementsPerMessage);
    }

    public void setBufferSize(int bufferSize){
        this.measurementProducer.setBufforSize(bufferSize);
    }
}
