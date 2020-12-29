package kraptis91.maritime.db.dao;

import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.VesselTrajectoryPoint;

import java.io.InputStream;
import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public interface VesselTrajectoryChunkDao {

  void insertMany(InputStream csvStream, int capacity) throws Exception;

  default void insertMany(InputStream is) throws Exception {
    insertMany(is, 15000);
  }

  void insertMany(List<VesselTrajectoryChunk> trajectories);

  default List<VesselTrajectoryPoint> findVesselTrajectory(String vesselName) {
    return findVesselTrajectory(vesselName, 0, 30);
  }

  List<VesselTrajectoryPoint> findVesselTrajectory(String vesselName, int skip, int limit);

  default List<VesselTrajectoryPoint> findVesselTrajectory(int mmsi) {
    return findVesselTrajectory(mmsi, 0, 30);
  }

  List<VesselTrajectoryPoint> findVesselTrajectory(int mmsi, int skip, int limit);
}
