package ws.otero.adrian.portlandbus.trimetservices.routeconfig.models;

public class PBStop {

    private String description;
    private int locationId;
    private boolean timePoint;
    private int sequence;
    private float lng;
    private float lat;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean isTimePoint() {
        return timePoint;
    }

    public void setTimePoint(boolean timePoint) {
        this.timePoint = timePoint;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }
}
