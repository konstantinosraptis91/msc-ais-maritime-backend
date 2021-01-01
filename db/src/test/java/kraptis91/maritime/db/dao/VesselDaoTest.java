package kraptis91.maritime.db.dao;

import kraptis91.maritime.model.ReceiverMeasurement;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.Voyage;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

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

        //    InputStream isBig =
        //        new
        // FileInputStream("D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_static.csv");

        VesselDao dao = DaoFactory.createMongoVesselDao();
        dao.insertMany(isSample);
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
    public void testVesselByMMSI() throws Exception {

        VesselDao dao = DaoFactory.createMongoVesselDao();
        System.out.println(dao.findVesselByMMSI(228157000));
        dao.findVesselByMMSI(228051000).ifPresent(vessel ->
            System.out.println(vessel.getVoyages().get(0).getFirstMeasurement().getFormattedDate()));


//        long t = 1443650723;
//        ZoneId zone = ZoneId.of("UTC");
//        DateTimeFormatter df = DateTimeFormatter
//            .ofPattern("dd/MM/yyyy hh:mm:ss")
//            .withZone(zone);
//        String fDate = df.format(Instant.ofEpochSecond(t));
//        System.out.println(fDate);


    }

}
