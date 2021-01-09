package kraptis91.maritime.parser.enums;

import org.junit.Assert;
import org.junit.Test;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public class ShipTypesTest {

  @Test
  public void testGetShipType() throws Exception {
    Assert.assertEquals("Reserved", ShipTypes.INSTANCE.getShipType(10));
  }
}
