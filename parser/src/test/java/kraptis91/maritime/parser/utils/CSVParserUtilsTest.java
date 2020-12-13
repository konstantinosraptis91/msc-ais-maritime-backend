package kraptis91.maritime.parser.utils;

import org.junit.Assert;
import org.junit.Test;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public class CSVParserUtilsTest {

  @Test
  public void testApplyTrimAndRemoveJunk() throws Exception {
    Assert.assertEquals("SUEZ,EGYPT", CSVParserUtils.trimAndRemoveJunk("\"\"SUEZ,EGYPT \"\""));
    Assert.assertEquals("1443678222", CSVParserUtils.trimAndRemoveJunk("1443678222\""));
    Assert.assertEquals("MONICA P", CSVParserUtils.trimAndRemoveJunk("\"MONICA P"));
  }
}
