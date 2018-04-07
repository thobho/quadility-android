package sensor.quadility.thobho.com.quadility.message;

import android.hardware.SensorEvent;
import android.location.Location;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Measurement {
    private Location start;
    private Location end;
    private List<SensorEvent> events;

    public Measurement(Location start, Location end, List<SensorEvent> events) {
        this.start = start;
        this.end = end;
        this.events = events;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public List<SensorEvent> getEvents() {
        return events;
    }
}

