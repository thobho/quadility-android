package sensor.quadility.thobho.com.quadility.dataflow;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.time.LocalDateTime;
import java.util.Date;


public class ResponseListener<JSONObject> implements Response.Listener<JSONObject> , Response.ErrorListener{

    private RequestStatus requestStatus = new RequestStatus();

    @Override
    public void onErrorResponse(VolleyError error) {
        requestStatus.incrementError();
    }

    @Override
    public void onResponse(JSONObject response) {
        requestStatus.incrementSuccess();
        requestStatus.setLastSuccess(new Date());
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
}
