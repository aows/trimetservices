package ws.otero.adrian.portlandbus.trimetservices.arrivals;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ws.otero.adrian.portlandbus.trimetservices.arrivals.models.PBArrival;
import ws.otero.adrian.portlandbus.trimetservices.arrivals.models.PBLocation;
import ws.otero.adrian.portlandbus.trimetservices.utils.NetworkingHelper;
import ws.otero.adrian.portlandbus.trimetservices.utils.TrimetURLBuilder;

class ArrivalsFetcher extends AsyncTask<String, Void, Object> {

    private static final String TAG = ArrivalsFetcher.class.getName();

    private static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Override
    protected Object doInBackground(String... params) {
        if (params == null || params.length > 1) {
            throw new IllegalStateException(TAG + " takes only one parameter: bus stop id");
        }
        return NetworkingHelper.jsonGETRequest(TrimetURLBuilder.getStopUrl(params[0]));
    }

    @Override
    protected void onPostExecute(Object s) {
        JSONObject jsonObject = (JSONObject) s;
        try {
            Arrivals arrivals = new Gson().fromJson(jsonObject.getString("resultSet"), Arrivals.class);
            TrimetArrivals.get().post(new ArrivalsResult(toPBArrivals(arrivals), toPBLocations(arrivals)));
        } catch (Exception e) {
            TrimetArrivals.get().post("Error fetching route(s): " + e.getMessage());
        }
    }

    //
    //
    // internal stuff
    //
    //
    class Arrivals {
        List<Arrival> arrival;
        List<Location> location;
        String queryTime;
    }

    class Arrival {
        String scheduled;
        String estimated;
        boolean detour;
        int locid;
        String shortSign;
        String fullSign;
        String status;
        int dir;
        int route;
        boolean departed;
    }

    class Location {
        String desc;
        int locid;
        String dir;
        float lng;
        float lat;
    }

    // to PBArrivals
    private List<PBArrival> toPBArrivals(Arrivals arrivals) {
        List<PBArrival> pbArrivals = new ArrayList<PBArrival>();
        if (arrivals != null && arrivals.arrival != null) {
            for (Arrival arrival : arrivals.arrival) {
                pbArrivals.add(toPBArrival(arrival));
            }
        }
        return pbArrivals;
    }

    private PBArrival toPBArrival(Arrival arrival) {
        PBArrival pbArrival = new PBArrival();
        pbArrival.setEstimated(getTime(arrival.estimated));
        pbArrival.setScheduled(getTime(arrival.scheduled));
        pbArrival.setFullSign(arrival.fullSign);
        pbArrival.setShortSign(arrival.shortSign);
        pbArrival.setDeparted(arrival.departed);
        pbArrival.setDetour(arrival.detour);
        pbArrival.setDir(arrival.dir);
        pbArrival.setRoute(arrival.route);
        pbArrival.setLocationId(arrival.locid);
        pbArrival.setStatus(arrival.status);
        return pbArrival;
    }

    // to PBLocations
    private List<PBLocation> toPBLocations(Arrivals arrivals) {
        List<PBLocation> pbLocations = new ArrayList<PBLocation>();
        if (arrivals != null && arrivals.arrival != null) {
            for (Location location : arrivals.location) {
                pbLocations.add(toPBLocation(location));
            }
        }
        return pbLocations;
    }

    private PBLocation toPBLocation(Location location) {
        PBLocation pbLocation = new PBLocation();
        pbLocation.setLocationId(location.locid);
        pbLocation.setDescription(location.desc);
        pbLocation.setDir(location.dir);
        pbLocation.setLat(location.lat);
        pbLocation.setLng(location.lng);
        return pbLocation;
    }

    private Calendar getTime(String dateToParse) {
        try {
            Calendar time = Calendar.getInstance();
            time.setTime(new SimpleDateFormat(DATEFORMAT).parse(dateToParse));
            return time;
        } catch (Exception e) {
            return null;
        }
    }

}
