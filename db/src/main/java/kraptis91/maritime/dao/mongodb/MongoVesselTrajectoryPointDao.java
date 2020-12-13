package kraptis91.maritime.dao.mongodb;

import kraptis91.maritime.dao.VesselTrajectoryPointDao;
import kraptis91.maritime.model.VesselTrajectoryPoint;

import java.io.InputStream;
import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public class MongoVesselTrajectoryPointDao implements VesselTrajectoryPointDao {

  @Override
  public void insertMany(InputStream is, int chunkSize) throws Exception {}

  @Override
  public void insertMany(List<VesselTrajectoryPoint> points) {}
}
