package kraptis91.maritime.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020.
 */
public class VesselTrajectoryPoint {

    /**
     * longitude and latitude in geoJSON format replaced longitude and latitude with geoPoint Stavros
     * Lamprinos on 14/12/2020
     */
    private final GeoPoint geoPoint;

    @DecimalMin(value = "0.0", message = "Invalid speed value, speed cannot be less than 0.0 knots")
    @DecimalMax(
            value = "102.2",
            message = "Invalid speed value, speed cannot be more than 102.2 knots")
    private final double speed;
    private final String vesselId;
    private final long timestamp;

    private VesselTrajectoryPoint(GeoPoint geoPoint, double speed, long timestamp, String vesselId) {
        this.geoPoint = geoPoint;
        this.speed = speed;
        this.timestamp = timestamp;
        this.vesselId = vesselId;
    }

    public GeoPoint getGeoPoint() {
        return this.geoPoint;
    }

    public double getSpeed() {
        return speed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getVesselId() {
        return vesselId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.timestamp);
        hash = 29 * hash + Objects.hashCode(this.speed);
        hash = 29 * hash + Objects.hashCode(this.geoPoint.getLongitude());
        hash = 29 * hash + Objects.hashCode(this.geoPoint.getLatitude());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        final VesselTrajectoryPoint other = (VesselTrajectoryPoint) obj;

        return this.geoPoint.equals(other.geoPoint)
                && Objects.equals(this.speed, other.speed)
                && Objects.equals(this.timestamp, other.timestamp);
    }

    @Override
    public String toString() {
        return "VesselTrajectoryPoint{"
                + "geoPoint="
                + geoPoint
                + ", speed="
                + speed
                + ", timestamp="
                + timestamp
                + '}';
    }

    public interface PointCoordinates {
        PointSpeed withCoordinates(GeoPoint geoPoint);
    }

    public interface PointSpeed {
        PointTimestamp withSpeed(double speed);
    }

    public interface PointTimestamp {
        PointVesselId withTimestamp(long timestamp);
    }

    public interface PointVesselId {
        PointBuild withVesselId(String vesselId);
    }

    public interface PointBuild {
        VesselTrajectoryPoint build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements PointCoordinates, PointSpeed, PointTimestamp, PointVesselId, PointBuild {

        private GeoPoint geoPoint;
        private double speed;
        private String vesselId;
        private long timestamp;

        public Builder() {
        }

        /**
         * @param geoPoint The geoPoint as a wrapper for the longitude and the latitude
         * @return The PointSpeed to set the speed value
         */
        @Override
        public PointSpeed withCoordinates(GeoPoint geoPoint) {
            this.geoPoint = geoPoint;
            return this;
        }

        /**
         * @param speed The speed over ground in knots on this point (Min = 0.0, Max = 102.2)
         * @return The PointTimestamp to set the timestamp
         */
        @Override
        public PointTimestamp withSpeed(double speed) {
            this.speed = speed;
            return this;
        }

        /**
         * @param timestamp timestamp in UNIX epochs
         * @return The PointVesselId to set the vessel _id
         */
        @Override
        public PointVesselId withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * @param vesselId The mongo _id of the parent vessel
         * @return The PointBuild to build the VesselTrajectoryPoint object
         */
        @Override
        public PointBuild withVesselId(String vesselId) {
            this.vesselId = vesselId;
            return this;
        }

        /**
         * Create the VesselTrajectoryPoint object.
         *
         * @return The VesselTrajectoryPoint
         */
        @Override
        public VesselTrajectoryPoint build() {
            return new VesselTrajectoryPoint(geoPoint, speed, timestamp, vesselId);
        }

    }

}
