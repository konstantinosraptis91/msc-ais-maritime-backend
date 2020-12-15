package kraptis91.maritime.dao;

import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020.
 * @author Stavros Lamprinos [stalab at linuxmail.org]
 */
public class MongoOceanConditionsDaoTest {

  private final InputStream isSample =
      MongoOceanConditionsDaoTest.class.getResourceAsStream(
          "/sample/maritime/oc_january_sample.csv");

  @Ignore
  @Test
  public void testInsertMany() throws Exception {

    OceanConditionsDao dao = DaoFactory.createMongoOceanConditionsDao();
    dao.insertMany(isSample);
  }

  @Ignore
  @Test
  public void testAddAllSeaStateForecastDtoToDb() throws Exception {

    String[] months = {"october", "november", "december", "january", "february", "march"};

    for (String month : months) {

      InputStream isBig =
          new FileInputStream(
              "D:/NetbeansProjects/maritime-nosql/data/ocean-conditions/oc_" + month + ".csv");

      //      InputStream isBig =
      //          new FileInputStream(
      //              DirectoryUtils.getDefaultDownloadsDirectory() + "/ocean-conditions/oc_" +
      // month +
      // ".csv");

      OceanConditionsDao dao = DaoFactory.createMongoOceanConditionsDao();
      dao.insertMany(isBig);
    }
  }
}
