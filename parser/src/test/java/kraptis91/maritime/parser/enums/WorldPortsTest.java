package kraptis91.maritime.parser.enums;

import org.junit.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
public class WorldPortsTest {

    @Test
    public void testGetPortNames() {
        WorldPorts.INSTANCE.getPortNames().forEach(System.out::println);
    }

}
