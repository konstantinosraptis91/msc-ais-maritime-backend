package kraptis91.maritime.dao;

import org.junit.Test;

import java.io.InputStream;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 14/12/2020. */
public class MongoVesselTrajectoryPointDaoTest {

  private final InputStream isSample =
      MongoOceanConditionsDaoTest.class.getResourceAsStream(
          "/sample/maritime/nari_dynamic_sample.csv");

  @Test
  public void testInsertMany() throws Exception {
    VesselTrajectoryPointDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    dao.insertMany(isSample);
  }
}
