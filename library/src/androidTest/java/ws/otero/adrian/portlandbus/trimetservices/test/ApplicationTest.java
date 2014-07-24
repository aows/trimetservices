package ws.otero.adrian.portlandbus.trimetservices.test;

import android.test.ActivityTestCase;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.util.concurrent.CountDownLatch;

import ws.otero.adrian.portlandbus.trimetservices.arrivals.ArrivalsResult;
import ws.otero.adrian.portlandbus.trimetservices.arrivals.TrimetArrivals;
import ws.otero.adrian.portlandbus.trimetservices.routeconfig.RouteConfigResult;
import ws.otero.adrian.portlandbus.trimetservices.routeconfig.TrimetRouteConfig;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityTestCase {

    private static final String TAG = ApplicationTest.class.getName();

    private CountDownLatch signal;

    public void testStops() throws Throwable {
        signal = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                TrimetArrivals.get().register(this).getArrivals("1793");
            }

            @Subscribe
            public void onArrivalsAvailable(ArrivalsResult result) {
                Log.d(TAG, result.getArrivals().get(0).getFullSign());
                TrimetArrivals.get().unregister(this);
                signal.countDown();
            }
        });

        try {
            signal.await();// wait for callback
        } catch (InterruptedException e1) {
            fail();
            e1.printStackTrace();
        }
    }

    public void testAllRoutes() throws Throwable {
        signal = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                TrimetRouteConfig.get().register(this).getAllRoutes();
            }

            @Subscribe
            public void onRoutesAvailable(RouteConfigResult result) {
                Log.d(TAG, result.getRoutes().get(1).getDescription());
                Log.d(TAG, result.getRoutes().get(1).getDirections().get(0).getDescription());
                Log.d(TAG, result.getRoutes().get(1).getDirections().get(1).getDescription());
                TrimetRouteConfig.get().unregister(this);
                signal.countDown();
            }
        });

        try {
            signal.await();// wait for callback
        } catch (InterruptedException e1) {
            fail();
            e1.printStackTrace();
        }
    }

    public void testSingleRoute() throws Throwable {
        signal = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                TrimetRouteConfig.get().register(this).getRoute(100);
            }

            @Subscribe
            public void onRoutesAvailable(RouteConfigResult result) {
                Log.d(TAG, result.getRoutes().get(1).getDescription());
                Log.d(TAG, result.getRoutes().get(1).getDirections().get(0).getDescription());
                Log.d(TAG, result.getRoutes().get(1).getDirections().get(0).getStops().get(5).getDescription());
                TrimetRouteConfig.get().unregister(this);
                signal.countDown();
            }
        });

        try {
            signal.await();// wait for callback
        } catch (InterruptedException e1) {
            fail();
            e1.printStackTrace();
        }
    }


}