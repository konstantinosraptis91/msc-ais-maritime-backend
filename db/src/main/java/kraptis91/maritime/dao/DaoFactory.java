package kraptis91.maritime.dao;

import kraptis91.maritime.dao.mongodb.MongoDBOceanConditionsDao;
import kraptis91.maritime.dao.mongodb.MongoDBVesselDao;
import kraptis91.maritime.model.OceanConditions;
import kraptis91.maritime.model.Vessel;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class DaoFactory {

  public static OceanConditionsDao createMongoDBOceanConditionsDao() {
    return new MongoDBOceanConditionsDao();
  }

  public static VesselDao createMongoDBOVesselDao() {
    return new MongoDBVesselDao();
  }
}
