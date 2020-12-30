package kraptis91.maritime.parser.enums;

import kraptis91.maritime.parser.dto.csv.PortDto;
import org.junit.Test;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
public class WorldPortsTest {

    @Test
    public void testGetPortNames() {
        WorldPorts.INSTANCE.getPortNames().forEach(System.out::println);
    }

    @Test
    public void testGetPortDto() {
        WorldPorts.INSTANCE.getPortDtoList().forEach(dto -> System.out.println(dto.toString()));
    }

}
