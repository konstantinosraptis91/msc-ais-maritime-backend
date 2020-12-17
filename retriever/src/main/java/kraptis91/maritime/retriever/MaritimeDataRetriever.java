package kraptis91.maritime.retriever;

import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.retriever.exception.RetrieverException;

import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public interface MaritimeDataRetriever {

  String getVesselDestination(int mmsi);

  String getVesselDestination(String vesselName) throws RetrieverException;

  List<VesselTrajectoryPoint> getVesselTrajectory(int mmsi);

  List<VesselTrajectoryPoint> getVesselTrajectory(String vesselName);

  Vessel getVesselByMMSI(int mmsi) throws RetrieverException;

  Vessel getVesselByName(String vesselName) throws RetrieverException;

  /**
   * Get vessels by type.
   *
   * @param shipType The ship type
   * @return The vessel list
   */
  List<Vessel> getVesselsByType(String shipType);

  /**
   * Get vessels by type.
   *
   * @param shipType The ship type
   * @param skip Number of docs to skip in db
   * @param limit Limit the number of results
   * @return The vessel list
   */
  List<Vessel> getVesselsByType(String shipType, int skip, int limit);
}
