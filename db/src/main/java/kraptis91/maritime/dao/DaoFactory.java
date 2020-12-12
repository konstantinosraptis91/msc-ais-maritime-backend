package kraptis91.maritime.dao;

import kraptis91.maritime.dao.mongodb.MongoDBOceanConditionsDao;
import kraptis91.maritime.model.OceanConditions;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class DaoFactory {

  public static OceanConditionsDao createMongoDBOceanConditionsDao() {
    return new MongoDBOceanConditionsDao();
  }
}
