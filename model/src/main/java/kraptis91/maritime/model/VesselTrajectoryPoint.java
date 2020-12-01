package kraptis91.maritime.model;

import java.util.Objects;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class VesselTrajectoryPoint {

  private final double longitude;
  private final double latitude;

  public VesselTrajectoryPoint(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + Objects.hashCode(this.longitude);
    hash = 29 * hash + Objects.hashCode(this.latitude);
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
}
