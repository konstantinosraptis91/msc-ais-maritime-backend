package kraptis91.maritime.db.enums;

import kraptis91.maritime.db.dao.mongodb.conf.MongoDBConfig;
import org.junit.Ignore;
import org.junit.Test;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public class MongoDBConfigTest {

  @Ignore
  @Test
  public void testGetPasswordAsCharArray() throws Exception {
    for (char c : MongoDBConfig.getPassword()) {
      System.out.println(c);
    }
  }
}
