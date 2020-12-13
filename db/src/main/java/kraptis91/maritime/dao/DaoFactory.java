package kraptis91.maritime.dao;

import kraptis91.maritime.dao.mongodb.MongoOceanConditionsDao;
import kraptis91.maritime.dao.mongodb.MongoVesselDao;
import kraptis91.maritime.dao.mongodb.MongoVesselTrajectoryPointDao;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class DaoFactory {

  public static OceanConditionsDao createMongoOceanConditionsDao() {
    return new MongoOceanConditionsDao();
  }

  public static VesselDao createMongoVesselDao() {
    return new MongoVesselDao();
  }

  public static VesselTrajectoryPointDao createMongoVesselTrajectoryPointDao() {
    return new MongoVesselTrajectoryPointDao();
  }
}
