package ws.otero.adrian.portlandbus.trimetservices.arrivals.models;

import java.util.Calendar;

public class PBArrival {

    private Calendar scheduled;
    private Calendar estimated;
    private boolean detour;
    private int locationId;
    private String shortSign;
    private String fullSign;
    private String status;
    private int dir;
    private int route;
    private boolean departed;

    public Calendar getScheduled() {
        return scheduled;
    }

    public void setScheduled(Calendar scheduled) {
        this.scheduled = scheduled;
    }

    public Calendar getEstimated() {
        return estimated;
    }

    public void setEstimated(Calendar estimated) {
        this.estimated = estimated;
    }

    public boolean isDetour() {
        return detour;
    }

    public void setDetour(boolean detour) {
        this.detour = detour;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getShortSign() {
        return shortSign;
    }

    public void setShortSign(String shortSign) {
        this.shortSign = shortSign;
    }

    public String getFullSign() {
        return fullSign;
    }

    public void setFullSign(String fullSign) {
        this.fullSign = fullSign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public boolean isDeparted() {
        return departed;
    }

    public void setDeparted(boolean departed) {
        this.departed = departed;
    }
}
