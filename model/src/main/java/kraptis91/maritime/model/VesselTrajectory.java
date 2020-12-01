package kraptis91.maritime.model;

import java.util.LinkedHashSet;
import java.util.Set;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class VesselTrajectory {

  private Set<VesselTrajectoryPoint> pointSet;

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
}
