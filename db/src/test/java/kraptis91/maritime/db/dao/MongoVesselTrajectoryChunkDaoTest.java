package kraptis91.maritime.db.dao;

import kraptis91.maritime.db.dao.mongodb.query.utils.NearQueryOptions;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 14/12/2020.
 */
public class MongoVesselTrajectoryChunkDaoTest {

    private final InputStream isSample =
        MongoVesselTrajectoryChunkDaoTest.class.getResourceAsStream(
            "/sample/maritime/nari_dynamic_sample.csv");

    private final InputStream isSample2 =
        MongoVesselTrajectoryChunkDaoTest.class.getResourceAsStream(
            "/sample/maritime/nari_dynamic_sample_2.csv");

    @Ignore
    @Test
    public void testInsertMany() throws Exception {

        InputStream isBig =
            new FileInputStream("D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_dynamic.csv");

        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        dao.insertMany(isBig);
    }

    @Ignore
    @Test
    public void testCount() throws Exception {
        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        System.out.println("Number of Documents in vesselTrajectoryChunks collection: " + dao.count());
    }

    // @Ignore
//    @Test
//    public void testDeleteMany() throws Exception {
//        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
//        System.out.println("Deleting all in the vesselTrajectoryChunks collection: ");
//        System.out.println("Documents deleted: " + dao.deleteMany());
//    }

    @Ignore
    @Test
    public void testFindVesselTrajectoryChunksByVesselName() {
        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        dao.findVesselTrajectory("F/V EL AMANECER").forEach(System.out::println);
    }

    @Ignore
    @Test
    public void testFindVesselTrajectoryByMMSI() {
        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        dao.findVesselTrajectory(228157000).forEach(System.out::println);
    }

    @Ignore
    @Test
    public void testFindNearVesselsByMMSI() {
        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        dao.findNearVesselsMMSIList(3.116667, 42.516667, 500000, 0)
            .forEach(System.out::println);
    }

    @Ignore
    @Test
    public void testFindNearVesselsByMMSIWithOptionParam() {
        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        dao.findNearVesselsMMSIList(NearQueryOptions.builder()
            .withLongitude(3.116667)
            .withLatitude(42.516667)
            .withMaxDistance(500000)
            .withMinDistance(0)
            .skip(0)
            .limit(30)
            .build())
            .forEach(System.out::println);
    }

    @Ignore
    @Test
    public void testFindNearVessels() {
        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        dao.findNearVessels(NearQueryOptions.builder()
            .withLongitude(3.116667)
            .withLatitude(42.516667)
            .withMaxDistance(500000)
            .withMinDistance(0)
            .skip(0)
            .limit(30)
            .build())
            .forEach(System.out::println);
    }

}
