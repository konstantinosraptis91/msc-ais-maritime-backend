package kraptis91.maritime.db.dao;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 14/12/2020. */
public class MongoVesselTrajectoryChunkDaoTest {

  private final InputStream isSample =
      MongoVesselTrajectoryChunkDaoTest.class.getResourceAsStream(
          "/sample/maritime/nari_dynamic_sample.csv");

  private final InputStream isSample2 =
          MongoVesselTrajectoryChunkDaoTest.class.getResourceAsStream(
                  "/sample/maritime/nari_dynamic_sample_2.csv");

  @Test
  public void testInsertMany() throws Exception {

    InputStream isBig =
        new FileInputStream("D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_dynamic.csv");

    VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    dao.insertMany(isBig);
  }

  @Test
  public void testFindVesselTrajectoryChunksByVesselName() {
    VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    dao.findVesselTrajectory("F/V EL AMANECER", 0, 100).forEach(System.out::println);
  }

  @Test
  public void testFindVesselTrajectoryByMMSI() {
    VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    dao.findVesselTrajectory(228157000).forEach(System.out::println);
  }
}
