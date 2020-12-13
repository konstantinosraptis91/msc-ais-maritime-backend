package kraptis91.maritime.model;

import java.util.LinkedHashSet;
import java.util.Set;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class VesselTrajectory {

  public VesselTrajectory() {}

  /** MMSI identifier for vessel. */
  private int mmsi;

  private Set<VesselTrajectoryPoint> pointSet;

  /**
   * Add a point to trajectory.
   *
   * @param point The point
   */
  public VesselTrajectory add(VesselTrajectoryPoint point) {
    getPointSet().add(point);
    return this;
  }

  /**
   * The the size of the point set.
   *
   * @return The size
   */
  public int size() {
    return getPointSet().size();
  }

  /**
   * Get the vessel trajectory as points.
   *
   * @return The point set
   */
  public Set<VesselTrajectoryPoint> getPointSet() {
    if (pointSet == null) {
      pointSet = new LinkedHashSet<>();
    }
    return pointSet;
  }

  /**
   * Get the mmsi id of vessel in which this trajectory belongs to.
   *
   * @return The mmsi id
   */
  public int getMmsi() {
    return mmsi;
  }

  /**
   * Set the mmsi id of vessel in which this trajectory belongs to.
   *
   * @param mmsi The mmsi id
   */
  public void setMmsi(int mmsi) {
    this.mmsi = mmsi;
  }

  @Override
  public String toString() {
    return "VesselTrajectory{" + "pointSet=" + getPointSet() + '}';
  }
}
