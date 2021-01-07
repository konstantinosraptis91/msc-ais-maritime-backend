package kraptis91.maritime.db.dao;

import kraptis91.maritime.db.dao.mongodb.query.utils.NearQueryOptions;
import kraptis91.maritime.model.Port;

import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public interface PortDao {

    void insertMany(List<Port> ports);

    List<Port> findPorts(int skip, int limit);

    List<Port> findPortsByCountryCode(String countryCode);

    List<Port> findNearPorts(double longitude,
                             double latitude,
                             double maxDistance,
                             double minDistance,
                             int skip,
                             int limit);

    List<Port> findNearPorts(NearQueryOptions options);

}
