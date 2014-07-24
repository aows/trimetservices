package ws.otero.adrian.portlandbus.trimetservices.routeconfig.models;

import java.util.List;

public class PBRouteDirection {
    private String description;
    private int dir;
    private List<PBStop> stops;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public List<PBStop> getStops() {
        return stops;
    }

    public void setStops(List<PBStop> stops) {
        this.stops = stops;
    }
}
