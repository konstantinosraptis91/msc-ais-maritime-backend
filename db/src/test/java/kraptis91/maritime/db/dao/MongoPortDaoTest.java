package kraptis91.maritime.db.dao;

import kraptis91.maritime.db.dao.mongodb.query.utils.NearQueryOptions;
import kraptis91.maritime.model.ModelExtractor;
import kraptis91.maritime.model.Port;
import kraptis91.maritime.parser.enums.WorldPorts;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public class MongoPortDaoTest {

    @Ignore
    @Test
    public void testInsertMany() {
        List<Port> ports = WorldPorts.INSTANCE.getPortDtoList().stream()
            .map(ModelExtractor::extractPort)
            .collect(Collectors.toList());

        PortDao dao = DaoFactory.createMongoPortDao();
        dao.insertMany(ports);
    }

    @Ignore
    @Test
    public void testFindNearPorts() {

        PortDao dao = DaoFactory.createMongoPortDao();

        dao.findNearPorts(NearQueryOptions.builder()
            .withLongitude(3.116667)
            .withLatitude(42.516667)
            .withMaxDistance(500000)
            .withMinDistance(0)
            .skip(0)
            .limit(30)
            .build())
            .forEach(System.out::println);
    }

}
