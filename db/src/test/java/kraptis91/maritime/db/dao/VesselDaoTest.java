package kraptis91.maritime.db.dao;

import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 8/12/2020.
 */
public class VesselDaoTest {

    private final InputStream isSample =
        VesselDaoTest.class.getResourceAsStream("/sample/maritime/nari_static_sample.csv");

    @Ignore
    @Test
    public void testInsertMany() throws Exception {

        //    InputStream isBig =
        //        new FileInputStream(
        //            DirectoryUtils.getDefaultDownloadsDirectory() + "/ais-data/nari_static.csv");

        InputStream isBig =
            new FileInputStream("D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_static.csv");

        VesselDao dao = DaoFactory.createMongoVesselDao();
        dao.insertMany(isBig);
    }

    @Ignore
    @Test
    public void testFindObjectIdAsString() {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        final int mmsi = 228157000;

        dao.findObjectIdAsString(mmsi)
            .ifPresentOrElse(
                System.out::println, () -> System.out.println("Cannot find vessel with mmsi " + mmsi));
    }

    @Ignore
    @Test
    public void testFindVesselByMMSI() throws Exception {

        VesselDao dao = DaoFactory.createMongoVesselDao();
        System.out.println(dao.findVesselByMMSI(228157000));
        dao.findVesselByMMSI(228051000).ifPresent(vessel ->
            System.out.println(vessel.getVoyages().get(0).getFirstMeasurement().getFormattedDate()));
    }

    @Ignore
    @Test
    public void testFindPlainVesselByMMSI() {

        VesselDao dao = DaoFactory.createMongoVesselDao();
        dao.findPlainVesselByMMSI(244995000).ifPresent(System.out::println);
    }

}
