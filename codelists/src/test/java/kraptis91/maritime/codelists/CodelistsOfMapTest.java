package kraptis91.maritime.codelists;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 14/1/2021.
 */
public class CodelistsOfMapTest {

    @Test
    public void testGetValueForId() {
        Assert.assertEquals("60478", CodelistsOfMap.MMSI_COUNTER_MAP.getValueForId("228037700"));
    }

}
