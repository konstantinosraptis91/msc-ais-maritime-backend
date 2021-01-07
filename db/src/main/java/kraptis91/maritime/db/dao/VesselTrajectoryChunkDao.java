package kraptis91.maritime.db.dao;

import kraptis91.maritime.db.dao.mongodb.query.utils.NearQueryOptions;
import kraptis91.maritime.model.PlainVessel;
import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.VesselTrajectoryPointListChunk;

import java.io.InputStream;
import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020.
 */
public interface VesselTrajectoryChunkDao {

    void insertMany(InputStream csvStream, int capacity) throws Exception;

    default void insertMany(InputStream is) throws Exception {
        insertMany(is, 15000);
    }

    void insertMany(List<VesselTrajectoryPointListChunk> trajectoryPointListChunkList);

    List<VesselTrajectoryChunk> findVesselTrajectory(String vesselName);

    List<VesselTrajectoryChunk> findVesselTrajectory(int mmsi);

    List<Integer> findNearVesselsMMSIList(double longitude, double latitude, double maxDistance, double minDistance);

    List<Integer> findNearVesselsMMSIList(NearQueryOptions options);

    List<PlainVessel> findNearVessels(NearQueryOptions options);
}
