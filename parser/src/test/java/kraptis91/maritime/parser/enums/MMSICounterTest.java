package kraptis91.maritime.parser.enums;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 26/12/2020.
 */
public class MMSICounterTest {

    @Test
    public void testGetCounterForVessel() {
        Assert.assertEquals(40436, MMSICounter.INSTANCE.getMMSICounterForVessel(228190600));
    }

    // @Ignore
    @Test
    public void testFindMaxMMSICounter() {

        int mmsi = 245257000;

//    System.out.println(MMSICounter.INSTANCE.getMaxMMSICounter());
//    System.out.println(MMSICounter.INSTANCE.getMaxMMSICounter() / 50);
//    System.out.println(MMSICounter.INSTANCE.getMaxMMSICounter() % 50);

        System.out.println("Counter for mmsi " + mmsi + " is " + MMSICounter.INSTANCE_SAMPLE.getMMSICounterForVessel(mmsi));
        System.out.println("Div " + MMSICounter.INSTANCE_SAMPLE.getMMSICounterForVessel(mmsi) / 50);
        System.out.println("Mod " + MMSICounter.INSTANCE_SAMPLE.getMMSICounterForVessel(mmsi) % 50);
    }

}
