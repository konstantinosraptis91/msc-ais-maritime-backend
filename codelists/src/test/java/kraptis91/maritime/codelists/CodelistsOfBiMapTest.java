package kraptis91.maritime.codelists;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 13/1/2021.
 */
public class CodelistsOfBiMapTest {

    @Test
    public void testGetValueForId() {
        Assert.assertEquals("Afghanistan", CodelistsOfBiMap.COUNTRY_CODE_MAP.getValueForId("AF"));
    }

    @Test
    public void testGetIdForValue() {
        Assert.assertEquals("BW", CodelistsOfBiMap.COUNTRY_CODE_MAP.getIdForValue("Botswana"));
    }

    @Test
    public void testByPassingOneMethodToOther() {
        Assert.assertEquals("GR", CodelistsOfBiMap.COUNTRY_CODE_MAP.getIdForValue(
            CodelistsOfBiMap.COUNTRY_CODE_MAP.getValueForId("GR")));

        Assert.assertEquals("Greece", CodelistsOfBiMap.COUNTRY_CODE_MAP.getValueForId(
            CodelistsOfBiMap.COUNTRY_CODE_MAP.getIdForValue("Greece")));
    }

}
