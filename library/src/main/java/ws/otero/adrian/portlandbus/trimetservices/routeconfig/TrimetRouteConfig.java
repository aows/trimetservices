package ws.otero.adrian.portlandbus.trimetservices.routeconfig;

import com.squareup.otto.Bus;


public class TrimetRouteConfig {
    private static final TrimetRouteConfig mInstance = new TrimetRouteConfig();
    private static final Bus BUS = new Bus();

    private TrimetRouteConfig() {}

    public static final TrimetRouteConfig get() {
        return mInstance;
    }

    public TrimetRouteConfig register(Object object) {
        BUS.register(object);
        return mInstance;
    }

    public TrimetRouteConfig unregister(Object object) {
        BUS.unregister(object);
        return mInstance;
    }

    public void post(Object object) {
        BUS.post(object);
    }

    public void getAllRoutes() {
        RouteConfigFetcher fetcher = new RouteConfigFetcher();
        fetcher.execute();
    }

    public void getRoute(int route) {
        RouteConfigFetcher fetcher = new RouteConfigFetcher();
        fetcher.execute(String.valueOf(route));
    }
}
