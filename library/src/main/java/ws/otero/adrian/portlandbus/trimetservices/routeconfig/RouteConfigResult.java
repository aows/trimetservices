package ws.otero.adrian.portlandbus.trimetservices.routeconfig;

import java.util.List;

import ws.otero.adrian.portlandbus.trimetservices.BaseResultObject;
import ws.otero.adrian.portlandbus.trimetservices.routeconfig.models.PBRoute;

public class RouteConfigResult extends BaseResultObject {

    private List<PBRoute> routes;

    public RouteConfigResult(List<PBRoute> routes) {
        this.routes = routes;
    }

    public RouteConfigResult(String errorMessage) {
        super(errorMessage);
    }

    public List<PBRoute> getRoutes() {
        return routes;
    }

}
