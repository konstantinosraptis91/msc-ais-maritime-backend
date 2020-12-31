package kraptis91.maritime.parser.enums;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/12/2020.
 */
public class CountryCodeMapTest {

    @Test
    public void testGetCountryNameByCode() {
        Assert.assertEquals("Germany", CountryCodeMap.INSTANCE.getCountryNameByCode(CountryCode.DE));
        Assert.assertEquals("Greece", CountryCodeMap.INSTANCE.getCountryNameByCode(CountryCode.GR));
    }

    @Test
    public void testGetCodeByCountryName() {
        Assert.assertEquals(CountryCode.DE, CountryCodeMap.INSTANCE.getCodeByCountryName("Germany"));
        Assert.assertEquals(CountryCode.GR, CountryCodeMap.INSTANCE.getCodeByCountryName("Greece"));
    }

    @Test
    public void testByPassingOneMethodToOther() {
        Assert.assertEquals(CountryCode.DE, CountryCodeMap.INSTANCE.getCodeByCountryName(
            CountryCodeMap.INSTANCE.getCountryNameByCode(CountryCode.DE)));

        Assert.assertEquals(CountryCode.GR, CountryCodeMap.INSTANCE.getCodeByCountryName(
            CountryCodeMap.INSTANCE.getCountryNameByCode(CountryCode.GR)));
    }

}
