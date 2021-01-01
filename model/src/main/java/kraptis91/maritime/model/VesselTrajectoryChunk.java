package kraptis91.maritime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import kraptis91.maritime.model.utils.TrajectoryChunkUtils;
import kraptis91.maritime.parser.enums.MMSICounter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
@JsonPropertyOrder(
    {
        "mmsi",
        "vesselName",
        "shipType",
        "startDate",
        "endDate",
        "avgGeoPoint",
        "avgSpeed",
        "nPoints"
    })
public class VesselTrajectoryChunk {

    protected final int mmsi;
    protected final String vesselName;
    protected final String shipType;
    @JsonIgnore
    protected Date startDate;
    @JsonIgnore
    protected Date endDate;
    protected GeoPoint avgGeoPoint;
    protected double avgSpeed;
    @JsonIgnore
    protected final int chunkFixedSize;
    protected int nPoints;

    private static final DateTimeFormatter dateTimeFormatter;
    private String formattedStartDate;
    private String formattedEndDate;

    static {
        ZoneId zone = ZoneId.of("UTC");
        dateTimeFormatter = DateTimeFormatter
            .ofPattern("dd/MM/yyyy hh:mm:ss")
            .withZone(zone);
    }

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

    @JsonProperty("startDate")
    public String getFormattedStartDate() {
        if (Objects.isNull(formattedStartDate)
            && !Objects.isNull(startDate)) {

            formattedStartDate = dateTimeFormatter.format(Instant.ofEpochSecond(startDate.getTime()));
        }
        return formattedStartDate;
    }

    @JsonProperty("endDate")
    public String getFormattedEndDate() {
        if (Objects.isNull(formattedEndDate)
            && !Objects.isNull(endDate)) {

            formattedEndDate = dateTimeFormatter.format(Instant.ofEpochSecond(endDate.getTime()));
        }
        return formattedEndDate;
    }

    @Override
    public String toString() {
        return "VesselTrajectoryChunk{" +
            "mmsi=" + mmsi +
            ", vesselName='" + vesselName + '\'' +
            ", shipType='" + shipType + '\'' +
            ", startDate=" + getFormattedStartDate() +
            ", endDate=" + getFormattedEndDate() +
            ", avgGeoPoint=" + avgGeoPoint +
            ", avgSpeed=" + avgSpeed +
            ", chunkFixedSize=" + chunkFixedSize +
            ", nPoints=" + nPoints +
            '}';
    }
}
