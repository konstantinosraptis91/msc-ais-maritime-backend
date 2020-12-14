package kraptis91.maritime.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Objects;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
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

  private final long timestamp;

  private VesselTrajectoryPoint(GeoPoint geoPoint, double speed, long timestamp) {
    this.geoPoint = geoPoint;
    this.speed = speed;
    this.timestamp = timestamp;
  }

  /**
   * Create a new vessel trajectory point.
   *
   * @param geoPoint The geoPoint as a wrapper for the longitude and the latitude
   * @param speed The speed over ground in knots on this point (Min = 0.0, Max = 102.2)
   * @param timestamp timestamp in UNIX epochs
   */
  public static VesselTrajectoryPoint createInstance(
      GeoPoint geoPoint, double speed, long timestamp) {
    return new VesselTrajectoryPoint(geoPoint, speed, timestamp);
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
}
