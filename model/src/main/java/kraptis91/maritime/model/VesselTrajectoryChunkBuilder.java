package kraptis91.maritime.model;

import java.util.Date;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
public class VesselTrajectoryChunkBuilder {

    private final int mmsi;
    private String vesselName;
    private String country;
    private String shipType;
    private Date startDate;
    private Date endDate;
    private GeoPoint avgGeoPoint;
    private double avgSpeed;
    private int nPoints;

    public VesselTrajectoryChunkBuilder(int mmsi) {
        this.mmsi = mmsi;
    }

    public VesselTrajectoryChunkBuilder withVesselName(String vesselName) {
        this.vesselName = vesselName;
        return this;
    }

    public VesselTrajectoryChunkBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public VesselTrajectoryChunkBuilder withShipType(String shipType) {
        this.shipType = shipType;
        return this;
    }

    public VesselTrajectoryChunkBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public VesselTrajectoryChunkBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public VesselTrajectoryChunkBuilder withAvgGeoPoint(GeoPoint point) {
        this.avgGeoPoint = point;
        return this;
    }

    public VesselTrajectoryChunkBuilder withAvgSpeed(double speed) {
        this.avgSpeed = speed;
        return this;
    }

    public VesselTrajectoryChunkBuilder withNPoints(int nPoints) {
        this.nPoints = nPoints;
        return this;
    }

    public VesselTrajectoryChunk buildChunk() {
        return new VesselTrajectoryChunk(this);
    }

    public VesselTrajectoryPointListChunk buildPointListChunk() {
        return new VesselTrajectoryPointListChunk(this);
    }

    public int getMMSI() {
        return mmsi;
    }

    public String getVesselName() {
        return vesselName;
    }

    public String getCountry() {
        return country;
    }

    public String getShipType() {
        return shipType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public GeoPoint getAvgGeoPoint() {
        return avgGeoPoint;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public int getNPoints() {
        return nPoints;
    }
}
