package sensor.quadility.thobho.com.quadility;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

class HttpConnector {
    private static final String url = "http://192.168.1.5:8080/measurement";
    private static final int MESSAGE_SIZE = 5;
    private RequestQueue queue;
    private ResponseListener<JSONObject> responseListener;

    HttpConnector(Context context) {
        queue = Volley.newRequestQueue(context);
        responseListener = new ResponseListener<>();
    }

    void start(Observable<Measurement> measurementStream) {
        measurementStream
                .map(MessageConverter::convert)
                .buffer(MESSAGE_SIZE)
                .map(JSONArray::new)
                .map(this::createRequest)
                .subscribe(jsonBody -> {
                    queue.add(jsonBody);
                });
    }

    private JsonObjectRequest createRequest(JSONArray measurementJsonArray) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("measurements", measurementJsonArray);
        JSONObject jsonRequest = new JSONObject(jsonBody);
        System.out.println(jsonRequest.toString());
        return new JsonObjectRequest(Request.Method.POST, url, jsonRequest, responseListener, responseListener);
    }

    public RequestStatus getStatus(){
        return responseListener.getRequestStatus();
    }
}
