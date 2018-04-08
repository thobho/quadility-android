package sensor.quadility.thobho.com.quadility.dataflow;

import java.util.Date;

public class RequestStatus {

    private int successNumber;
    private int errorNumber;
    private Date lastSuccess = new Date();
    private String lastError;

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

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    @Override
    public String toString() {
        return String.format("SUCCESS COUNT: %d\n(last at: %s)\n ERRORS: %d \n %s", successNumber, lastSuccess, errorNumber, lastError);
    }
}
