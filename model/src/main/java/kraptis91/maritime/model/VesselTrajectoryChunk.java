package kraptis91.maritime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kraptis91.maritime.model.utils.TrajectoryChunkUtils;
import kraptis91.maritime.parser.enums.MMSICounter;

import java.util.Date;
import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
public class VesselTrajectoryChunk {

    protected final int mmsi;
    protected final String vesselName;
    protected final String shipType;
    protected Date startDate;
    protected Date endDate;
    protected GeoPoint avgGeoPoint;
    protected double avgSpeed;
    @JsonIgnore
    protected final int chunkFixedSize;
    protected int nPoints;

    VesselTrajectoryChunk(VesselTrajectoryChunkBuilder builder) {
        this.mmsi = builder.getMMSI();
        this.vesselName = builder.getVesselName();
        this.shipType = builder.getShipType();
        this.startDate = builder.getStartDate();
        this.endDate = builder.getEndDate();
        this.avgGeoPoint = builder.getAvgGeoPoint();
        this.avgSpeed = builder.getAvgSpeed();
        this.nPoints = builder.getNPoints();
        this.chunkFixedSize = Math.max(TrajectoryChunkUtils
                .calcChunkCapacity(MMSICounter.INSTANCE.getMMSICounterForVessel(mmsi)), 1);
    }

    public void setAvgGeoPoint(GeoPoint avgGeoPoint) {
        this.avgGeoPoint = avgGeoPoint;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public int getChunkFixedSize() {
        return chunkFixedSize;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public GeoPoint getAvgGeoPoint() {
        return avgGeoPoint;
    }

    public int getNumberOfPoints() {
        return nPoints;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getMmsi() {
        return mmsi;
    }

    public String getVesselName() {
        return vesselName;
    }

    public String getShipType() {
        return shipType;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "VesselTrajectoryChunk{" +
                "mmsi=" + mmsi +
                ", vesselName='" + vesselName + '\'' +
                ", shipType='" + shipType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", avgGeoPoint=" + avgGeoPoint +
                ", avgSpeed=" + avgSpeed +
                ", chunkFixedSize=" + chunkFixedSize +
                ", nPoints=" + nPoints +
                '}';
    }
}
