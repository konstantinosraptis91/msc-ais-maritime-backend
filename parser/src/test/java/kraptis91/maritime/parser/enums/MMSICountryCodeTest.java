package kraptis91.maritime.parser.enums;

import org.junit.Assert;
import org.junit.Test;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public class MMSICountryCodeTest {

  @Test
  public void testGetCountryByMMSI() throws Exception {
    Assert.assertEquals("Malta", MMSICountryCode.INSTANCE.getCountryByMMSI(215));
  }
}
