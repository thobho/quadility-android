package sensor.quadility.thobho.com.quadility.dataflow;

import java.util.Date;

public class RequestStatus {

    private int successNumber;
    private int errorNumber;
    private Date lastSuccess = new Date();
    private int allAccelerometerMeasurements;

    public void addAccelerometerMeasurement(int add) {
        allAccelerometerMeasurements += add;
    }

    public void incrementSuccess() {
        successNumber++;
    }

    public void incrementError() {
        errorNumber++;
    }

    public int getSuccessNumber() {
        return successNumber;
    }


    public int getErrorNumber() {
        return errorNumber;
    }


    public Date getLastSuccess() {
        return lastSuccess;
    }

    public void setLastSuccess(Date lastSuccess) {
        this.lastSuccess = lastSuccess;
    }

    @Override
    public String toString() {
        return String.format("AML: %d\nSUCCESS COUNT: %d\n(last at: %s)\n ERRORS: %d", allAccelerometerMeasurements/(successNumber+errorNumber+1), successNumber, lastSuccess, errorNumber);
    }
}
