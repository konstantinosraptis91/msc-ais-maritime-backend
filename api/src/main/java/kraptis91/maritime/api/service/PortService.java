package kraptis91.maritime.api.service;

import kraptis91.maritime.model.Port;
import kraptis91.maritime.retriever.MaritimeDataRetriever;
import kraptis91.maritime.retriever.RetrieverFactory;

import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public class PortService {

    public List<Port> getPorts(int skip, int limit) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getPorts(skip, limit);
    }

    public List<Port> getPortsByCountryCode(String countryCode) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getPortsByCountryCode(countryCode.toUpperCase());
    }

    public List<Port> getNearPortsByReferencePoint(double longitude, double latitude, double maxDistance,
                                                   int skip, int limit) {

        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getNearPortsByReferencePoint(longitude, latitude, maxDistance, 0, skip, limit);
    }

    public List<Port> getNearPortsByMMSI(int mmsi, double maxDistance, int skip, int limit) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getNearPortsByMMSI(mmsi, maxDistance, skip, limit);
    }

}
