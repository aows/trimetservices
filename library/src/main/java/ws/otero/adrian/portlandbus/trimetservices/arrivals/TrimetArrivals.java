package ws.otero.adrian.portlandbus.trimetservices.arrivals;

import com.squareup.otto.Bus;

public class TrimetArrivals {

    private static final TrimetArrivals mInstance = new TrimetArrivals();
    private static final Bus BUS = new Bus();

    public static final TrimetArrivals get() {
        return mInstance;
    }

    public TrimetArrivals register(Object object) {
        BUS.register(object);
        return mInstance;
    }

    public TrimetArrivals unregister(Object object) {
        BUS.unregister(object);
        return mInstance;
    }

    public void post(Object object) {
        BUS.post(object);
    }

    public void getArrivals(String busStopId) {
        ArrivalsFetcher fetcher = new ArrivalsFetcher();
        fetcher.execute(busStopId);
    }


}
