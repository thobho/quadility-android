package sensor.quadility.thobho.com.quadility.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import sensor.quadility.thobho.com.quadility.dataflow.RequestStatus;
import sensor.quadility.thobho.com.quadility.dataflow.ResponseListener;
import sensor.quadility.thobho.com.quadility.message.Measurement;
import sensor.quadility.thobho.com.quadility.message.MessageConverter;

public class HttpConnection {

    private static final String MEASUREMENT_PATH = "/measurement";
    private String url;
    private int measurementsPerMessage;

    private RequestQueue queue;
    private ResponseListener<JSONObject> responseListener;

    public HttpConnection(Context context, String url, int measurementsPerMessage) {
        this.queue = Volley.newRequestQueue(context);
        this.url = url;
        this.measurementsPerMessage = measurementsPerMessage;
        this.responseListener = new ResponseListener<>();
    }

    public void start(Observable<Measurement> measurementStream) {
        measurementStream
                .map(MessageConverter::convert)
                .buffer(measurementsPerMessage)
                .map(JSONArray::new)
                .map(this::createRequest)
                .subscribe(jsonBody -> queue.add(jsonBody));
    }

    private JsonObjectRequest createRequest(JSONArray measurementJsonArray) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("measurements", measurementJsonArray);
        JSONObject jsonRequest = new JSONObject(jsonBody);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + MEASUREMENT_PATH, jsonRequest, responseListener, responseListener);
        jsonObjectRequest.setRetryPolicy(new QuadilityRetryPolicy());
        return jsonObjectRequest;
    }

    public Observable<RequestStatus> getStatus() {
        return responseListener.getRequestStatus();
    }

    public void setMeasurementsPerMessage(int measurementsPerMessage) {
        this.measurementsPerMessage = measurementsPerMessage;
    }

    public void setUrl(String serverUrl) {
        this.url = serverUrl;
    }
}
