package kraptis91.maritime.db.dao;

import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.VesselTrajectoryPointListChunk;

import java.io.InputStream;
import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public interface VesselTrajectoryChunkDao {

  void insertMany(InputStream csvStream, int capacity) throws Exception;

  default void insertMany(InputStream is) throws Exception {
    insertMany(is, 15000);
  }

  void insertMany(List<VesselTrajectoryPointListChunk> trajectoryPointListChunkList);

  List<VesselTrajectoryChunk> findVesselTrajectory(String vesselName);

  List<VesselTrajectoryChunk> findVesselTrajectory(int mmsi);
}
