package sensor.quadility.thobho.com.quadility.http;

import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;


public class QuadilityRetryPolicy implements RetryPolicy {
    @Override
    public int getCurrentTimeout() {
        return 5000;
    }

    @Override
    public int getCurrentRetryCount() {
        return 10;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {

    }
}
