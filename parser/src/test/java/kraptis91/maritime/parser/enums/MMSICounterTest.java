package kraptis91.maritime.parser.enums;

import org.junit.Assert;
import org.junit.Test;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 26/12/2020. */
public class MMSICounterTest {

  @Test
  public void testGetCounterForVessel() throws Exception {
    Assert.assertEquals(40436, MMSICounter.INSTANCE.getCounterForVessel(228190600));
  }
}
