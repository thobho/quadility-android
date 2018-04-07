package sensor.quadility.thobho.com.quadility;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testSensorGpsSynchronization() throws Exception {
        System.out.println("START TEST");
        Observable<Long> fast = Observable.interval(100, TimeUnit.MILLISECONDS);
        Observable<Long> slow = Observable.interval(2,1, TimeUnit.SECONDS);

        fast.buffer(slow).blockingSubscribe(System.out::print);
        slow.blockingSubscribe(System.out::print);
    }
}