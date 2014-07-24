package ws.otero.adrian.portlandbus.trimetservices.routeconfig.models;

import java.util.List;

public class PBRoute {
    private String description;
    private int routeId;
    private String type;
    private boolean detour;
    private List<PBRouteDirection> directions;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDetour() {
        return detour;
    }

    public void setDetour(boolean detour) {
        this.detour = detour;
    }

    public List<PBRouteDirection> getDirections() {
        return directions;
    }

    public void setDirections(List<PBRouteDirection> directions) {
        this.directions = directions;
    }
}
