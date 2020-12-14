package kraptis91.maritime.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigInteger;
import java.util.Objects;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class VesselTrajectoryPoint {

  /** longitude and latitude in geoJSON format
   *  replaced longitude and latitude with geoPoint Stavros Lamprinos on 14/12/2020
   * */
  private final GeoPoint location;

  @DecimalMin(value = "0.0", message = "Invalid speed value, speed cannot be less than 0.0 knots")
  @DecimalMax(
      value = "102.2",
      message = "Invalid speed value, speed cannot be more than 102.2 knots")
  private final double speed;

  private final long timestamp;

  private VesselTrajectoryPoint(double longitude, double latitude, double speed, long timestamp) {
    this.location = new GeoPoint(longitude, latitude);
    this.speed = speed;
    this.timestamp = timestamp;
  }

  /**
   * Create a new vessel trajectory point.
   *
   * @param longitude The longitude
   * @param latitude The latitude
   * @param speed The speed over ground in knots on this point (Min = 0.0, Max = 102.2)
   * @param timestamp timestamp in UNIX epochs
   */
  public static VesselTrajectoryPoint of(
      double longitude, double latitude, double speed, long timestamp) {
    return new VesselTrajectoryPoint(longitude, latitude, speed, timestamp);
  }

  public GeoPoint getLocation() { return this.location; }

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

    return Objects.equals(this.location, other.location);
  }

  @Override
  public String toString() {
    return "VesselTrajectoryPoint{"
        + "location = {"
        + "type = "
        + location.getType()
        + ", coordinates = ["
        + location.getCoordinates()
        + "]}"
        + ", speed="
        + speed
        + ", timestamp="
        + timestamp
        + '}';
  }
}
