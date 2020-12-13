package kraptis91.maritime.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigInteger;
import java.util.Objects;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class VesselTrajectoryPoint {

  private final double longitude;
  private final double latitude;

  @DecimalMin(value = "0.0", message = "Invalid speed value, speed cannot be less than 0.0 knots")
  @DecimalMax(
      value = "102.2",
      message = "Invalid speed value, speed cannot be more than 102.2 knots")
  private final double speed;

  private final long timestamp;

  private VesselTrajectoryPoint(double longitude, double latitude, double speed, long timestamp) {
    this.longitude = longitude;
    this.latitude = latitude;
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

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
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
    hash = 29 * hash + Objects.hashCode(this.longitude);
    hash = 29 * hash + Objects.hashCode(this.latitude);
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

    return Objects.equals(this.longitude, other.longitude)
        && Objects.equals(this.latitude, other.latitude);
  }

  @Override
  public String toString() {
    return "VesselTrajectoryPoint{"
        + "longitude="
        + longitude
        + ", latitude="
        + latitude
        + ", speed="
        + speed
        + ", timestamp="
        + timestamp
        + '}';
  }
}
