package ws.otero.adrian.portlandbus.trimetservices.arrivals;

import java.util.List;

import ws.otero.adrian.portlandbus.trimetservices.BaseResultObject;
import ws.otero.adrian.portlandbus.trimetservices.arrivals.models.PBArrival;
import ws.otero.adrian.portlandbus.trimetservices.arrivals.models.PBLocation;

public class ArrivalsResult extends BaseResultObject {

    private List<PBArrival> arrivals;
    private List<PBLocation> locations;

    public ArrivalsResult(List<PBArrival> arrivals, List<PBLocation> locations) {
        this.arrivals = arrivals;
        this.locations = locations;
    }

    public ArrivalsResult(String errorMessage) {
        super(errorMessage);
    }

    public List<PBArrival> getArrivals() {
        return arrivals;
    }

    public List<PBLocation> getLocations() {
        return locations;
    }
}
