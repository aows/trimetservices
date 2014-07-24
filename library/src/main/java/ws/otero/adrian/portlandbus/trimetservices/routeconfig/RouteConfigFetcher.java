package ws.otero.adrian.portlandbus.trimetservices.routeconfig;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ws.otero.adrian.portlandbus.trimetservices.routeconfig.models.PBRoute;
import ws.otero.adrian.portlandbus.trimetservices.routeconfig.models.PBRouteDirection;
import ws.otero.adrian.portlandbus.trimetservices.routeconfig.models.PBStop;
import ws.otero.adrian.portlandbus.trimetservices.utils.NetworkingHelper;
import ws.otero.adrian.portlandbus.trimetservices.utils.TrimetURLBuilder;

class RouteConfigFetcher extends AsyncTask<String, Void, Object> {

    private static final String TAG = RouteConfigFetcher.class.getName();

    @Override
    protected Object doInBackground(String... params) {
        if (params.length > 1) {
            throw new IllegalStateException(TAG + " takes none or one parameter: route id");
        }
        if (params.length > 0) {
            return NetworkingHelper.jsonGETRequest(TrimetURLBuilder.getLineStopsUrl(params[0]));
        } else {
            return NetworkingHelper.jsonGETRequest(TrimetURLBuilder.getLinesUrl());
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        JSONObject jsonObject = (JSONObject) o;
        try {
            Routes routes = new Gson().fromJson(jsonObject.getString("resultSet"), Routes.class);
            TrimetRouteConfig.get().post(new RouteConfigResult(toPBRouteList(routes)));
        } catch (Exception e) {
            TrimetRouteConfig.get().post("Error fetching route(s): " + e.getMessage());
        }
    }

    //
    //
    // internal stuff
    //
    //
    class Routes {
        List<Route> route;
    }

    class Route {
        String desc;
        int route;
        String type;
        boolean detour;
        List<Dir> dir;
    }

    class Dir {
        List<Stop> stop;
        String desc;
        int dir;
    }

    class Stop {
        String desc;
        int locid;
        boolean tp;
        int seq;
        float lng;
        float lat;
    }

    private List<PBRoute> toPBRouteList(Routes routes) {
        List<PBRoute> pbRoutes = new ArrayList<PBRoute>();
        for (Route route : routes.route) {
            PBRoute pbRoute = new PBRoute();
            pbRoute.setDescription(route.desc);
            pbRoute.setType(route.type);
            pbRoute.setRouteId(route.route);
            pbRoute.setDetour(route.detour);
            List<PBRouteDirection> pbRouteDirections = new ArrayList<PBRouteDirection>();
            if (route.dir != null) {
                for (Dir dir : route.dir) {
                    PBRouteDirection pbRouteDirection = new PBRouteDirection();
                    pbRouteDirection.setDescription(dir.desc);
                    pbRouteDirection.setDir(dir.dir);
                    List<PBStop> pbStops = new ArrayList<PBStop>();
                    if (dir.stop != null) {
                        for (Stop stop : dir.stop) {
                            PBStop pbStop = new PBStop();
                            pbStop.setDescription(stop.desc);
                            pbStop.setLocationId(stop.locid);
                            pbStop.setSequence(stop.seq);
                            pbStop.setTimePoint(stop.tp);
                            pbStop.setLng(stop.lng);
                            pbStop.setLat(stop.lat);
                            pbStops.add(pbStop);
                        }
                    }
                    pbRouteDirection.setStops(pbStops);
                    pbRouteDirections.add(pbRouteDirection);
                }
            }
            pbRoute.setDirections(pbRouteDirections);
            pbRoutes.add(pbRoute);
        }
        return pbRoutes;
    }

}
