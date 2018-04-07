package sensor.quadility.thobho.com.quadility.message;

import android.hardware.SensorEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public class MessageConverter {

    public static JSONObject convert(Measurement measurement) {
        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("startLatt", measurement.getStart().getLatitude());
        jsonMap.put("startLong", measurement.getStart().getLongitude());
        jsonMap.put("startTimestamp", measurement.getStart().getLatitude());
        jsonMap.put("endLatt", measurement.getEnd().getLatitude());
        jsonMap.put("endLong", measurement.getEnd().getLongitude());
        jsonMap.put("endTimestamp", measurement.getEnd().getLatitude());

        List<SensorEvent> events = measurement.getEvents();
        int size = events.size();
        ArrayList<Double> xs = new ArrayList<>(size);
        ArrayList<Double> ys = new ArrayList<>(size);
        ArrayList<Double> zs = new ArrayList<>(size);
        ArrayList<Long> timestamps = new ArrayList<>(size);

        for (SensorEvent event : events) {
            xs.add((double) event.values[0]);
            ys.add((double) event.values[1]);
            zs.add((double) event.values[2]);
            timestamps.add(event.timestamp);
        }

        jsonMap.put("x", new JSONArray(xs));
        jsonMap.put("y", new JSONArray(ys));
        jsonMap.put("z", new JSONArray(zs));
        jsonMap.put("timestamps", new JSONArray(timestamps));

        return new JSONObject(jsonMap);
    }
}
