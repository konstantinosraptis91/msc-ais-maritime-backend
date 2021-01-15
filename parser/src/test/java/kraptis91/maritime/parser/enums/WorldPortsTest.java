package kraptis91.maritime.parser.enums;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
public class WorldPortsTest {

    @Test
    public void testGetPortNames() {
        // WorldPorts.INSTANCE.getPortNames().forEach(System.out::println);
        Assert.assertFalse(WorldPorts.INSTANCE.getPortNames().isEmpty());
    }

    @Test
    public void testGetPortDto() {
        // WorldPorts.INSTANCE.getPortDtoList().forEach(dto -> System.out.println(dto.toString()));
        Assert.assertFalse(WorldPorts.INSTANCE.getPortDtoList().isEmpty());
    }

}
