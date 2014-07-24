package ws.otero.adrian.portlandbus.trimetservices.utils;

import android.location.Location;

import ws.otero.adrian.portlandbus.trimetservices.TrimetServices;

public class TrimetURLBuilder {

    private static final String SERVER_URL = "http://developer.trimet.org/ws/V1/";
    private static final String JSON_TRUE = "/json/true";
    private static final String STREET_CAR_TRUE = "/streetcar/true";
    private static final String STOP_URL = SERVER_URL + "arrivals/locIDs/$stopID/appID/$appID" + STREET_CAR_TRUE + JSON_TRUE;
    private static final String LINES_URL = SERVER_URL + "routeConfig/dir/true/appid/$appID" + JSON_TRUE;
    private static final String LINE_STOPS_URL = SERVER_URL + "routeConfig/route/$lineID/dir/true/stops/true/appid/$appID" + JSON_TRUE;
    private static final String STOPS_NEARBY_URL = SERVER_URL + "stops/ll/$lng,$lat/meters/500/appid/$appID" + JSON_TRUE;

    // singleton logic
    private static TrimetURLBuilder mInstance;

    private TrimetURLBuilder() {
    }

    public static TrimetURLBuilder getInstance() {
        if (mInstance == null) {
            mInstance = new TrimetURLBuilder();
        }
        return mInstance;
    }

    private static String setAppID(String url) {
        return url.replace("$appID", TrimetServices.get().getAppId());
    }

    public static String getStopUrl(String stopId) {
        return setAppID(STOP_URL).replace("$stopID", stopId);
    }

    public static String getLinesUrl() {
        return setAppID(LINES_URL);
    }

    public static String getLineStopsUrl(String lineId) {
        return setAppID(LINE_STOPS_URL).replace("$lineID", lineId);
    }

    public static String getStopsNearbyUrl(Location location) {
        return setAppID(STOPS_NEARBY_URL).replace("$lng", "" + location.getLongitude()).replace("$lat", "" + location.getLatitude());
    }


}
