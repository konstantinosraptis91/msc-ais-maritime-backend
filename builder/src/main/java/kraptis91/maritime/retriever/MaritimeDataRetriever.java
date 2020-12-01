package kraptis91.maritime.retriever;

import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectory;
import kraptis91.maritime.model.VesselTrajectoryPoint;

import java.math.BigInteger;
import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public interface MaritimeDataRetriever {

  VesselTrajectoryPoint getVesselPosition(String vesselName);

  VesselTrajectoryPoint getVesselPosition(String vesselName, BigInteger timestamp);

  String getVesselDestination(String vesselName);

  VesselTrajectory getVesselTrajectory(String vesselName);

  List<Vessel> getVesselsByTrajectoryPoint(double longitude, double latitude);

  /**
   * Get vessels by draught specifications.
   *
   * @param min The minimum draught
   * @param max The maximum draught
   * @return The vessel list
   */
  List<Vessel> getVesselsByDraught(double min, double max);

  /**
   * Get vessels by type.
   *
   * @param id The ship type id
   * @return The vessel list
   */
  List<Vessel> getVesselsByType(int id);
}
