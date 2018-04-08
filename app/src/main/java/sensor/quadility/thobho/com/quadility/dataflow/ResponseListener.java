package sensor.quadility.thobho.com.quadility.dataflow;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


public class ResponseListener<JSONObject> implements Response.Listener<JSONObject> , Response.ErrorListener{

    private RequestStatus requestStatus = new RequestStatus();

    @Override
    public void onErrorResponse(VolleyError error) {
        requestStatus.setLastError(error.getMessage());
        requestStatus.incrementError();
    }

    @Override
    public void onResponse(JSONObject response) {
        requestStatus.incrementSuccess();
        requestStatus.setLastSuccess(new Date());
    }

    public Observable<RequestStatus> getRequestStatus() {
        return Observable.interval(1, TimeUnit.SECONDS).map(x -> requestStatus);
    }
}
