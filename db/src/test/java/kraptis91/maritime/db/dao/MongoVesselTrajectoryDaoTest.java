package kraptis91.maritime.db.dao;

import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 14/12/2020. */
public class MongoVesselTrajectoryDaoTest {

  private final InputStream isSample =
      MongoVesselTrajectoryDaoTest.class.getResourceAsStream(
          "/sample/maritime/nari_dynamic_sample.csv");

  @Test
  public void testInsertMany() throws Exception {

    InputStream isBig =
        new FileInputStream("D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_dynamic.csv");

    VesselTrajectoryPointDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    dao.insertMany(isBig);
  }

  @Test
  public void testFindVesselTrajectoryByVesselName() {
    VesselTrajectoryPointDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    dao.findVesselTrajectory("F/V EL AMANECER").forEach(System.out::println);
  }

  @Test
  public void testFindVesselTrajectoryByMMSI() {
    VesselTrajectoryPointDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    dao.findVesselTrajectory(228157000).forEach(System.out::println);
  }
}
