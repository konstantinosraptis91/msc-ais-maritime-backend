package kraptis91.maritime.dao;

import kraptis91.maritime.parser.utils.DirectoryUtils;
import kraptis91.maritime.parser.utils.InputStreamUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020.
 * @author Stavros Lamprinos [stalab at linuxmail.org]
 */
public class MongoDBOceanConditionsDaoTest {

  private final InputStream isSample =
      MongoDBOceanConditionsDaoTest.class.getResourceAsStream(
          "/sample/maritime/oc_january_sample.csv");

  // @Ignore
  @Test
  public void testInsertMany() throws Exception {

    //      InputStream isBig =
    //          new FileInputStream(
    //              DirectoryUtils.getDefaultDownloadsDirectory() +
    // "/ocean-conditions/oc_december.csv");

    OceanConditionsDao dao = DaoFactory.createMongoDBOceanConditionsDao();
    dao.insertMany(InputStreamUtils.getBufferedInputStream(isSample));
  }

  @Ignore
  @Test
  public void testAddAllSeaStateForecastDtoToDb() throws Exception {

    String[] months = {"october", "november", "december", "january", "february", "march"};

    for (String m : months) {
      InputStream isBig =
          new FileInputStream(
              DirectoryUtils.getDefaultDownloadsDirectory() + "/ocean-conditions/oc_" + m + ".csv");

      OceanConditionsDao dao = DaoFactory.createMongoDBOceanConditionsDao();
      dao.insertMany(InputStreamUtils.getBufferedInputStream(isBig));
    }
  }
}
